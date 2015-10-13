// Homework Assignment Number 4                                               //
// Class: CS6301 User Interface Design                                        //

// Name: ARNAV SHARMA              Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 03.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//
// This program displays the contacts in a list view on main screen. 	      //

package com.example.arnav.contactsappv2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {
    ArrayList<Contacts> mContacts ;
    Context context;
    DataAdapter( Context mContext,ArrayList<Contacts> contacts)
    {
        this.mContacts = contacts;

        this.context = mContext;
    }

    @Override
    public int getCount() {

        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mylayout, null);

        TextView mTextView = (TextView) view.findViewById(R.id.textView);
        TextView mTextView1 = (TextView) view.findViewById(R.id.textView2);
        mTextView.setText(mContacts.get(position).getName() +" " + mContacts.get(position).getLname());
        mTextView1.setText(mContacts.get(position).getPhone());



        return view;
    }


}
