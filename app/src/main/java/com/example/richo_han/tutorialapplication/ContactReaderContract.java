package com.example.richo_han.tutorialapplication;

import android.provider.BaseColumns;

/**
 * Created by Richo_Han on 2016/12/2.
 */

public final class ContactReaderContract {

    public ContactReaderContract() {}

    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_COMPANY = "company";
        public static final String COLUMN_NAME_EMAIL = "email";

    }
}
