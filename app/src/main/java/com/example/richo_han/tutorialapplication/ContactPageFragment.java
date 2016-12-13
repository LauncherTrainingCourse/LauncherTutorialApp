package com.example.richo_han.tutorialapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import static android.app.Activity.RESULT_OK;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class ContactPageFragment extends Fragment {
    public final static String EXTRA_CONTACT = "com.example.richo_han.tutorialapplication.EXTRA_CONTACT";
    static final int SHOW_CONTACT_REQUEST = 1;
    static final int NEW_CONTACT_REQUEST = 2;
    static final int RESULT_NEED_UPDATE = 3;
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
        refreshContactList(mDbHelper);
    }

    /***
     * Generate ListView instance and link it to the data source.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the View instance containing ListVIew instance.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_page, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lv_contact);
        listView.setAdapter(contactAdapter);

        // Check the current orientation mode, hold true when in landscape mode.
        if(getActivity().getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
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

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                newContact(mDbHelper);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SHOW_CONTACT_REQUEST) {
            if(resultCode == RESULT_OK) {
                Contact contact = data.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);
                removeContactFromDb(contact, mDbHelper);
            } else if (resultCode == RESULT_NEED_UPDATE) {
                Contact contact = data.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);
                Contact originalContact = data.getParcelableExtra(ContactInfoActivity.ORIGINAL_CONTACT);
                AsyncUpdateTask updateTask = new AsyncUpdateTask();
                updateTask.originalContact = originalContact;
                updateTask.contact = contact;
                updateTask.helper = this.mDbHelper;
                updateTask.execute();
            }
        } else if(requestCode == NEW_CONTACT_REQUEST){
            Contact contact = data.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);
            addContactToDb(contact, this.mDbHelper);
            addContactToList(contact);
        }
    }

    private void refreshContactList(ContactReaderDbHelper dbHelper) {
        contactAdapter.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ContactReaderContract.ContactEntry.TABLE_NAME, null);
        if (!cursor.moveToFirst()) {
            initiateDb(mDbHelper, loadJSONFromAssets());
        } else {
            //getLoaderManager().initLoader(0, null, this);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Contact contact = new Contact(
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
                        ));
                addContactToList(contact);
                cursor.moveToNext();
            }
        }
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

    private void newContact(ContactReaderDbHelper helper){
        FragmentManager fragmentManager = getFragmentManager();
        NewContactFragment fragment = new NewContactFragment();

        fragment.setTargetFragment(this, NEW_CONTACT_REQUEST);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, fragment).addToBackStack(null).commit();
    }

    /**
     *
     * @param dbHelper
     * @param jsonString
     */
    private void initiateDb(ContactReaderDbHelper dbHelper, String jsonString) {
        try {
            JSONArray contacts = new JSONArray(jsonString);
            for (int i=0; i<contacts.length(); i++){
                JSONObject object = contacts.getJSONObject(i);
                Contact contact = new Contact(object.getString("name"),
                        object.getString("phone"),
                        object.getString("gender"),
                        object.getString("company"),
                        object.getString("email"));
                addContactToDb(contact, dbHelper);
                addContactToList(contact);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void addContactToDb(Contact contact, ContactReaderDbHelper helper) {
        AsyncInsertTask insertTask = new AsyncInsertTask();
        insertTask.contact = contact;
        insertTask.helper = helper;
        insertTask.execute();
    }

    private void removeContactFromDb(Contact contact, ContactReaderDbHelper helper) {
        AsyncDeleteTask deleteTask = new AsyncDeleteTask();
        deleteTask.contact = contact;
        deleteTask.helper = helper;
        deleteTask.execute();
    }

    private void addContactToList(Contact contact) {
        contactAdapter.add(contact);
        contactAdapter.notifyDataSetChanged();
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
        startActivityForResult(intent, SHOW_CONTACT_REQUEST);
    }

    private class AsyncInsertTask extends AsyncTask<Void, Void, Void> {
        Contact contact;
        ContactReaderDbHelper helper;

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_NAME, contact.name);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE, contact.phone);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER, contact.gender);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY, contact.company);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL, contact.email);

            db.insert(
                    ContactReaderContract.ContactEntry.TABLE_NAME,
                    null,
                    values);
            return null;
        }
    }

    private class AsyncDeleteTask extends AsyncTask<Void, Void, Void> {
        Contact contact;
        ContactReaderDbHelper helper;

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = helper.getWritableDatabase();

            db.delete(
                    ContactReaderContract.ContactEntry.TABLE_NAME,
                    "name = ?",
                    new String[] {contact.name}
            );

            return null;
        }

        protected void onPostExecute(Void result) {
            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            refreshContactList(mDbHelper);
                        }
                    }
            );
        }
    }

    private class AsyncUpdateTask extends AsyncTask<Void, Void, Void> {
        Contact contact, originalContact;
        ContactReaderDbHelper helper;

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_NAME, contact.name);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE, contact.phone);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER, contact.gender);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY, contact.company);
            values.put(ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL, contact.email);

            db.update(
                    ContactReaderContract.ContactEntry.TABLE_NAME,
                    values,
                    "name = ?",
                    new String[] {originalContact.name}
            );

            return null;
        }

        protected void onPostExecute(Void result) {
            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            refreshContactList(mDbHelper);
                        }
                    }
            );
        }
    }
}