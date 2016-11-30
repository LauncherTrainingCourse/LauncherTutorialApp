package com.example.richo_han.tutorialapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactPageFragment extends Fragment {
    public final static String TAG = ContactPageFragment.class.getSimpleName();
    public final static String EXTRA_CONTACT = "com.example.richo_han.tutorialapplication.EXTRA_CONTACT";
    public ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ArrayList<Contact> arrayOfContacts = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(this.getContext(), arrayOfContacts);

        // Deal with data binding here.
        // Read json file from asset and create Contact instances for simulation.
        addContacts(contactAdapter, loadJSONFromAssets());
    }

    /***
     * Generate ListView instance and link it to the data source.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the View instance containing ListVIew instance.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_page, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lv_contact);
        listView.setAdapter(contactAdapter);

        if(view.findViewById(R.id.info_container) != null) {
            final FragmentActivity fragmentActivity = this.getActivity();
            final FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();

            if(fragmentManager.findFragmentById(R.id.info_container) == null) {
                showContactInfo(fragmentActivity, fragmentManager, contactAdapter.getItem(0));
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switchContactInfo(fragmentActivity, fragmentManager, (Contact) adapterView.getItemAtPosition(i));
                }
            });
        } else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Context context = getContext();
                    Intent intent = new Intent(context, ContactInfoActivity.class);
                    intent.putExtra(EXTRA_CONTACT, (Contact) adapterView.getItemAtPosition(i));
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

    /***
     * Used to read json file from asset directory.
     * @return json string
     */
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

    /**
     * Add each contact extracted from json string along with his/her detailed info to the input adapter.
     * @param adapter
     * @param jsonString
     */
    public void addContacts(ContactAdapter adapter, String jsonString){
        try {
            JSONArray contacts = new JSONArray(jsonString);
            for (int i=0; i<contacts.length(); i++){
                JSONObject contact = contacts.getJSONObject(i);
                adapter.add(new Contact(contact.getString("name"),
                        contact.getString("phone"),
                        contact.getString("gender"),
                        contact.getString("company"),
                        contact.getString("email")));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Add contact info to the FrameLayout.
     * @param activity
     * @param manager
     * @param contact
     */
    private void showContactInfo(FragmentActivity activity, FragmentManager manager, Contact contact){
        ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
        activity.getIntent().putExtra(EXTRA_CONTACT, contact);
        contactInfoFragment.setArguments(activity.getIntent().getExtras());

        manager.beginTransaction()
                .add(R.id.info_container, contactInfoFragment)
                .commit();
    }

    /**
     * Replace contact info with the existing one in the FrameLayout.
     * @param activity
     * @param manager
     * @param contact
     */
    private void switchContactInfo(FragmentActivity activity, FragmentManager manager, Contact contact){
        ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
        activity.getIntent().putExtra(EXTRA_CONTACT, contact);
        contactInfoFragment.setArguments(activity.getIntent().getExtras());

        manager.beginTransaction()
                .replace(R.id.info_container, contactInfoFragment)
                .commit();
    }

}