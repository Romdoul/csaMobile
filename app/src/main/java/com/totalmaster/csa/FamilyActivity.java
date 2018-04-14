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
import android.widget.Switch;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FamilyActivity extends AppCompatActivity {

    private NiceSpinner memberInFamily, fatherJob, numberOfSibling, numberOfRelative, currentAsset;
    private TextView memberInFamilyValidate, fatherJobValidate, numberOfSiblingValidate, numberOfRelativeValidate,currentAssetValidate;
    private static ProgressDialog progressDialog;
    private List<String> mNumber = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "1","2","3","4","5",
            "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"));
    private List<String> mJop = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select",
            "កសិករ | Farmer",
            "កម្មករសំណង់​ | Construction Worker",
            "​វិស្វករសំណង់​ | Construction Engineer",
            "អ្នកធ្វើការរដ្ឋ | Government Worker",
            "អ្នកធ្វើការក្រុមហ៊ុន | Company Worker",
            "អ្នកគ្រប់គ្រងក្រុមហ៊ុន | Company Management"
    ));
    private List<String> mCurrentAsset = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "ផ្ទះ | House", "ឡាន | Car", "ដី | Land",  " គ្រឿងចក្រ | Construction Machinery ", "យាន្តយន្ត\u200B | Automobile", "អចលនទ្រព្យ\u200B | Real Estate   "));
    private SharedPreferences sharedPreferences;
    private String userID, usereName, passWord;
    private String[] family_member=null, father_occupation=null, no_siblings=null, no_relative=null, current_asset=null;
    int i;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        memberInFamilyValidate = findViewById(R.id.validate_member_in_family);
        fatherJobValidate = findViewById(R.id.validate_father_job);
        numberOfSiblingValidate = findViewById(R.id.validate_number_of_sibling);
        numberOfRelativeValidate = findViewById(R.id.validate_number_of_relative);
        currentAssetValidate = findViewById(R.id.validate_current_asset);

        Button btnFamilyForm = (Button) findViewById(R.id.btnSummitFamilyDetail);
        memberInFamily = findViewById(R.id.memberInFamily);
        fatherJob = findViewById(R.id.fatherJob);
        numberOfSibling = findViewById(R.id.numberOfSibling);
        numberOfRelative = findViewById(R.id.numberOfRelative);
        currentAsset = findViewById(R.id.currentAsset);

        spinner(memberInFamily,mNumber);
        spinner(fatherJob,mJop);
        spinner(numberOfSibling,mNumber);
        spinner(numberOfRelative,mNumber);
        spinner(currentAsset,mCurrentAsset);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = sharedPreferences.getString("userID",null);
        usereName = sharedPreferences.getString("username", null);
        passWord = sharedPreferences.getString("password", null);

        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username", usereName);
        data.put("password", passWord);
        HttpRequestAsync httpRequestAsync = new HttpRequestAsync(headers, data);
        String userResult = null;
        try {
            userResult = httpRequestAsync.execute("http://10.10.0.4:8000/api/display_screen4/", "POST").get();
            Log.d("user result ------", userResult);
            JSONObject jsonObject = new JSONObject(userResult);
            Log.d("jsonobject ---", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("screen4");
            Log.d("json arrays ---", jsonArray.toString());
            family_member = new String[jsonArray.length()];
            father_occupation = new String[jsonArray.length()];
            no_siblings = new String[jsonArray.length()];
            no_relative = new String[jsonArray.length()];
            current_asset = new String[jsonArray.length()];
            for(i = 0 ; i< jsonArray.length(); i++)
            {
                try {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    Log.d("jsonobject 3  ---", jsonObject3.toString());
                    family_member[i] = jsonObject3.getString("family_member");
                    father_occupation[i] = jsonObject3.getString("father_occupation");
                    no_siblings[i] = jsonObject3.getString("no_siblings");
                    no_relative[i] = jsonObject3.getString("no_relative");
                    current_asset[i] = jsonObject3.getString("current_asset");
                    Log.d("emaillllllll", current_asset[i]);
                    if(family_member[i].equals("null"))
                        memberInFamily.setText("សូមជ្រើសរើស | Please select");
                    else
                        memberInFamily.setText(family_member[i]);

                    if(father_occupation[i].equals("null"))
                        fatherJob.setText("សូមជ្រើសរើស | Please select");
                    else
                        fatherJob.setText(father_occupation[i]);

                    if(no_siblings[i].equals("null"))
                        numberOfSibling.setText("សូមជ្រើសរើស | Please select");
                    else
                        numberOfSibling.setText(no_siblings[i]);

                    if(no_relative[i].equals("null"))
                        numberOfRelative.setText("សូមជ្រើសរើស | Please select");
                    else
                        numberOfRelative.setText(no_relative[i]);

                    if(current_asset[i].equals("null"))
                        currentAsset.setText("សូមជ្រើសរើស | Please select");
                    else
                        currentAsset.setText(current_asset[i]);
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

        btnFamilyForm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    String memberInFamilyValue = memberInFamily.getText().toString();
                    String fatherJobValue = fatherJob.getText().toString();
                    String numberOfSiblingValue = numberOfSibling.getText().toString();
                    String numberOfRelativeValue = numberOfRelative.getText().toString();
                    String currentAssetValue = currentAsset.getText().toString();

                    ValidationHelper mValidationHelper = new  ValidationHelper(memberInFamilyValue,
                            fatherJobValue, numberOfSiblingValue, numberOfRelativeValue, currentAssetValue);

//                    if (mValidationHelper.isMemberInFamilyEmpty()){
//                        memberInFamilyValidate.setText(R.string.fieldValidation);
//                    }
//                    else memberInFamilyValidate.setText("");
//                    if (mValidationHelper.isFatherJobEmpty()){
//                        fatherJobValidate.setText(R.string.fieldValidation);
//                    }
//                    else fatherJobValidate.setText("");
//                    if (mValidationHelper.isNumberOfSiblingEmpty()){
//                        numberOfSiblingValidate.setText(R.string.fieldValidation);
//                    }
//                    else numberOfSiblingValidate.setText("");
//                    if (mValidationHelper.isNumberOfRelativeEmpty()){
//                        numberOfRelativeValidate.setText(R.string.fieldValidation);
//                    }
//                    else numberOfRelativeValidate.setText("");
//                    if (mValidationHelper.isCurrentAssetEmpty()){
//                        currentAssetValidate.setText(R.string.fieldValidation);
//                    }
//                    else currentAssetValidate.setText("");
//                    if (mValidationHelper.isReadyForFamilyDetailForm()){
                        summitFamilyDetailForm(memberInFamilyValue, fatherJobValue, numberOfSiblingValue,
                                numberOfRelativeValue, currentAssetValue);
//                    }
                }
                else {
                    presentDialog("ដាក់ស្នើសំណុំបែបបទបរាជ័យ!","សូមពិនិត្យមើលការតភ្ជាប់អ៊ីធឺណិតរបស់អ្នក");
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void summitFamilyDetailForm(final String successMemberInFamily, final String successFatherJob,
                                        final String successNumberOfSibling, final String successNumberOfRelative,
                                        final String successCurrentAsset){
        showProgressDialog("Processing...");
        //summit form
        JSONObject dataResult;
        String result = null;
        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("user",userID);
        data.put("family_member", successMemberInFamily);
        data.put("father_occupation", successFatherJob);
        data.put("no_siblings", successNumberOfSibling);
        data.put("no_relative", successNumberOfRelative);
        data.put("current_asset", successCurrentAsset);
        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
        try {
            result = myhttp.execute("http://10.10.0.4:8000/api/submit_screen4/", "POST").get();
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
                Intent intent = new Intent(FamilyActivity.this,EducationBackgroundActivity.class);
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
        Intent intent = new Intent(FamilyActivity.this, DashboardActivity.class);
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
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(FamilyActivity.this);
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
