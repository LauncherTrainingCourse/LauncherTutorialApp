package com.example.richo_han.tutorialapplication;

import android.provider.BaseColumns;

/**
 * Created by Richo_Han on 2016/12/2.
 */

public final class ContactReaderContract {

    public ContactReaderContract() {}

    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
