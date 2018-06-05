package edu.dartmouth.cs.login.Login;
import edu.dartmouth.cs.login.*;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.hardware.Camera;
import android.graphics.Bitmap;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.net.Uri;
import android.support.v4.app.DialogFragment;

import java.io.File;

import com.soundcloud.android.crop.Crop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import edu.dartmouth.cs.login.MyRunsDialogFragment;

//import com.example.android.basicpermissions.camera.CameraPreviewActivity;


public class RegisterActivity extends AppCompatActivity {

    public static int required = 0;

    public static final int REQUEST_CODE_TAKE_FROM_CAMERA = 0;
    private static final String URI_INSTANCE_STATE_KEY = "saved_uri";

    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private boolean isTakenFromCamera;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

//        ImageView photo_emoji = findViewById(R.id.open_camera_button);
//        photo_emoji.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getCameraPermission();
//            }
//        });

        Button photo_emoji = findViewById(R.id.change_profile_pic_button);
        photo_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCameraPermission();
            }
        });

        /*Button register_button = findViewById(R.id.submit_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClicked(view);
            }
        });*/

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.register_button:
                onSaveClicked();
                return true;
        }
        return false;
    }

//    // create an action bar button
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.xml.regappbar_menu, menu);
//        getMenuInflater().inflate(R.menu.activity_login, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    // handle button activities
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
////        if (id == R.id.mybutton) {
////            // do something here
////        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the image capture uri before the activity goes into background
        outState.putParcelable(URI_INSTANCE_STATE_KEY, mImageCaptureUri);
    }


    /*public void onSaveClicked(View v) {

        required = onMakeRequired();
//
//        //Toast.makeText(getApplicationContext(), Integer.toString(required), Toast.LENGTH_SHORT).show();
//
//        // Save all information from the screen into a "shared preferences"
//        // using private helper function
//
        if (required < 1) {
            saveProfile();

            Toast.makeText(getApplicationContext(),
                    getString(R.string.registration_confirmation), Toast.LENGTH_SHORT).show();

            Intent mIntent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(mIntent);
        }
//
//        // Save picture
//        saveSnap();
    }*/

    public void onSaveClicked() {

        required = onMakeRequired();
//
//        //Toast.makeText(getApplicationContext(), Integer.toString(required), Toast.LENGTH_SHORT).show();
//
//        // Save all information from the screen into a "shared preferences"
//        // using private helper function
//
        if (required < 1) {
            saveProfile();

            Toast.makeText(getApplicationContext(),
                    getString(R.string.registration_confirmation), Toast.LENGTH_SHORT).show();

            Intent mIntent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(mIntent);
        }
//
//        // Save picture
//        saveSnap();
    }


    public void onChangePhotoClicked(View v) {
        // changing the profile image, show the dialog asking the user
        // to choose between taking a picture
        // Go to MyRunsDialogFragment for details.
        displayDialog(MyRunsDialogFragment.DIALOG_ID_PHOTO_PICKER);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            mImageView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void displayDialog(int id) {
        //DialogFragment fragment = MyRunsDialogFragment.newInstance(id);
        MyRunsDialogFragment fragment = MyRunsDialogFragment.newInstance(id);
        fragment.show(getFragmentManager(),
                getString(R.string.dialog_fragment_tag_photo_picker));
    }

    public void onPhotoPickerItemSelected(int item) {
        Intent intent;

        switch (item) {

            case MyRunsDialogFragment.ID_PHOTO_PICKER_FROM_CAMERA:
                // Take photo from camera，
                // Construct an intent with action
                // MediaStore.ACTION_IMAGE_CAPTURE
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Construct temporary image path and name to save the taken
                // photo
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                /**
                 This was the previous code to generate a URI. This was throwing an exception -
                 "android.os.StrictMode.onFileUriExposed" in Android N.
                 This was because StrictMode prevents passing URIs with a file:// scheme. Once you
                 set the target SDK to 24, then the file:// URI scheme is no longer supported because the
                 security is exposed. You can change the  targetSDK version to be <24, to use the following code.
                 The new code as written above works nevertheless.


                 mImageCaptureUri = Uri.fromFile(new File(Environment
                 .getExternalStorageDirectory(), "tmp_"
                 + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                 **/

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                try {
                    // Start a camera capturing activity
                    // REQUEST_CODE_TAKE_FROM_CAMERA is an integer tag you
                    // defined to identify the activity in onActivityResult()
                    // when it returns
                    startActivityForResult(intent, REQUEST_CODE_TAKE_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                isTakenFromCamera = true;
                break;

            default:
                return;
        }

    }


    private void loadSnap() {


        // Load profile photo from internal storage
        try {
            FileInputStream fis = openFileInput(getString(R.string.profile_photo_file_name));
            Bitmap bmap = BitmapFactory.decodeStream(fis);
            mImageView.setImageBitmap(bmap);
            fis.close();
        } catch (IOException e) {
            // Default profile photo if no photo saved before.
            mImageView.setImageResource(R.drawable.default_profile);
        }
    }

    private void saveSnap() {

        // Commit all the changes into preference file
        // Save profile image into internal storage.
        mImageView.buildDrawingCache();
        Bitmap bmap = mImageView.getDrawingCache();
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.profile_photo_file_name), MODE_PRIVATE);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int onMakeRequired() {
        int required = 0;
//        if ((((EditText) findViewById(R.id.register_name)).getText().toString()).equals("")) {
//            ((EditText) findViewById(R.id.register_name)).setError("Name is required");
//            required++;
//        }
        if ((((EditText) findViewById(R.id.register_email_text)).getText().toString()).equals("")) {
            ((EditText) findViewById(R.id.register_email_text)).setError("Email is required");
            required++;
        }
        if (!((((EditText) findViewById(R.id.register_email_text)).getText().toString()).contains("@"))) {
            ((EditText) findViewById(R.id.register_email_text)).setError("Your email requires a domain");
            required++;
        }
        if ((((EditText) findViewById(R.id.register_password_text)).getText().toString()).equals("")) {
            ((EditText) findViewById(R.id.register_password_text)).setError("Password is required");
            required++;
        }
//        if ((((EditText) findViewById(R.id.register_phone)).getText().toString()).equals("")) {
//            ((EditText) findViewById(R.id.register_phone)).setError("Phone # is required");
//            required++;
//        }
//        if ((((EditText) findViewById(R.id.register_major)).getText().toString()).equals("")) {
//            ((EditText) findViewById(R.id.register_major)).setError("Major is required");
//            required++;
//        }
//        if ((((EditText) findViewById(R.id.register_class_year)).getText().toString()).equals("")) {
//            ((EditText) findViewById(R.id.register_class_year)).setError("Class year is required");
//            required++;
//        }

        return required;
    }

    // load the user data from shared preferences if there is no data make sure
    // that we set it to something reasonable
    private void saveProfile() {

        Log.d(TAG, "saveProfile()");

        // Getting the shared preferences editor

        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.clear();

        // Save email information

        mKey = getString(R.string.preference_key_profile_email);
        String mValue = ((EditText) findViewById(R.id.register_email_text))
                .getText().toString();
        mEditor.putString(mKey, mValue);

        // Save password information

        mKey = getString(R.string.preference_key_profile_password);
        mValue = ((EditText) findViewById(R.id.register_password_text))
                .getText().toString();
        mEditor.putString(mKey, mValue);

        // Read which index the radio is checked.

        // edit this out and use as a debug example
        // interesting bug because you try and write an int to a string

        mKey = getString(R.string.preference_key_profile_gender);

        RadioGroup mRadioGroup = findViewById(R.id.radioGender);
        int mIntValue = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));
        mEditor.putInt(mKey, mIntValue);

        // Save phone information

        mKey = getString(R.string.preference_key_profile_phone);
        mValue = ((EditText) findViewById(R.id.register_phone))
                .getText().toString();
        mEditor.putString(mKey, mValue);

        // Save major information

        mKey = getString(R.string.preference_key_profile_major);
        mValue = ((EditText) findViewById(R.id.register_major))
                .getText().toString();
        mEditor.putString(mKey, mValue);

        // Save year information

        mKey = getString(R.string.preference_key_profile_year);
        mValue = ((EditText) findViewById(R.id.register_class_year))
                .getText().toString();
        mEditor.putString(mKey, mValue);

        // Commit all the changes into the shared preference
        mEditor.apply();

//        Toast.makeText(getApplicationContext(), "saved name: " + mValue,
//                Toast.LENGTH_SHORT).show();

    }

 /*   public void setNegativeForCam(AlertDialog.Builder build){
        build.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "onCancelDialog");
                Toast.makeText(getApplicationContext(),
                        "Camera access denied", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    } */



    public void getCameraPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Permission is not granted
            // Should we show an explanation to users the reason that we need the permission?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.CAMERA)) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(R.string.explain_permission_request);
                builder.setPositiveButton(RegisterActivity.this.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
                builder.setNegativeButton(RegisterActivity.this.getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Log.d(TAG, "onCancelDialog");
                                Toast.makeText(getApplicationContext(),
                                        "Camera access denied", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                    }
                });

                builder.show();

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // What does the below comment mean?
                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
//        else {
//            // Permission has already been granted
//            //onRequestPermissionsResult();
//
//            //int permissions[] = new int[2]; //
//            int permissions[] = {getPackageManager().PERMISSION_GRANTED, getPackageManager().PERMISSION_DENIED};
//
//
//            onRequestPermissionsResult(MY_PERMISSIONS_REQUEST_CAMERA, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, permissions);
////            Intent intent = new Intent(RegisterActivity.this, CAMERA_SERVICE.getClass());
////            startActivity(intent);
//
//            /* CBT -- Intent intent = new Intent(RegisterActivity.this, CAMERA_SERVICE.getClass());
//            startActivity(intent); -- Look at below method*/
//        }
    }



    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);

                    //from android developer site
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }

//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);

            ImageView photo_emoji = findViewById(R.id.open_camera_button);
            photo_emoji.setImageBitmap(imageBitmap);
        }

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_TAKE_FROM_CAMERA:
                // Send image taken from camera for cropping
                beginCrop(mImageCaptureUri);
                break;

            case Crop.REQUEST_CROP: //We changed the RequestCode to the one being used by the library.
                // Update image view after image crop
                handleCrop(resultCode, data);

                // Delete temporary image taken by camera after crop.
                if (isTakenFromCamera) {
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists())
                        f.delete();
                }

                break;
        }

    }

}





//    public void setCamera(Camera camera) {
//        Manifest.permission.CAMERA
//        if (mCamera == camera) { return; }
//
//        //stopPreviewAndFreeCamera();
//
//        mCamera = camera;
//
//        if (mCamera != null) {
////            List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
////            mSupportedPreviewSizes = localSizes;
////            requestLayout();
////
////            try {
////                mCamera.setPreviewDisplay(mHolder);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//            // Important: Call startPreview() to start updating the preview
//            // surface. Preview must be started before you can take a picture.
//            mCamera.startPreview();
//        }
//    }





/*    public void startCamera() {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent); */

//
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//
//        {
//            // Permission is not granted
//            // Should we show an explanation to users the reason that we need the permission?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
//                    Manifest.permission.CAMERA))
//
//            {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                Toast.makeText(RegisterActivity.this, R.string.explain_permission_request, Toast.LENGTH_SHORT).show();
//
//            } else
//
//            {
//
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(RegisterActivity.this,
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//
//                // MY_PERMISSIONS_REQUEST_CAMERA is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//
//            // Permission has already been granted
//            //Intent intent = new Intent(RegisterActivity.this, Camera)
//            Intent intent = new Intent(RegisterActivity.this, CAMERA_SERVICE.getClass());
//            startActivity(intent);
//        }
/*    }

} */










//package edu.dartmouth.cs.login;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.util.HashSet;
//import java.util.Set;
//
////import com.example.android.basicpermissions.camera.CameraPreviewActivity;
//
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private static final String TAG = "RegisterActivity";
//    private static final String USER_EMAIL = "user_email";
//    private static final String USER_PW = "user_pw";
//
//    private final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
//    ImageButton mGetProfilePicture; //= findViewById(R.id.open_camera_button);
//    EditText mUserEmail = findViewById(R.id.register_email_text);
//    EditText mUserPassword = findViewById(R.id.register_password_text);
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState()");
//        outState.putString(USER_EMAIL, mUserEmail.toString());
//        outState.putString(USER_PW, mUserPassword.toString());
//        //outState.pu
//
//        //outState.putInt(KEY_INDEX, mCurrentIndex);
//        //outState.putBoolean(CHEAT_STATUS, mIsCheater);
//    }
//
//    /* Register at top of navigation */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        saveProfile(RegisterActivity.this, mUserEmail.getText().toString(), mUserPassword.getText().toString());
//
//        Button mChangeButton = findViewById(R.id.change_profile_pic_button);
//        mGetProfilePicture = findViewById(R.id.open_camera_button);
//
//        mChangeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(RegisterActivity.this, R.string.profile_option, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mGetProfilePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startCamera();
//
//            }
//        });
//    }
//
////    private void saveUserInfo(){
////
////    }
//
//    private void saveProfile(Context context, String email, String password){
//        SharedPreferences mPref = context.getSharedPreferences("edu.dartmouth.cs.login", 0);
//        SharedPreferences.Editor mPrefEditor = mPref.edit();
//        /*mPrefEditor.putString("Email", email);
//        mPrefEditor.putString("Password", password);*/
//        HashSet<String> mUserProfile = new HashSet<>();
//        mUserProfile.add(email);
//        mUserProfile.add(password);
//
//        mPrefEditor.putStringSet("Profile Login", mUserProfile);
//        //mPrefEditor.commit();
//        mPrefEditor.apply();
//    }
//
//
//
////    public static void saveUserInfo(Context context)
////    {
////
////    }
//
//    public void startCamera() {
//
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//
//        {
//
//
//            // Permission is not granted
//            // Should we show an explanation to users the reason that we need the permission?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
//                    Manifest.permission.CAMERA))
//
//            {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                Toast.makeText(RegisterActivity.this, R.string.explain_permission_request, Toast.LENGTH_SHORT).show();
//
//            } else
//
//            {
//
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(RegisterActivity.this,
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
//
//                // MY_PERMISSIONS_REQUEST_CAMERA is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//
//            // Permission has already been granted
//            //Intent intent = new Intent(RegisterActivity.this, Camera)
//            Intent intent = new Intent(RegisterActivity.this, CAMERA_SERVICE.getClass());
//            startActivity(intent);
//        }
//    }
//}

// ****************** private helper functions ***************************//

// load the user data from shared preferences if there is no data make sure
// that we set it to something reasonable
    /*
    private void loadUserData() {

        // We can also use log.d to print to the LogCat

        Log.d(TAG, "loadUserData()");

        // Load and update all profile views

        // Get the shared preferences - create or retrieve the activity
        // preference object

        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        // Load the user email

        mKey = getString(R.string.preference_key_profile_email);
        String mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.register_email_text)).setText(mValue);

        // Load the user password

        mKey = getString(R.string.preference_key_profile_password);
        mValue = mPrefs.getString(mKey, "");
        ((EditText) findViewById(R.id.register_password_text)).setText(mValue);

        // Please Load gender info and set radio box

        mKey = getString(R.string.preference_key_profile_gender);

        int mIntValue = mPrefs.getInt(mKey, -1);
        // In case there isn't one saved before:
        if (mIntValue >= 0) {
            // Find the radio button that should be checked.
            RadioButton radioBtn = (RadioButton) ((RadioGroup) findViewById(R.id.radioGender))
                    .getChildAt(mIntValue);
            // Check the button.
            radioBtn.setChecked(true);
            Toast.makeText(getApplicationContext(),
                    "number of the radioButton is : " + mIntValue,
                    Toast.LENGTH_SHORT).show();
        }

    }
    */