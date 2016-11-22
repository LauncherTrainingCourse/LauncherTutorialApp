package com.example.richo_han.tutorialapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactPageFragment extends Fragment {
    public final static String TAG = ContactPageFragment.class.getSimpleName();
    public static final String ARG_PAGE = "ARG_PAGE";
    public ContactAdapter contactAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ArrayList<Contact> arrayOfContacts = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(this.getContext(), arrayOfContacts);

        // Deal with data binding here.
        try {
            JSONArray contacts = new JSONArray(loadJSONFromAssets());
            for (int i=0; i<contacts.length(); i++){
                JSONObject contact = contacts.getJSONObject(i);
                contactAdapter.add(new Contact(contact.getString("name"),
                        contact.getString("phone"),
                        contact.getString("gender"),
                        contact.getString("company"),
                        contact.getString("email")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_page, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lv_contact);
        listView.setAdapter(contactAdapter);
        return view;
    }

    public String loadJSONFromAssets() {
        String json = null;
        try {
            InputStream inputStream = this.getContext().getAssets().open("contacts_sample.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
