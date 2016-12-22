package com.example.richo_han.tutorialapplication;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;


public class ContactInfoFragment extends Fragment {
    private static final String TAG = ContactInfoFragment.class.getName();
    private static final int MY_PERMISSIONS_TEST_RUN = 1;
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

        FloatingActionButton editFab = (FloatingActionButton) view.findViewById(R.id.edit_fab);
        editFab.setOnClickListener(clickListener);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_TEST_RUN:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Customized permission was granted!");
                    sendIntent();
                } else {

                    Log.i(TAG, "Customized permission was NOT granted.");
                    showDeniedSnackBar();
                }
                return;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_fab:
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            "com.example.richo.permissionpractice.permission.IMAGE_PROVIDER_ACTIVITY")
                            != PackageManager.PERMISSION_GRANTED) {

                        if (shouldShowRequestPermissionRationale("com.example.richo.permissionpractice.permission.IMAGE_PROVIDER_ACTIVITY")) {

                            Log.d(TAG, "Explanation needed!");
                            showDeniedSnackBar();
                        } else {

                            Log.d(TAG, "Requesting permission...");
                            requestPermissions(new String[]{"com.example.richo.permissionpractice.permission.IMAGE_PROVIDER_ACTIVITY"},
                                    MY_PERMISSIONS_TEST_RUN);
                        }
                    } else {

                        Log.d(TAG, "Permission granted!");
                        sendIntent();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void sendIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_RUN);
        shareIntent.setType("text/plain");

        try {
            if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showDeniedSnackBar() {
        Snackbar snackbar = Snackbar
                .make(getActivity().getWindow().findViewById(android.R.id.content), R.string.permissions_not_granted, Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }
}
