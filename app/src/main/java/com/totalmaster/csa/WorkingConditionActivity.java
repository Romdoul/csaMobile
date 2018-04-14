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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkingConditionActivity extends AppCompatActivity {
    private NiceSpinner workPosition;
    private static ProgressDialog progressDialog;
    private EditText workLocation;
    private TextView validate_workLocation, validate_workPosition, validate_expectedSalary, validate_receiveSalaryHow, validate_receiveSalaryWhen;
    private RadioGroup mExpectedSalary, mReceiveSalaryHow, mReceiveSalaryWhen;
    private RadioButton mExpected_salary_100, mexpected_salary_150, mexpected_salary_175, mexpected_salary_200;
    private RadioButton mReceive_salary_cash, mReceive_salary_bank;
    private RadioButton mReceive_salary_daily, mReceive_salary_weekly, mReceive_salary_monthly;
    private String myExpectedSalary="", myReceiveSalaryHow="", myReceiveSalaryWhen="";
    private List<String> jobElement = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select",
            "ជាងភ្លើង | Light Civil Worker",
            "កម្មករសុីវីល | Civil Worker",
            "វិស្វករសុីវីល | Civil Engineer",
            "ជំនួយការខាងតូប៉ូ | Surveying Assistant",
            "វិស្វករខាងតូប៉ូ​ |  Surveying Engineer",
            "ប្រតិបត្តិករម៉ាស៊ីនសំណង់​ | Construction Machine Operator",
            "អ្នកបើកបរត្រាក់ទ័រ | Truck Driver",
            "កម្មករស្ថាបត្យកម្ម | Architecture Worker",
            "ជាងឈើ | Carpenter",
            "ស្ថាបត្យករ | Architect",
            "ជាងប្រព័ន្ធទឹក | Water Worker",
            "ជាងហ្គាស | Gas Worker",
            "ជាងអគ្គិសនី | Electrical Worker",
            "ជាងបំពង់ទឺក | Plumber"
    ));
    String mExpectsalary = null;
    int i;
    private SharedPreferences sharedPreferences;
    private String userID, usereName, passWord;
    ArrayList<String> stringArray = new ArrayList<String>();
    String[] job_=null, location_=null, expect_salary_=null, payment_method_=null, salary_duration_=null;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_condition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userID",null);
        usereName = sharedPreferences.getString("username", null);
        passWord = sharedPreferences.getString("password", null);
        mExpectsalary = sharedPreferences.getString("expectedSalary", null);

        validate_workLocation = findViewById(R.id.validate_work_location);
        validate_workPosition = findViewById(R.id.validate_working_position);
        validate_expectedSalary = findViewById(R.id.validated_expect_salary);
        validate_receiveSalaryHow = findViewById(R.id.validate_getting_salary_how);
        validate_receiveSalaryWhen = findViewById(R.id.validate_receive_salary_when);

        workLocation = findViewById(R.id.workLocation);
        workPosition = findViewById(R.id.workingPosition);
        mExpectedSalary = findViewById(R.id.expected_salary);
        mReceiveSalaryHow = findViewById(R.id.receive_salary_how);
        mReceiveSalaryWhen = findViewById(R.id.receive_salary_when);

        mExpected_salary_100 = findViewById(R.id.expected_salary_100);
        mexpected_salary_150 = findViewById(R.id.expected_salary_150);
        mexpected_salary_175 = findViewById(R.id.expected_salary_175);
        mexpected_salary_200 = findViewById(R.id.expected_salary_200);
        mReceive_salary_cash = findViewById(R.id.receive_salary_cash);
        mReceive_salary_bank = findViewById(R.id.receive_salary_bank);
        mReceive_salary_daily = findViewById(R.id.receive_salary_everyDay);
        mReceive_salary_weekly = findViewById(R.id.receive_salary_everyWeek);
        mReceive_salary_monthly = findViewById(R.id.receive_salary_everyMonth);

//        mExpectedSalary.check(R.id.expected_salary_100);
//        mExpected_salary_100.setBackgroundColor(getResources().getColor(R.color.blueDark));
//        mExpected_salary_100.setTextColor(getResources().getColor(R.color.white));

        System.out.print("thissssssss=======:");

        //spinner of work position

        spinner(workPosition,jobElement);


        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username", usereName);
        data.put("password", passWord);
        HttpRequestAsync httpRequestAsync = new HttpRequestAsync(headers, data);
        String userResult = null;
        try {
            userResult = httpRequestAsync.execute("http://10.10.0.4:8000/api/display_screen2/", "POST").get();
            Log.d("user result ------", userResult);
            JSONObject jsonObject = new JSONObject(userResult);
            Log.d("jsonobject ---", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("screen2");
            Log.d("json arrays ---", jsonArray.toString());
            job_ = new String[jsonArray.length()];
            location_ = new String[jsonArray.length()];
            expect_salary_ = new String[jsonArray.length()];
            payment_method_ = new String[jsonArray.length()];
            salary_duration_ = new String[jsonArray.length()];
            for(i = 0 ; i< jsonArray.length(); i++)
            {
                try {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    Log.d("jsonobject 3  ---", jsonObject3.toString());
//                    stringArray.add(jsonArray.get(i).toString());
                    job_[i] = jsonObject3.getString("job");
                    location_[i] = jsonObject3.getString("location");
                    expect_salary_[i] = jsonObject3.getString("expect_salary");
                    payment_method_[i] = jsonObject3.getString("payment_method");
                    salary_duration_[i] = jsonObject3.getString("salary_duration");
                    Log.d("emaillllllll", expect_salary_[i]);
                    if(job_[i].equals("null"))
                        workPosition.setText("");
                    else
                        workPosition.setText(job_[i]);
                    if(location_[i].equals("null"))
                        workPosition.setText("សូមជ្រើសរើស | Please select");
                    else
                        workLocation.setText(location_[i]);
//                    age.setText(expect_salary_[i]);
//                    dob_validate.setText(payment_method_[i]);
//                    address.setText(salary_duration_[i]);
                    switch (expect_salary_[i]) {
                        case "100$":
                            mExpectedSalary.check(R.id.expected_salary_100);
                            mExpected_salary_100.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mExpected_salary_100.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "150$":
                            mExpectedSalary.check(R.id.expected_salary_150);
                            mexpected_salary_150.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mexpected_salary_150.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "175$":
                            mExpectedSalary.check(R.id.expected_salary_175);
                            mexpected_salary_175.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mexpected_salary_175.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "200$":
                            mExpectedSalary.check(R.id.expected_salary_200);
                            mexpected_salary_200.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mexpected_salary_200.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.i("this is empty", "lol");
                            break;
                    }


                    switch (payment_method_[i]) {
                        case "By cash":
                            mReceiveSalaryHow.check(R.id.receive_salary_cash);
                            mReceive_salary_cash.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceive_salary_cash.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "By bank":
                            mReceiveSalaryHow.check(R.id.receive_salary_bank);
                            mReceive_salary_bank.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceive_salary_bank.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.i("this is empty", "lol");
                            break;
                    }

                    switch (salary_duration_[i]) {
                        case "Daily":
                            mReceiveSalaryWhen.check(R.id.receive_salary_everyDay);
                            mReceive_salary_daily.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceive_salary_daily.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "Weekly":
                            mReceiveSalaryWhen.check(R.id.receive_salary_everyWeek);
                            mReceive_salary_weekly.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceive_salary_weekly.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "Monthly":
                            mReceiveSalaryWhen.check(R.id.receive_salary_everyMonth);
                            mReceive_salary_monthly.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceive_salary_monthly.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.i("this is empty", "lol");
                            break;
                    }


                }catch (JSONException e) {
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





        mExpectedSalary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                String Expectsalary = radioButton.getText().toString();
                Log.i("This noob me ", Expectsalary);
                editor.putString("expectedSalary", Expectsalary);
                editor.apply();
                myExpectedSalary = (String) radioButton.getText();
                mExpected_salary_100.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mExpected_salary_100.setTextColor(getResources().getColor(R.color.black));
                mexpected_salary_150.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mexpected_salary_150.setTextColor(getResources().getColor(R.color.black));
                mexpected_salary_175.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mexpected_salary_175.setTextColor(getResources().getColor(R.color.black));
                mexpected_salary_200.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mexpected_salary_200.setTextColor(getResources().getColor(R.color.black));
                switch(i){
                    case R.id.expected_salary_100:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.expected_salary_150:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.expected_salary_175:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.expected_salary_200:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        mReceiveSalaryWhen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myReceiveSalaryWhen =(String) radioButton.getText();
                mReceive_salary_daily.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_daily.setTextColor(getResources().getColor(R.color.black));
                mReceive_salary_weekly.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_weekly.setTextColor(getResources().getColor(R.color.black));
                mReceive_salary_monthly.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_monthly.setTextColor(getResources().getColor(R.color.black));

                switch(i){
                    case R.id.receive_salary_everyDay:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.receive_salary_everyWeek:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.receive_salary_everyMonth:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        mReceiveSalaryHow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myReceiveSalaryHow =(String) radioButton.getText();
                Log.d("receive salary how ======", myReceiveSalaryHow);
                mReceive_salary_cash.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_cash.setTextColor(getResources().getColor(R.color.black));
                mReceive_salary_bank.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_bank.setTextColor(getResources().getColor(R.color.black));

                switch(i){
                    case R.id.receive_salary_cash:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.receive_salary_bank:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        mReceiveSalaryWhen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myReceiveSalaryWhen =(String) radioButton.getText();
                mReceive_salary_daily.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_daily.setTextColor(getResources().getColor(R.color.black));
                mReceive_salary_weekly.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_weekly.setTextColor(getResources().getColor(R.color.black));
                mReceive_salary_monthly.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceive_salary_monthly.setTextColor(getResources().getColor(R.color.black));

                switch(i){
                    case R.id.receive_salary_everyDay:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.receive_salary_everyWeek:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.receive_salary_everyMonth:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });

        Button btnWorkingConditionForm = findViewById(R.id.btnSummitDesiredCondition);
        btnWorkingConditionForm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    String workLocationValue = workLocation.getText().toString().trim();
                    String workPositionValue = workPosition.getText().toString().trim();
                    String expectedSalaryValue = myExpectedSalary;
                    String receiveSalaryHowValue = myReceiveSalaryHow;
                    String receiveSalaryWhenValue = myReceiveSalaryWhen;
                    Log.d("1********", workLocationValue);
                    Log.d("2********", workPositionValue);
                    Log.d("3********", expectedSalaryValue);
                    Log.d("4********", receiveSalaryHowValue);
                    Log.d("5********", receiveSalaryWhenValue);
                    ValidationHelper2 mValidateHelper = new ValidationHelper2(workLocationValue,
                            workPositionValue, expectedSalaryValue, receiveSalaryHowValue,
                            receiveSalaryWhenValue);

                    if (mValidateHelper.isWorkLocationEmpty()){
                        validate_workLocation.setText(R.string.fieldValidation);
                        validate_workLocation.requestFocus();
                    }
                    else validate_workLocation.setText("");
                    if (mValidateHelper.isWorkPositionEmpty()){
                        validate_workPosition.setText(R.string.fieldValidation);
                    }
                    else validate_workPosition.setText("");
                    if (mValidateHelper.isExpectedSalaryEmpty()){
                        validate_expectedSalary.setText(R.string.fieldValidation);
                    }
                    else validate_expectedSalary.setText("");
                    if (mValidateHelper.isGettingSalaryEmpty()){
                        validate_receiveSalaryHow.setText(R.string.fieldValidation);
                    }
                    else validate_receiveSalaryHow.setText("");
                    if (mValidateHelper.isReceiveSalaryWhenEmpty()){
                        validate_receiveSalaryWhen.setText(R.string.fieldValidation);
                    }
                    else validate_receiveSalaryWhen.setText("");
                    if (mValidateHelper.isReadyForWorkingConditionForm()){
                        submitWorkingConditionForm(workLocationValue, workPositionValue, expectedSalaryValue, receiveSalaryHowValue, receiveSalaryWhenValue);
                    }
                }
                else {
                    presentDialog("ដាក់ស្នើសំណុំបែបបទបរាជ័យ!","សូមពិនិត្យមើលការតភ្ជាប់អ៊ីធឺណិតរបស់អ្នក");
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void submitWorkingConditionForm(final String successWorkLocation, final String successWorkPosition, final String successExpectedSalary, final String successReceiveSalaryHow, final String successReceiveSalaryWhen ){
        showProgressDialog("Processing...");

        //summit form
        JSONObject dataResult;
        String result = null;
        String mLocation = successWorkLocation;
        String mPosition = successWorkPosition;
        String mSalary = successExpectedSalary;
        String mSalaryMethod = successReceiveSalaryHow;
        String mSalaryTime = successReceiveSalaryWhen;
        String mUserType = "3";
        Log.d("print mLocation ==---",mLocation);
        Log.d("print mPosition ==---",mPosition);
        Log.d("print mSalary ==---",mSalary);
        Log.d("print mSalaryMethod=--",mSalaryMethod);
        Log.d("print mSalaryTime ==---",mSalaryTime);
        ArrayMap<String, String> headers = new ArrayMap<>();
//        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNTIyNjc3MjczLCJlbWFpbCI6ImFkbWluQGdhbWlsLmNvbSIsIm9yaWdfaWF0IjoxNTIyMDcyNDczfQ.4x4jNPnbS38DYdJL2qaxc2QdRdSh02HeApWE4-PElxA");
        ArrayMap<String, String> data = new ArrayMap<>();
//        data.put("user_type",mUserType);
        data.put("job", successWorkPosition);
        data.put("location", successWorkLocation);
        data.put("expect_salary", successExpectedSalary);
        data.put("payment_method", successReceiveSalaryHow);
        data.put("salary_duration", successReceiveSalaryWhen);
        data.put("user", userID);
//        Log.d("id =========",userID);

        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
        try {
            result = myhttp.execute("http://10.10.0.4:8000/api/submit_screen2/", "POST").get();
            Log.d("Result Post method===",result);
            JSONObject jsonObject = new JSONObject(result);

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
            else if (result.equals("false : 201")){
                dismissProgressDialog();
                presentDialog("Summit form failed!", "Server is not running...");
            }
            else {
                dismissProgressDialog();
                Intent intent = new Intent(WorkingConditionActivity.this,WorkingDetailsActivity.class);
                startActivity(intent);
            }

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
        Intent intent = new Intent(WorkingConditionActivity.this, DashboardActivity.class);
        startActivity(intent);
    }


    public void spinner (NiceSpinner niceSpinner, List dataset){
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
        progressDialog = new ProgressDialog(WorkingConditionActivity.this);
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