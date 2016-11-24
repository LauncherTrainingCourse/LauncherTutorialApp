package com.example.richo_han.tutorialapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class stores detailed information of a contact.
 * Parcelable is implemented for data serialization.
 * Make it more easier to pass the created instance to between fragment and activities using intent.
 */

public class Contact implements Parcelable{
    public String name, phone, gender, company, email;
    private boolean isFavorite = false;

    public Contact(String name, String phone, String gender, String company, String email){
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.company = company;
        this.email = email;
    }

    public void setFavorite(boolean b){
        this.isFavorite = b;
    }

    public boolean getFavorite(){
        return this.isFavorite;
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
        out.writeByte((byte) (isFavorite ? 1 : 0)); // if isFavorite == true, byte == 1
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
        this.isFavorite = in.readByte() != 0; // isFavorite == true if byte != 0
    }
}
