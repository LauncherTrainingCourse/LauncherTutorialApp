package com.example.richo_han.tutorialapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactPageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public ContactAdapter contactAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ArrayList<Contact> arrayOfContacts = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(this.getContext(), arrayOfContacts);

        /* Deal with data binding here.
        contactAdapter.add(new Contact("Richo"));
        contactAdapter.add(new Contact("John"));
        contactAdapter.add(new Contact("Jack"));
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_page, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lv_contact);
        listView.setAdapter(contactAdapter);
        return view;
    }

}
