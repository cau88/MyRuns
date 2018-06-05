package edu.dartmouth.cs.login.Login;
import edu.dartmouth.cs.login.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //    private static final String EMAIL = "email";
//    private static final String PW = "password";
    private static final int REQUEST_CODE_PROFILE = 0;

    EditText mUserEmail;
    EditText mUserPassword;

    private String userEmail;
    private String userPassword;
    private Boolean profileCreated; //= false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE_PROFILE)) {
            if (data != null) {
                /*profileCreated = RegisterActivity.wasProfileCreated(data);*/
                //userEmail = RegisterActivity.setEmail();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.dartmouth.cs.login.R.layout.activity_login);

        Log.d(TAG, "onCreate()");

        Toolbar appBar = findViewById(edu.dartmouth.cs.login.R.id.app_bar);
        setSupportActionBar(appBar);

//        EditText mUserEmail = findViewById(R.id.email_user_input);
//        EditText mUserPassword = findViewById(R.id.password_user_input);

        mUserEmail = findViewById(edu.dartmouth.cs.login.R.id.email_user_input);
        mUserPassword = findViewById(edu.dartmouth.cs.login.R.id.password_user_input);

        profileCreated = false;

        Button mSignInButton = findViewById(edu.dartmouth.cs.login.R.id.sign_in_button);
        Button mRegisterButton = findViewById(edu.dartmouth.cs.login.R.id.register_button);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkProfile();
            }
        });
    }

    public void getProfile() {
        /*
        SharedPreferences mPref = getSharedPreferences("edu.dartmouth.cs.login", 0);
        userEmail = mPref.getString("email", null);
        userPassword = mPref.getString("password", null);
        //profileCreated = mPref.getBoolean("profile created", profileCreated);*/

        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        // Load the user email

        /*userEmail = getString(R.string.preference_key_profile_email);
        userPassword = getString(R.string.preference_key_profile_password);*/

        // Load the user email

        mKey = getString(R.string.preference_key_profile_email);
        userEmail = mPrefs.getString(mKey, "");

        // Load the user password

        mKey = getString(R.string.preference_key_profile_password);
        userPassword = mPrefs.getString(mKey, "");


        //userEmail = getString(R.string.register_email);
        //userPassword = getString(R.string.register_password);

    }


    private void checkProfile() {

        getProfile();

        //These are empty
        /*
        Toast.makeText(LoginActivity.this, userEmail, Toast.LENGTH_SHORT).show();
        Toast.makeText(LoginActivity.this, userPassword, Toast.LENGTH_SHORT).show();

        Toast.makeText(LoginActivity.this, inputEm, Toast.LENGTH_SHORT).show();
        Toast.makeText(LoginActivity.this, mUserPassword.getText(), Toast.LENGTH_SHORT).show();
        */

        String inputEm = (mUserEmail.getText()).toString();
        String inputPw = (mUserPassword.getText()).toString();

        if ((userEmail.equals(inputEm) && userPassword.equals(inputPw)) && (!inputEm.equals("") && !inputPw.equals(""))) {
            //check if edittext input matches above
            Intent intent = new Intent(LoginActivity.this, edu.dartmouth.cs.login.MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, edu.dartmouth.cs.login.R.string.incorrect_login_text, Toast.LENGTH_SHORT).show();
        }
    }

}