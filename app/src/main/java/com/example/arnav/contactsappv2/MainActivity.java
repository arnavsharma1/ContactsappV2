// Homework Assignment Number 4                                                  //
// Class: CS6301 User Interface Design                                           //
// Group of 2 students                                                           //
//-------------------------------------------------------------------------------//
// Name: ARJUN SHROFF MAHESH       Net ID: axs144930                            //
//-------------------------------------------------------------------------------//
// Name: ARNAV SHARMA               Net ID: axs144130                            //
//-------------------------------------------------------------------------------//
// Date created: 03.19.2015                                                      //
///////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//
// This program manages the main screen of the app including action bar buttons. //

package com.example.arnav.contactsappv2;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

    int position;
    ListView mListView;
    boolean isEdit = false;
    DataAdapter mDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView =(ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);



        mDataAdapter = new DataAdapter(this,loadArrayFromFile());
        mListView.setAdapter(mDataAdapter);



    }
    ArrayList<Contacts> mContacts ;
    private ArrayList<Contacts> loadArrayFromFile(){
        mContacts = new ArrayList<>();
        try {



            BufferedReader reader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "contacts5.txt")));
            String line;

            //Read each line

            while ((line = reader.readLine()) != null) {

                //Split to separate the name from the capital
                String[] RowData = line.split(",");

                //Create a Contacts object for this row's data.
                Contacts cur = new Contacts();
                cur.setName(RowData[0]);
                cur.setLname(RowData[1]);
                cur.setPhone(RowData[2]);
                cur.setEmail(RowData[3]);

                mContacts.add(cur);
                Collections.sort(mContacts, new Comparator<Contacts>() {
                    @Override
                    public int compare(Contacts c1, Contacts c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });
                //Add the Contacts object to the ArrayList (in this case we are the ArrayList).

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mContacts;
    }
    @Override
    public void onBackPressed() {
        if(!isEdit)
        {
            finish();
        }
        else
        {
            isEdit = false;
            mListView.setAdapter(mDataAdapter);
            invalidateOptionsMenu();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isEdit)
        {
            getMenuInflater().inflate(R.menu.edit, menu);

        }
        else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        isEdit = false;

        final Intent intent1=new Intent(this,newindow.class);
        switch (id)
        {
            case R.id.edit:

                intent1.putExtra("check","edit");

                intent1.putExtra("name", mDataAdapter.mContacts.get(position).getName());
                intent1.putExtra("lname",mDataAdapter.mContacts.get(position).getLname());
                intent1.putExtra("email",mDataAdapter.mContacts.get(position).getEmail());
                intent1.putExtra("phone",mDataAdapter.mContacts.get(position).getPhone());
                intent1.putExtra("position",position);
                startActivity(intent1);
                break;
            case R.id.delete:
                //Popup window for confirming delete
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                intent1.putExtra("check","delete");

                                intent1.putExtra("name", mDataAdapter.mContacts.get(position).getName());
                                intent1.putExtra("lname",mDataAdapter.mContacts.get(position).getLname());
                                intent1.putExtra("email",mDataAdapter.mContacts.get(position).getEmail());
                                intent1.putExtra("phone",mDataAdapter.mContacts.get(position).getPhone());
                                startActivity(intent1);

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                //  startActivity(intent1);
                break;
            case R.id.add:
                intent1.putExtra("check","add");
                startActivity(intent1);
                break;

        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataAdapter = new DataAdapter(this,loadArrayFromFile());
        mListView.setAdapter(mDataAdapter);
        invalidateOptionsMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.position = position;
        isEdit = true;
        invalidateOptionsMenu();


    }
}