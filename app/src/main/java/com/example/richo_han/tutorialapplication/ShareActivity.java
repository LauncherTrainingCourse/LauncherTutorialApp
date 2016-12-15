package com.example.richo_han.tutorialapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShareActivity extends Activity {
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line,
                null,
                new String[] { ContactsContract.Contacts.DISPLAY_NAME },
                new int[] { android.R.id.text1 },
                0);
        ((AutoCompleteTextView) (findViewById(R.id.contact_input))).setAdapter(mAdapter);

        mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                return getCursor(str);
            } });

        mAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                int index = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                return cur.getString(index);
            }});

        Intent intent = getIntent();
        String title = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String link = intent.getStringExtra(Intent.EXTRA_TEXT);

        ((TextView) (findViewById(R.id.news_title))).setText(title);
        ((TextView) (findViewById(R.id.news_link))).setText(link);
    }

    public Cursor getCursor(CharSequence str) {
        String select = "(" + ContactsContract.Contacts.DISPLAY_NAME + " LIKE ? )";
        String[]  selectArgs = { "%" + str + "%" };
        String[] contactsProjection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.LOOKUP_KEY,  };

        return getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, contactsProjection, select, selectArgs, null);
    }
}
