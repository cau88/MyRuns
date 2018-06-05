package edu.dartmouth.cs.login;

/** Commented out the code because the log said there's a null-pointer exception.
 * I couldn't find it and I didn't want the app to crash.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.dartmouth.cs.login.ManualEntry.Entry;
import edu.dartmouth.cs.login.ManualEntry.EntryAdapter;
import edu.dartmouth.cs.login.ManualEntry.ManualEntryAlertDialogFragment;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";
    private List<Entry> selectedList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EntryAdapter mAdapter;
    private String selectedActivity;

    String mDisplayDate, mDisplayTime;
    Calendar mDateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d(TAG, "onCreate()");


/*
        collectEntryData();
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
                        mIntent = new Intent(HistoryActivity.this,
                                MainActivity.class);
                        startActivity(mIntent);
                        return true;
                }
                return true;
            }
        });
    }
    private void collectEntryData(){
        Entry entry = new Entry("Activity", selectedActivity);
        selectedList.add(entry);

        entry = new Entry("Date", mDisplayDate);
        selectedList.add(entry);

        entry = new Entry("Time", mDisplayTime);
        selectedList.add(entry);

        entry = new Entry("Duration", "0");
        ManualEntryAlertDialogFragment alert = new ManualEntryAlertDialogFragment();
        selectedList.add(entry);

        entry = new Entry("Heartbeat", "0");
        selectedList.add(entry);

        mAdapter.notifyDataSetChanged(); */
    }
}
