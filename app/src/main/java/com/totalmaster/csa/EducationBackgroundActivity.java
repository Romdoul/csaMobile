package com.totalmaster.csa;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EducationBackgroundActivity extends AppCompatActivity{

    private NiceSpinner howToGetTrain;
    private static ProgressDialog progressDialog;
    private List<String> mHowToGetTrain = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "ពេលចូលធ្វើការ", "មុនចូលធ្វើការ"));
    private RadioGroup mTraining, mTrainingDuration;
    private RadioButton training1, training2, trainingDuration1, trainingDuration2, trainingDuration3;
    private TextView validate_training, validate_getTraining, validate_trainingDuration;
    String myTraining = "", myTrainingDuration = "";
    private SharedPreferences sharedPreferences;
    private String userID, usereName, passWord;
    private String result = null;
    int i;
    private  String[] training_=null, training_methods_=null, duration_training_=null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_background);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button educationBackground = findViewById(R.id.btnSummitEducationBackground);

        mTraining = findViewById(R.id.training);
        mTrainingDuration = findViewById(R.id.training_duration);
        training1 = findViewById(R.id.training_yes);
        training2 = findViewById(R.id.training_no);
        trainingDuration1 = findViewById(R.id.training_duration_3);
        trainingDuration2 = findViewById(R.id.training_duration_6);
        trainingDuration3 = findViewById(R.id.training_duration_9);
        validate_getTraining = findViewById(R.id.validate_training);
        validate_getTraining = findViewById(R.id.validate_how_to_get_train);
        validate_trainingDuration = findViewById(R.id.validate_training_duration);
        howToGetTrain = findViewById(R.id.how_to_get_train);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = sharedPreferences.getString("userID",null);
        usereName = sharedPreferences.getString("username", null);
        passWord = sharedPreferences.getString("password", null);

        spinner(howToGetTrain,mHowToGetTrain);

        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username", usereName);
        data.put("password", passWord);
        HttpRequestAsync httpRequestAsync = new HttpRequestAsync(headers, data);
        String userResult = null;
        try {
            userResult = httpRequestAsync.execute("http://10.10.0.4:8000/api/display_screen5/", "POST").get();
            Log.d("user result ------", userResult);
            JSONObject jsonObject = new JSONObject(userResult);
            Log.d("jsonobject ---", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("screen5");
            Log.d("json arrays ---", jsonArray.toString());
            training_ = new String[jsonArray.length()];
            training_methods_ = new String[jsonArray.length()];
            duration_training_ = new String[jsonArray.length()];
            for(i = 0 ; i< jsonArray.length(); i++)
            {
                try {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    Log.d("jsonobject 3  ---", jsonObject3.toString());
//                    stringArray.add(jsonArray.get(i).toString());
                    training_[i] = jsonObject3.getString("training");
                    training_methods_[i] = jsonObject3.getString("training_methods");
                    duration_training_[i] = jsonObject3.getString("duration_training");
                    Log.d("emaillllllll", training_[i]);
                    Log.d("emaillllllll", duration_training_[i]);
//                    country.setText(training_[i]);
                    if(training_methods_[i].equals("null"))
                        howToGetTrain.setText("សូមជ្រើសរើស | Please select");
                    else
                        howToGetTrain.setText(training_methods_[i]);

//                    age.setText(duration_training_[i]);
//                    if(country_[i].equals("")){
//                        country.setText("");
//                    }
                    switch (training_[i]) {
                        case "បាទ/ចាស | Yes":
                            mTraining.check(R.id.training_yes);
                            training1.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            training1.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "ទេ | No":
                            mTraining.check(R.id.training_no);
                            training2.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            training2.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.d("ohhhh no ", "lol");
                            break;
                    }

                    if(duration_training_[i].equals("3ខែ\n3 Months")){
                        mTrainingDuration.check(R.id.training_duration_3);
                        trainingDuration1.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        trainingDuration1.setTextColor(getResources().getColor(R.color.white));
                    }else if(duration_training_[i].equals("6ខែ\n6 Months")){
                        mTrainingDuration.check(R.id.training_duration_6);
                        trainingDuration2.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        trainingDuration2.setTextColor(getResources().getColor(R.color.white));
                    }else if(duration_training_[i].equals("9ខែ\n9 Months")){
                        mTrainingDuration.check(R.id.training_duration_9);
                        trainingDuration3.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        trainingDuration3.setTextColor(getResources().getColor(R.color.white));
                    }else
                        Log.d("ohhhh no ", "lol");


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mTraining.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myTraining =(String) radioButton.getText();
                Log.d("receive salary how ======", myTraining);
                training1.setBackground(getResources().getDrawable(R.drawable.border_shape));
                training1.setTextColor(getResources().getColor(R.color.black));
                training2.setBackground(getResources().getDrawable(R.drawable.border_shape));
                training2.setTextColor(getResources().getColor(R.color.black));

                switch(i){
                    case R.id.training_yes:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.training_no:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        mTrainingDuration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myTrainingDuration =(String) radioButton.getText();
                Log.d("receive salary how ======", myTrainingDuration);
                trainingDuration1.setBackground(getResources().getDrawable(R.drawable.border_shape));
                trainingDuration1.setTextColor(getResources().getColor(R.color.black));
                trainingDuration2.setBackground(getResources().getDrawable(R.drawable.border_shape));
                trainingDuration2.setTextColor(getResources().getColor(R.color.black));
                trainingDuration3.setBackground(getResources().getDrawable(R.drawable.border_shape));
                trainingDuration3.setTextColor(getResources().getColor(R.color.black));

                switch(i){
                    case R.id.training_duration_3:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.training_duration_6:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.training_duration_9:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });



        educationBackground.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    String trainingValue = myTraining;
                    String getTrainingValue = howToGetTrain.getText().toString();
                    String trainingDurationValue = myTrainingDuration;
                    ValidationHelper2 mValidationHelper = new ValidationHelper2(trainingValue, getTrainingValue, trainingDurationValue);
//                    if (mValidationHelper.isTrainingEmpty())
//                        validate_training.setText(R.string.fieldValidation);
//                    else validate_training.setText("");
                    if (mValidationHelper.isGetTrainingEmpty())
                        validate_getTraining.setText(R.string.fieldValidation);
                    else validate_getTraining.setText("");
                    if (mValidationHelper.isTrainingDurationEmpty())
                        validate_trainingDuration.setText(R.string.fieldValidation);
                    else validate_trainingDuration.setText("");
                    if (mValidationHelper.isReadyforTrainingForm()){
                        submitEducationBackground(trainingValue,getTrainingValue,trainingDurationValue);

                    }
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void submitEducationBackground(final String successMemberInFamily, final String successFatherJob,
                                        final String successNumberOfSibling){
        showProgressDialog("Processing...");

        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("user",userID);
        data.put("training", successMemberInFamily);
        data.put("training_methods", successFatherJob);
        data.put("duration_training", successNumberOfSibling);
        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
        try {
            result = myhttp.execute("http://10.10.0.4:8000/api/submit_screen5/", "POST").get();
            Log.d("Result Post method===",result);
            JSONObject jsonObject = new JSONObject(result);
            Log.d("submited screen 1 is ______",jsonObject.toString());
            if (result.equals("failed")){
                dismissProgressDialog();
                presentDialog("Summit form failed!", "Server is not running...");
            }
            else if (result.equals("false : 400")){
                dismissProgressDialog();
                presentDialog("Summit form failed!", "Username already exist...");
            }
            else if (result.equals("false : 401")){
                dismissProgressDialog();
                presentDialog("Summit form failed!", "Server is not running...");
            }
            else {
                dismissProgressDialog();
                Intent intent = new Intent(EducationBackgroundActivity.this,EducationBackgroundActivity.class);
                startActivity(intent);}

        } catch (InterruptedException e) {
            Log.d("print error Interrupted +++",e.toString());
            dismissProgressDialog();
            presentDialog("InterruptedException","error...");
        } catch (ExecutionException e) {
            Log.d("print errors execution +++",e.toString());
            dismissProgressDialog();
            presentDialog("ExecutionException", "error...");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dismissProgressDialog();
        Intent intent = new Intent(EducationBackgroundActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void spinner(NiceSpinner niceSpinner, List dataset){
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("selected ^^^^^^^^^^^^^^^^^^", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(EducationBackgroundActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void presentDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
