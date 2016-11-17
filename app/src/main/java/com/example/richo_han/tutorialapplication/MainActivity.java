package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private String gender = "";
    private String domain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

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
        showStateToast("In onRestoreInstanceState state!");
    }

    /**
     * Show toast that describe the current state in the app's life cycle.
     * @param text
     */
    private void showStateToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        Log.i("State", text.toString());
        toast.show();
    }

    /**
     * Save user's selection on gender.
     * @param view
     */
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

    /**
     * Start another activity to show the preview of user's form registration.
     * Will alert if user did not complete form.
     * @param view
     */
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

    /**
     * Check if the form is completed.
     * @return
     */
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

    /* Callback method to be invoked when the selection disappears
     * from this view.The selection can disappear for instance when
     * touch is activated or when the adapter becomes empty. */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                Log.i("Toolbar", "Favorite button pushed.");
                return true;
            case R.id.action_settings:
                Log.i("Toolbar", "Settings button pushed.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
