package edu.dartmouth.cs.login.ManualEntry;
import edu.dartmouth.cs.login.*;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManualEntryActivity extends AppCompatActivity {
    private List<Entry> entryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EntryAdapter mAdapter;
    private String selectedActivity;

    String mDisplayDate, mDisplayTime;
    Calendar mDateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_manual_entry);
        setContentView(R.layout.activity_manual_entry);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new EntryAdapter(entryList);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        updateDateDisplay();
        updateTimeDisplay();

        getActivity();
        prepareEntryData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent mIntent;
                Entry entry = entryList.get(position);
                switch(position){
                    case 1:
                        onDateClicked(view);
                    case 2:
                        onTimeClicked(view);
                    case 3:

                }
                //Toast.makeText(getApplicationContext(), entry.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

//    String input_value = spinner1.getSelectedItem().toString();
//
//    selectedActivity = find



    private void prepareEntryData() {
        Entry entry = new Entry("Activity", selectedActivity);
        entryList.add(entry);

        entry = new Entry("Date", mDisplayDate);
        entryList.add(entry);

        entry = new Entry("Time", mDisplayTime);
        entryList.add(entry);

        entry = new Entry("Duration", "0");
        ManualEntryAlertDialogFragment alert = new ManualEntryAlertDialogFragment();
        entryList.add(entry);

        entry = new Entry("Calorie", "0");
        entryList.add(entry);

        entry = new Entry("Heartbeat", "0");
        entryList.add(entry);

        entry = new Entry("Comment", "");
        entryList.add(entry);

        mAdapter.notifyDataSetChanged();
    }

    public void getActivity() {

        /*SharedPreferences mPref = getSharedPreferences("edu.dartmouth.cs.login", 0);
        userEmail = mPref.getString("email", null);
        userPassword = mPref.getString("password", null);
        //profileCreated = mPref.getBoolean("profile created", profileCreated);*/

        String mActivity = "entry_preferences";
        SharedPreferences mPrefs = getSharedPreferences(mActivity, MODE_PRIVATE);

        // Load the user email

        /*userEmail = getString(R.string.preference_key_profile_email);
        userPassword = getString(R.string.preference_key_profile_password);*/

        // Load the user email

        mActivity = "Activity";
        selectedActivity = mPrefs.getString(mActivity, "");

        //userEmail = getString(R.string.register_email);
        //userPassword = getString(R.string.register_password);

    }

    public void onTimeClicked(View v) {

        TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mDateAndTime.set(Calendar.MINUTE, minute);
                updateTimeDisplay();
            }
        };

        new TimePickerDialog(ManualEntryActivity.this, mTimeListener,
                mDateAndTime.get(Calendar.HOUR_OF_DAY),
                mDateAndTime.get(Calendar.MINUTE), true).show();


    }

    public void onDateClicked(View v) {

        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mDateAndTime.set(Calendar.YEAR, year);
                mDateAndTime.set(Calendar.MONTH, monthOfYear);
                mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateDisplay();
            }
        };

        new DatePickerDialog(ManualEntryActivity.this, mDateListener,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH)).show();


    }

//    private void updateDateAndTimeDisplay() {
//        mDisplayDateTime.setText(DateUtils.formatDateTime(this,
//                mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
//                        | DateUtils.FORMAT_SHOW_TIME));
//    }

    private void updateDateDisplay() {
        /*mDisplayDateTime.setText(DateUtils.formatDateTime(this,
                mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_TIME));*/
//        mDisplayDate.setText(DateUtils.FORMAT_SHOW_DATE);
        mDisplayDate = (DateUtils.formatDateTime(this,
                mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
    }
    private void updateTimeDisplay() {
        /*mDisplayDateTime.setText(DateUtils.formatDateTime(this,
                mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_TIME));*/
//        mDisplayTime.setText(DateUtils.FORMAT_SHOW_TIME);
        mDisplayTime = (DateUtils.formatDateTime(this,
                mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }
}