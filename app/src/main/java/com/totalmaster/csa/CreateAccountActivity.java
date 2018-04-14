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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mUsername, mPassword, mConfirmPassword;
    private TextView nameValidation, passwordValidation, confirmPasswordValidation;
    private Button createAcc;
    private ValidationHelper mValidationHelper;
    private static ProgressDialog progressDialog;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    private static Context context;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        context = this;
        // Display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        nameValidation = findViewById(R.id.usernameValidation);
        passwordValidation = findViewById(R.id.passwordValidation);
        confirmPasswordValidation = findViewById(R.id.confirmPasswordValidation);

        createAcc = findViewById(R.id.btnCreateAcc);


        createAcc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    final String getUserName = mUsername.getText().toString().trim();
                    final String getPassword = mPassword.getText().toString().trim();
                    final String getConfirmPassword = mConfirmPassword.getText().toString().trim();
                    mValidationHelper = new ValidationHelper(getUserName, getPassword, getConfirmPassword);

                    if (mValidationHelper.isNameEmpty()){
                        nameValidation.setText(R.string.usernameValidation);
                        nameValidation.requestFocus();
                    }
                    else if (!mValidationHelper.isValidName()){
                        nameValidation.setText(R.string.usernameValidation);
                        nameValidation.requestFocus();
                    }
                    else {
                        nameValidation.setText("");
                    }
                    if (mValidationHelper.isPasswordEmpty()){
                        passwordValidation.setText(R.string.passwordValidation);
                        passwordValidation.requestFocus();
                    }
                    else if (!mValidationHelper.isValidPassword()){
                        passwordValidation.setText(R.string.passwordValidation);
                        passwordValidation.requestFocus();
                    }
                    else {
                        passwordValidation.setText("");
                    }
                    if (mValidationHelper.isPasswordEmpty()){
                        confirmPasswordValidation.setText(R.string.confirmPasswordValidation);
                        confirmPasswordValidation.requestFocus();
                    }
                    else if (!mValidationHelper.isPasswordMatched()){
                        confirmPasswordValidation.setText(R.string.confirmPasswordValidation);
                        confirmPasswordValidation.requestFocus();
                    }
                    else {
                        confirmPasswordValidation.setText("");
                    }
                    if (mValidationHelper.isReadyForCreateAccount()){
                        createAccount(getUserName, getPassword);
                    }
                }
                else {
                    presentDialog("Sign Up fail!","Please check your internet connection.");
                }
            }
        });

    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createAccount(final String successUserName, final String successPassword){
        showProgressDialog("Processing...");

        preference = PreferenceManager .getDefaultSharedPreferences(context);
        editor = preference.edit();
        editor.putString("pass", successPassword);
        editor.commit();

        //create acc
        JSONObject dataResult;
        String result = null;
        String userName = successUserName;
        String passWord = successPassword;
        Log.d("print username ==---",userName);
        Log.d("print password ==---",passWord);
        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username",userName);
        data.put("password", passWord);
        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
        try {
            result = myhttp.execute("http://10.10.0.4:8000/api/create_users/", "POST").get();
            Log.d("Result Post method===",result);
//            JSONObject jsonObject = new JSONObject(result);
//            Log.d("This is json object _____",jsonObject.toString());
            if (result.equals("failed")){
                dismissProgressDialog();
                presentDialog("Sign up failed!", "Server is not running...");
            }
            else if (result.equals("false : 400")){
                dismissProgressDialog();
                presentDialog("Sign up failed!", "Username already exist...");
            }
            else {
                dismissProgressDialog();
                Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
                startActivity(intent);}

        } catch (InterruptedException e) {
            Log.d("print error Interrupted +++",e.toString());
            dismissProgressDialog();
            presentDialog("InterruptedException","error...");
        } catch (ExecutionException e) {
            Log.d("print errors execution +++",e.toString());
            dismissProgressDialog();
            presentDialog("ExecutionException", "error...");
        }

    }

    private void navigateScreen(Button btn, final Class next) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, next);
                startActivity(intent);
            }
        });
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
        builder.setCancelable(true);
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
    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(CreateAccountActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }
//    Go back
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
