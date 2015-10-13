// Homework Assignment Number 5                                               //
// Class: CS6301 User Interface Design                                        //
// Group of 2 students                                                        //
//----------------------------------------------------------------------------//
// Name: ARJUN SHROFF MAHESH       Net ID: axs144930                         //
//----------------------------------------------------------------------------//
// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//
// Basic Data class to hold a contacts name, phone and email. 	              //

package com.example.arnav.contactsappv2;

public class Contacts {

    private String name;
    private String Lname;
    private String phone;
    private String email;

    public Contacts(String name, String lname, String phone, String email) {
        this.name = name;
        Lname = lname;
        this.phone = phone;
        this.email = email;
    }
    public Contacts()
    {

    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLname() {
        return Lname;
    }
    public void setLname(String Lname) {
        this.Lname = Lname;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


}