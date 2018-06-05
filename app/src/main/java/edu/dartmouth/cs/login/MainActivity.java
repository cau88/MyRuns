package edu.dartmouth.cs.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import edu.dartmouth.cs.login.Login.RegisterActivity;
import edu.dartmouth.cs.login.ManualEntry.ManualEntryActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String chosenActivity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");

        Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        BottomNavigationView navBar = findViewById(R.id.navigation);
        navBar.getMenu().getItem(0).setChecked(true);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mIntent;
                switch (item.getItemId()){
                    case R.id.navigation_start:
                        return true;
                    case R.id.navigation_history:
                        //SharedPreferences? To retrieve previously inputted info
                        mIntent = new Intent(MainActivity.this,
                                HistoryActivity.class);
                        startActivity(mIntent);
                        return true;
                }
                return true;
            }
        });

        final Spinner spinner1 = (Spinner) findViewById(R.id.input_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> input_adapter = ArrayAdapter.createFromResource(this,
                R.array.input_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        input_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(input_adapter);


        final Spinner spinner2 = (Spinner) findViewById(R.id.activity_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> activity_adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        activity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(activity_adapter);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(
                    AdapterView<?> parent, View view, int position, long id) {
                //String selected = parent.getItemAtPosition(position).toString();
                if(position == 0){
                    spinner2.setEnabled(true);
                    spinner2.setAdapter(activity_adapter);

                }
                if(position == 1){
                    spinner2.setEnabled(true);
                    spinner2.setAdapter(activity_adapter);
                }
                if(position == 2){
                    spinner2.setEnabled(false);
                    spinner2.setAdapter(activity_adapter);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        final String[] chosenActivity = new String[1];
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            //String chosenActivity = "";
            public void onItemSelected(
                    AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                chosenActivity = selected;
/*                 if(position == 0){
                    chosenActivity="@string/activity_array[0]";

                }
                if(position == 1){
                    spinner2.setEnabled(true);
                    spinner2.setAdapter(activity_adapter);
                }
                if(position == 2){
                    spinner2.setEnabled(false);
                    spinner2.setAdapter(activity_adapter);
                }*/
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton startFAB = findViewById(R.id.start_FAB);

        startFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent;
                String[] input_array = getResources().getStringArray(R.array.input_array);
                String input_value = spinner1.getSelectedItem().toString();
                saveActivity();

                if(input_value.equals(input_array[0]))
                {
                    mIntent = new Intent(MainActivity.this, ManualEntryActivity.class);
                    mIntent.putExtra("Activity", chosenActivity);
                    startActivity(mIntent);
                }
                else if(input_value.equals(input_array[1]))
                {

                    mIntent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(mIntent);
                }
                else if(input_value.equals(input_array[2]))
                {
                    mIntent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(mIntent);
                }

            }
        });

    }

    // load the user data from shared preferences if there is no data make sure
    // that we set it to something reasonable
    private void saveActivity() {

        Log.d(TAG, "saveActivity()");

        // Getting the shared preferences editor

        String mActivity = "entry_preferences";
        SharedPreferences mPrefs = getSharedPreferences(mActivity, MODE_PRIVATE);


        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.clear();

        // Save activity information
        mActivity = "Activity";
//        R.id.activity_spinner
        String mActVal = chosenActivity;
        mEditor.putString(mActivity, mActVal);

        // Commit all the changes into the shared preference
        mEditor.apply();

    }


    /*
    public boolean onSelectAutomatic{}
    public boolean onSelectGPS{}
    public boolean onSelectManual()*/

    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.navigation, menu);
        getMenuInflater().inflate(R.menu.navigation2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent mIntent;
        switch (item.getItemId()) {
            case R.id.navigation_settings:
                mIntent = new Intent(MainActivity.this,
                        SettingsActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.navigation_editProf:
                //SharedPreferences? To retrieve previously inputted info
                mIntent = new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(mIntent);
                return true;
        }
        return false;
    }


}
