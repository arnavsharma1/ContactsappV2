// Homework Assignment Number 4                                               //
// Class: CS6301 User Interface Design                                        //
// Group of 2 students                                                        //
//----------------------------------------------------------------------------//
// Name1: ARJUN SHROFF MAHESH        Net ID: axs144930                        //
//----------------------------------------------------------------------------//
// Name2: ARNAV SHARMA               Net ID: axs144130                        //
//----------------------------------------------------------------------------//
// Date created: 03.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//
// This program manages the Add contact screen and Edit contact screen. 	  //

package com.example.arnav.contactsappv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.arnav.contactsappv2.R.id.email;
import static com.example.arnav.contactsappv2.R.id.firstName;
import static com.example.arnav.contactsappv2.R.id.lastName;
import static com.example.arnav.contactsappv2.R.id.phone;


public class newindow extends ActionBarActivity implements View.OnClickListener {

    EditText tvView;
    EditText fName;
    EditText lName;
    EditText phoneNum;
    EditText mail;
    Button mButton;
    //Button dButton;
    String check;
    Intent intent;
    static File myFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "contacts5.txt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcontact);

        tvView = (EditText) findViewById(firstName);
        mButton = (Button) findViewById(R.id.saveButton);
        // dButton = (Button) findViewById(R.id.deleteButton);
        mButton.setOnClickListener(this);
        //dButton.setOnClickListener(this);
        fName = (EditText) findViewById(firstName);
        lName = (EditText) findViewById(lastName);
        phoneNum = (EditText) findViewById(phone);
        mail = (EditText) findViewById(email);

        intent = getIntent();
        check = intent.getStringExtra("check");
        if (check.equals("add")) {

        } else if (check.equals("delete")) {

            deleteRecord(myFile, intent.getStringExtra("name"), intent.getStringExtra("lname"), intent.getStringExtra("phone"), intent.getStringExtra("email"));
            finish();




        } else {
            String lineToRemove = intent.getStringExtra("name") + intent.getStringExtra("lname") + intent.getStringExtra("phone") + intent.getStringExtra("email");
            tvView.setText(intent.getStringExtra("name"));

                lName.setText(intent.getStringExtra("lname"));

                phoneNum.setText(intent.getStringExtra("phone"));

                mail.setText(intent.getStringExtra("email"));

        }


    }

    public boolean deleteRecord(File path, String varFirstName, String varLastName, String varPhoneNo, String varEmail){
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "contacts5.txt");
        // Boolean to let us know if the record was found and deleted
        boolean wasDeleted = false;
        try {
            // will be used for storing the list after reading from the disk
            List<Contacts> listRead = new ArrayList<Contacts>();

            // Read the current data into the list
            listRead = readFile(file);
            file.delete();
            file.createNewFile();
			/*
			 * Iterate over each object and check if the name property of each object is the same as the one we have to delete, if so then, delete the object and then write the list again to file
			 */
            //listRead.remove(posi);
            //wasDeleted=true;

            for (Contacts person : listRead) {
                if (person.getName().equalsIgnoreCase(varFirstName) && person.getLname().equalsIgnoreCase(varLastName)
                        && person.getPhone().equalsIgnoreCase(varPhoneNo) && person.getEmail().equalsIgnoreCase(varEmail)) {
                    System.out.println("-------------------------------here------------------------------------");
                    listRead.remove(person);
                    wasDeleted = true; // set to true, means record was deleted
                    break; // Break here, as as only one occurrence of a particular combination of First Name + Middle Name + Last Name are allowed
                }
            }

            // if record was deleted then write the new list to disk..
            if (wasDeleted) {
                boolean wasWritten = writeList(file, listRead); // Boolean to know if the file was successfully written
                wasDeleted = wasWritten;
            }
            return wasDeleted;
        } catch (Exception e) {
        }
        return wasDeleted;
    }

    public boolean writeList(File file, List<Contacts> listToWrite) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            // Assume default encoding
            // We usually write as, FileWriter(fileName,true). BUT NO 'true' here denotes file will not be overwritten each time, but will insert data in same file.
            fileWriter = new FileWriter(file);
            //wrap FileWriter in BufferedWriter
            bufferedWriter = new BufferedWriter(fileWriter);

            for (Contacts p : listToWrite) {
                bufferedWriter.write(p.getName() + "," + p.getLname() + "," + p.getPhone() + "," + p.getEmail());
                bufferedWriter.newLine();
            }

            // close and flush connections
            fileWriter.flush();
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.saveButton:
                String firstName = fName.getText().toString();
                if (firstName.equals("")) {

                    Toast.makeText(getBaseContext(), "Please Enter the First Name",
                            Toast.LENGTH_LONG).show();
                    break;
                } else if (check.equals("add")) {
                    String lastName = lName.getText().toString()+" ";
                    String phnNum = phoneNum.getText().toString()+" ";
                    String Email = mail.getText().toString()+" ";
                    WriteBtn(firstName, lastName, phnNum, Email);
                    break;
                } else {
                    editRecord();
                    break;
                }
                //  case R.id.deleteButton:


                //    deleteRecord(myFile, intent.getStringExtra("name"), intent.getStringExtra("lname"), intent.getStringExtra("phone"), intent.getStringExtra("email"));
                // finish();

                //break;

        }
    }

    public void editRecord() {


        // Delete the existing record
        deleteRecord(myFile, intent.getStringExtra("name"), intent.getStringExtra("lname"), intent.getStringExtra("phone"), intent.getStringExtra("email"));

        // Adding New Record with details
        String lineToWrite = fName.getText().toString()+ "," + lName.getText().toString()+ "," + phoneNum.getText().toString()+ "," + mail.getText().toString()+" ";


        boolean flg = WriteBtn(lineToWrite);
        if (flg) {
            Toast.makeText(getBaseContext(), "Record saved to File !", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private boolean checkValidity(String FName, String LName, String PNo, String Email) {
        // checks first name for s empty
        if (FName.equals(null) || FName.length() <= 0) {
            // User prompted to enter first name
            Toast.makeText(getBaseContext(), "Please enter your First Name !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // write text to file
    public void WriteBtn(String fname, String Lname, String phoneNumb, String email1) {

        try {

            if (!myFile.exists()) {
                myFile.createNewFile();
            }

            FileWriter fw = new FileWriter(myFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fname + "," + Lname + "," + phoneNumb + "," + email1);
            bw.flush();
            bw.newLine();
            bw.close();


            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            finish();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean WriteBtn(String line) {
        // add/-write text into file
        try {

            if (!myFile.exists()) {
                myFile.createNewFile();
            }

            FileWriter fw = new FileWriter(myFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.flush();
            bw.newLine();
            bw.close();


            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            finish();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void Read() {
        try {


            BufferedReader buffreader = new BufferedReader(new FileReader(myFile));
            String line, line1 = "";
            try {
                while ((line = buffreader.readLine()) != null)
                    line1 += line;
                tvView.setText(line1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            String error = "";
            error = e.getMessage();
        }


    }

    public List<Contacts> readFile(File path) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "contacts5.txt");
        List<Contacts> personList = new ArrayList<Contacts>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                String delimited[] = null;
                // Split each line into it's constituent properties, then the result is stored in a string array.
                delimited = line.split(",");
                // This will make sure to take only the correct data in the correct format

                //	String array is used to initialize a Person object.
                Contacts p1 = new Contacts(delimited[0], delimited[1], delimited[2], delimited[3]);
                // Each Person object is stored in a List of Person
                personList.add(p1);

            }
            // close and flush connections
            reader.close();
            fileReader.close();
            return personList;
        } catch (Exception e) {
            return null;
        }
    }
}