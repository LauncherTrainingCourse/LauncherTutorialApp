package com.example.richo_han.tutorialapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ContactInfoFragment extends Fragment {
    public Contact contact;

    public ContactInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);

        Intent intent = getActivity().getIntent();
        contact = intent.getParcelableExtra(ContactPageFragment.EXTRA_CONTACT);

        ImageView ivPhoto = (ImageView) view.findViewById(R.id.contact_info_photo);
        TextView tvName = (TextView) view.findViewById(R.id.contact_info_name);
        TextView tvPhone = (TextView) view.findViewById(R.id.contact_info_phone);
        TextView tvCompany= (TextView) view.findViewById(R.id.contact_info_company);
        TextView tvEmail = (TextView) view.findViewById(R.id.contact_info_email);

        if("male".equals(contact.gender)) {
            ivPhoto.setImageResource(R.drawable.steve);
        } else {
            ivPhoto.setImageResource(R.drawable.kristy);
        }

        tvName.setText(contact.name);
        tvPhone.setText(contact.phone);
        tvCompany.setText(contact.company);
        tvEmail.setText(contact.email);

        return view;
    }

}
