package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public final static String EXTRA_NAME = "com.example.tutorialapp.NAME";
    public final static String EXTRA_GENDER = "com.example.tutorialapp.GENDER";
    public final static String EXTRA_EMAIL = "com.example.tutorialapp.EMAIL";
    private static final String TAG = "MainActivity";
    private String gender = "";
    private String domain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Form");

        if(savedInstanceState!=null)
            Log.d("State", "savedInstanceState not null!");

        Spinner spinner = (Spinner) findViewById(R.id.domain_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.domain_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        showStateToast("In onCreate state!");
    }

    @Override
    protected void onStart() {
        super.onStart();

        showStateToast("In onStart state!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        showStateToast("In onResume state!");
    }

    @Override
    protected void onPause() {
        super.onPause();

        showStateToast("In onPause state!");
    }

    @Override
    protected void onStop() {
        super.onStop();

        showStateToast("In onStop state!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        showStateToast("In onDestroy state!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        showStateToast("In onRestart state!");
    }

    @Override
    protected void onSaveInstanceState(Bundle onState) {
        super.onSaveInstanceState(onState);

        showStateToast("In onSaveInstanceState state!");
    }

    @Override
    protected void onRestoreInstanceState(Bundle onState) {
        super.onRestoreInstanceState(onState);
        Log.d("State", "onState == null: " + String.valueOf((onState==null)));
        showStateToast("In onRestoreInstanceState state!");
    }

    private void showStateToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        Log.d("State", text.toString());
        toast.show();
    }

    public void onGenderButtonClicked(View view) {
        switch (view.getId()){
            case R.id.radio_male:
                gender = "male";
                break;
            case R.id.radio_female:
                gender = "female";
                break;
        }
    }

    public void previewForm(View view) {
        if(!isFormCompleted()) {
            AlertDialogFragment newFragment = new AlertDialogFragment();
            newFragment.show(getSupportFragmentManager(), "alert");
        } else {
            Intent intent = new Intent(this, DisplayPreviewActivity.class);

            EditText editName = (EditText) findViewById(R.id.edit_name);
            String name = editName.getText().toString();
            intent.putExtra(EXTRA_NAME, name);

            intent.putExtra(EXTRA_GENDER, gender);

            EditText editAccount = (EditText) findViewById(R.id.edit_account);
            String account = editAccount.getText().toString();
            intent.putExtra(EXTRA_EMAIL, account + "@" + domain);
            startActivity(intent);
        }
    }

    private boolean isFormCompleted() {
        EditText editName = (EditText) findViewById(R.id.edit_name);
        EditText editAccount = (EditText) findViewById(R.id.edit_account);
        if(TextUtils.isEmpty(editAccount.getText().toString().trim()) ||
                TextUtils.isEmpty(editName.getText().toString().trim()) ||
                gender.equals("") || domain.equals(""))
            return false;
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        domain = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
