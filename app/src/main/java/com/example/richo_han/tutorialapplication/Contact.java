package com.example.richo_han.tutorialapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Richo_Han on 2016/11/18.
 */

public class Contact implements Parcelable{
    public String name, phone, gender, company, email;

    public Contact(String name, String phone, String gender, String company, String email){
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.company = company;
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(this.name);
        out.writeString(this.phone);
        out.writeString(this.gender);
        out.writeString(this.company);
        out.writeString(this.email);
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {

        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Contact(Parcel in){
        this.name = in.readString();
        this.phone = in.readString();
        this.gender = in.readString();
        this.company = in.readString();
        this.email = in.readString();
    }
}
