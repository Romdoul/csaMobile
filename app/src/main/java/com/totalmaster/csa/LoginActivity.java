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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText userName, passWord;
    private Button btnSubmitSignin, btnSubmitCreateAcc;
    private TextView nameValidation;
    private TextView passwordValidation;
    private ValidationHelper mValidationHelper;
    private static ProgressDialog progressDialog;
    private SharedPreferences spref;
    SharedPreferences.Editor editor;
    private String mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        btnSubmitSignin = findViewById(R.id.btnSummitLogin);
        btnSubmitCreateAcc = findViewById(R.id.btnCreateAcc);
        nameValidation = findViewById(R.id.usernameValidation);
        passwordValidation = findViewById(R.id.passwordValidation);


//        Screen Navigation
        navigateScreen(btnSubmitCreateAcc, CreateAccountActivity.class);

//        signIn(btnSubmitSignin);
        btnSubmitSignin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Log.d("this is onclick________________________","ok");
//                showProgressDialog("Authenticating...");
                if (isNetworkAvailable()){
                    final String getUserName = userName.getText().toString().trim();
                    final String getPass = passWord.getText().toString().trim();
                    mValidationHelper = new ValidationHelper(getUserName, getPass);

                    if (mValidationHelper.isNameEmpty()){
                        nameValidation.setText(R.string.usernameValidation);
                        userName.requestFocus();
                    }
                    else {
                        nameValidation.setText("");
                    }
                    if (mValidationHelper.isPasswordEmpty()){
                        passwordValidation.setText(R.string.passwordValidation);
                        passWord.requestFocus();
                    }
                    else if (!mValidationHelper.isValidPassword()){
                        passwordValidation.setText(R.string.passwordValidation);
                        passWord.requestFocus();
                    }
                    else {
                        passwordValidation.setText("");
                    }
                    if (mValidationHelper.isReadyForLogin()){
                        Log.d("Is it okey //////////////","wah");
                        showProgressDialog("Authenticating...");
                        //Sign in
                        JSONObject jsonObject=null, jsonObject1=null;
                        String result = null;
                        String[] users=null;
                        int i;
                        ArrayList<String> stringArray = new ArrayList<String>();
                        mUsername = getUserName;
                        mPassword = getPass;
                        spref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        editor = spref.edit();
                        editor.putString("username", mUsername);
                        editor.putString("password", mPassword);
                        editor.apply();

                        ArrayMap<String, String> headers = new ArrayMap<>();
                        ArrayMap<String, String> data = new ArrayMap<>();
                        data.put("username", mUsername);
                        data.put("password", mPassword);
                        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
                        try {
                            result = myhttp.execute("http://10.10.0.4:8000/authenticate/", "POST").get();
                            jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("users");
                            users = new String[jsonArray.length()];
                            for(i = 0 ; i< jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                                    Log.d("jsonobject 3 resutl", jsonObject3.toString());
                                    stringArray.add(jsonArray.get(i).toString());
                                    users[i] = jsonObject3.getString("id");
                                    editor.putString("userID", users[i]);
                                    editor.putBoolean("loggedIn", true);
//                                    editor.putString("username", mUsername);
//                                    editor.putString("password", mPassword);
                                    editor.apply();
                                    Log.d("userID", users[i]);
                                    Log.d("username ----", mUsername);
                                    Log.d("password ----", mPassword);

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


                        if (result.equals("failed")) {
                            dismissProgressDialog();
                            presentDialog("Log in failed!", "server is not running...");
                        }
                        else if (result.equals("false : 400")){
                            dismissProgressDialog();
                            presentDialog("Log in failed!", "Wrong username or password...");
                        }
                        else {
                            Log.d("added data to database _________","done");
                            dismissProgressDialog();
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                else {
                    presentDialog("Log in failed!","Please check your internet connection.");
                }
            }
        });
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
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

    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void navigateScreen(final Button btn, final Class next){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, next);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spref = PreferenceManager.getDefaultSharedPreferences(this);
        spref.edit().remove("username").apply();
        spref.edit().remove("pass").apply();
        System.out.println("get data from local storage " + spref.getString("username", null) + "  " + spref.getString("pass", null));
    }
}
