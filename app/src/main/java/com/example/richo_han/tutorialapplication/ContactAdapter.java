package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richo_Han on 2016/11/18.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    public final static String TAG = ContactAdapter.class.getSimpleName();
    public ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        this.contacts = contacts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Contact contact = getItem(position);

        // What does convertView do?
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.contact_name);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.contact_phone);
        tvName.setText(contact.name);
        tvPhone.setText(contact.phone);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "" + position);
            }
        });

        return convertView;
    }
}
