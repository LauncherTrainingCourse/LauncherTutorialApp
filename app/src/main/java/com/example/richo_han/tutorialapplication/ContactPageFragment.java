package com.example.richo_han.tutorialapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class ContactPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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
        refreshContactList(mDbHelper, contactAdapter);
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
                newContact(mDbHelper, contactAdapter);
            }
        });

        return view;
    }

    private void refreshContactList(ContactReaderDbHelper dbHelper, ContactAdapter adapter) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + ContactReaderContract.ContactEntry.TABLE_NAME, null);
        if (!mCursor.moveToFirst()) {
            initiateDb(mDbHelper, contactAdapter, loadJSONFromAssets());
        } else {
            getLoaderManager().initLoader(0, null, this);
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

    private void newContact(ContactReaderDbHelper helper, ContactAdapter adapter){
        Log.d("TAG", "newContact()");
        Contact contact = new Contact("Richo Han", "+1 (000) 000-0000", "male", "ASUS", "Richo_Han@asus.com");
        addContactToDb(contact, helper);
        addContactToList(contact, adapter);
    }

    /**
     * Add each contact extracted from json string along with his/her detailed info to the input adapter.
     * @param adapter
     * @param jsonString
     */
    private void initiateDb(ContactReaderDbHelper dbHelper, ContactAdapter adapter, String jsonString) {
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
                addContactToList(contact, adapter);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void addContactToDb(Contact contact, ContactReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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
    }

    private void addContactToList(Contact contact, ContactAdapter adapter) {
        adapter.add(contact);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ContactReaderContract.ContactEntry.COLUMN_NAME_NAME,
                ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE,
                ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER,
                ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY,
                ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL
        };
        String sortOrder =
                ContactReaderContract.ContactEntry.COLUMN_NAME_NAME + " ASC";

        String select = "";
        return new CursorLoader(getActivity(), null, projection, select, null, sortOrder) {
            @Override
            public Cursor loadInBackground() {
                // You better know how to get your database.
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                return db.query(ContactReaderContract.ContactEntry.TABLE_NAME, getProjection(), getSelection(), getSelectionArgs(), null, null, getSortOrder(), null );
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
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
            addContactToList(contact, contactAdapter);
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}