package com.example.richo_han.tutorialapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Richo_Han on 2016/11/14.
 */

public class AlertDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle onSavedInstance) {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Form not completed!")
                .setPositiveButton("I got it.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                    }
                });

        return builder.create();
    }
}
