package com.example.richo_han.tutorialapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Richo_Han on 2016/11/11.
 */

public class ConfirmDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Activity activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to send the form?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        if(activity instanceof DisplayPreviewActivity)
                            ((DisplayPreviewActivity) activity).sendForm();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){

                    }
                });

        return builder.create();
    }
}
