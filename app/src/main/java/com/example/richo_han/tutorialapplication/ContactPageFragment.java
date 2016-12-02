package com.example.richo_han.tutorialapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public final static String EXTRA_CONTACT = "com.example.richo_han.tutorialapplication.EXTRA_CONTACT";
    public ContactAdapter contactAdapter;
    ContactReaderDbHelper mDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ArrayList<Contact> arrayOfContacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(this.getContext(), arrayOfContacts);

        // Deal with data binding here.
        // Read json file from asset and create Contact instances for simulation.
        mDbHelper = new ContactReaderDbHelper(getContext());
        initiateDb(mDbHelper);
        addContactsToList(findContactsFromDb(mDbHelper), contactAdapter);
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

        // Check the current orientation mode, hold true when in landscape mode.
        if(view.findViewById(R.id.info_container) != null) {
            final FragmentActivity fragmentActivity = this.getActivity();
            final FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();

            // Will have duplicate fragment if not wrapped in the if statement
            if(fragmentManager.findFragmentById(R.id.info_container) == null) {
                showContactInfo(fragmentActivity, fragmentManager, contactAdapter.getItem(0));
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showContactInfo(fragmentActivity, fragmentManager, (Contact) adapterView.getItemAtPosition(i));
                }
            });
        } else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact contact = (Contact) adapterView.getItemAtPosition(i);
                    popupContactInfo(contact);
                }
            });
        }

        return view;
    }

    private void initiateDb(ContactReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + ContactReaderContract.ContactEntry.TABLE_NAME, null);
        if (!mCursor.moveToFirst()) {
            addContacts(mDbHelper, contactAdapter, loadJSONFromAssets());
        }
        findContactsFromDb(mDbHelper);
    }

    /***
     * Used to read json file from asset directory.
     * @return json string
     */
    private String loadJSONFromAssets() {
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
    private void addContacts(ContactReaderDbHelper dbHelper, ContactAdapter adapter, String jsonString) {
        try {
            JSONArray contacts = new JSONArray(jsonString);
            for (int i=0; i<contacts.length(); i++){
                JSONObject contact = contacts.getJSONObject(i);
                addContactToDb(dbHelper, contact);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void addContactToDb(ContactReaderDbHelper dbHelper, JSONObject contact) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_NAME, contact.getString("name"));
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE, contact.getString("phone"));
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER, contact.getString("gender"));
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY, contact.getString("company"));
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL, contact.getString("email"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        long newRowId;
        newRowId = db.insert(
                ContactReaderContract.ContactEntry.TABLE_NAME,
                null,
                values);
    }

    private Cursor findContactsFromDb(ContactReaderDbHelper dbHelper) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                ContactReaderContract.ContactEntry.COLUMN_NAME_NAME,
                ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE,
                ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER,
                ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY,
                ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL
        };
        String sortOrder =
                ContactReaderContract.ContactEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                ContactReaderContract.ContactEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    private void addContactsToList(Cursor cursor, ContactAdapter adapter) {
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            adapter.add(new Contact(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactReaderContract.ContactEntry.COLUMN_NAME_NAME)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL)
                    )));
            cursor.moveToNext();
        }
    }

    /**
     * Add contact info to the FrameLayout or replace with existing one.
     * @param activity
     * @param manager
     * @param contact
     */
    private void showContactInfo(FragmentActivity activity, FragmentManager manager, Contact contact){
        ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
        activity.getIntent().putExtra(EXTRA_CONTACT, contact);
        contactInfoFragment.setArguments(activity.getIntent().getExtras());

        // replace() == .remove().add(), so the function will work given no existing fragment.
        manager.beginTransaction()
                .replace(R.id.info_container, contactInfoFragment)
                .commit();
    }

    private void popupContactInfo(Contact contact) {
        Context context = getContext();
        Intent intent = new Intent(context, ContactInfoActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        context.startActivity(intent);
    }
}