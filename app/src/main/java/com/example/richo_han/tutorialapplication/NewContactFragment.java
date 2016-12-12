package com.example.richo_han.tutorialapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


public class NewContactFragment extends DialogFragment {
    public String gender = "male";

    public NewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        final View view = inflater.inflate(R.layout.fragment_new_contact, container, false);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.gender_radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio_male:
                        gender = "male";
                        break;
                    case R.id.radio_female:
                        gender = "female";
                        break;
                }
            }
        });

        Button button = (Button) view.findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent data = new Intent();
                TextInputEditText nameEditText = (TextInputEditText) view.findViewById(R.id.input_name);
                TextInputEditText phoneEditText = (TextInputEditText) view.findViewById(R.id.input_phone);
                TextInputEditText mailEditText = (TextInputEditText) view.findViewById(R.id.input_email);
                TextInputEditText companyEditText = (TextInputEditText) view.findViewById(R.id.input_company);

                Contact contact = new Contact(nameEditText.getText().toString(),
                        phoneEditText.getText().toString(),
                        gender,
                        companyEditText.getText().toString(),
                        mailEditText.getText().toString()
                );
                data.putExtra(ContactAdapter.EXTRA_CONTACT, contact);
                getTargetFragment().onActivityResult(ContactPageFragment.NEW_CONTACT_REQUEST, 0, data);
                dismiss();
            }
        });
        return view;
    }
}
