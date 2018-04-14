package com.totalmaster.csa;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkingDetailsActivity extends AppCompatActivity{
    private NiceSpinner currentDesignation, workingExperience, skill,yearOfExperience, disability, workingField;
    private static ProgressDialog progressDialog;
    private List<String> mCurrentDesignation = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select","អ្នកធ្វើការងារស្រាលៗឫងាយៗខាងសុីវីល", "អ្នកធ្វើការខាងសុីវីល", "អ្នកជំនាញឫវិស្វករសុីវីល","ជំនួយការខាងតូប៉", "អ្នកជំនាញឫវិស្វករខាងតូប៉ូ","អ្នកប្រើម៉ាសុីន","អ្នកបើកបរត្រាក់","អ្នកធ្វើការខាងអាសុីតិច","ជាងឈើ","អាសុីតិច","អ្នកធ្វើការ(កម្មករឫជាង)ខាងប្រព័ន្ធទឹក","អ្នកធ្វើការ (កម្មករឫជាង) ខាងហ្គាស","អ្នកធ្វើការ (កម្មករឫជាង) ខាងអគ្គិសន","អ្នកធ្វើការ (កម្មករឫជាង) ខាងបំពង់ទឺកឫហ្គាស"));
    private List<String> mSkill = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select","អ្នកធ្វើការងារស្រាលៗឫងាយៗខាងសុីវីល", "អ្នកធ្វើការខាងសុីវីល", "អ្នកជំនាញឫវិស្វករសុីវីល","ជំនួយការខាងតូប៉", "អ្នកជំនាញឫវិស្វករខាងតូប៉ូ","អ្នកប្រើម៉ាសុីន","អ្នកបើកបរត្រាក់","អ្នកធ្វើការខាងអាសុីតិច","ជាងឈើ","អាសុីតិច","អ្នកធ្វើការ(កម្មករឫជាង)ខាងប្រព័ន្ធទឹក","អ្នកធ្វើការ (កម្មករឫជាង) ខាងហ្គាស","អ្នកធ្វើការ (កម្មករឫជាង) ខាងអគ្គិសន","អ្នកធ្វើការ (កម្មករឫជាង) ខាងបំពង់ទឺកឫហ្គាស"));
    private List<String> mExperience = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"));
    private List<String> mDisability = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "មាន |Yes", "មិនមាន | No"));
    private List<String> mWorkingField = new LinkedList<>(Arrays.asList("សូមជ្រើសរើស | Please select", "សំណង់ | Construction", "សីុវិល | Civil"));
    private EditText numberOfCoworker;
    private RadioGroup mCurrentSalary, mReceiveSalaryTime;
    private RadioButton mCurrentSalary1, mCurrentSalary2, mCurrentSalary3;
    private RadioButton mReceiveSalary1, mReceiveSalary2, mReceiveSalary3;
    private TextView validate_currentDesignation, validate_workingExperience, validate_skill, validate_yearOfExperience, validate_noOfCoworker, validate_disability,validate_workingField, validate_currentSalary, validate_receiveSalary;
    String myCurrentSalary = "", myReceiveSalaryTime = "";

    private SharedPreferences sharedPreferences;
    private String userID, usereName, passWord;
    String[] current_designation=null, working_years_cc=null, skills=null, experiences=null, no_coworker=null, disability_=null, department=null, current_salary=null, current_salary_duration=null;
    int i;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = sharedPreferences.getString("userID",null);
        usereName = sharedPreferences.getString("username", null);
        passWord = sharedPreferences.getString("password", null);

        Button btnWorkingDetailForm = findViewById(R.id.btnSummitWorkingDetail);

        numberOfCoworker = findViewById(R.id.coWorker);
        currentDesignation = findViewById(R.id.currentDesignation);
        workingExperience = findViewById(R.id.workingExperience);
        skill = findViewById(R.id.skill);
        yearOfExperience = findViewById(R.id.yearOfExperience);
        disability = findViewById(R.id.disability);
        workingField = findViewById(R.id.workingField);
        mCurrentSalary = findViewById(R.id.current_salary);

        mReceiveSalaryTime = findViewById(R.id.when_receive_salary);
        mCurrentSalary1 = findViewById(R.id.current_salary_100_150);
        mCurrentSalary2 = findViewById(R.id.current_salary_150_200);
        mCurrentSalary3 = findViewById(R.id.current_salary_200_250);
        mReceiveSalary1 = findViewById(R.id.when_receive_salary_monthly);
        mReceiveSalary2 = findViewById(R.id.when_receive_salary_weekly);
        mReceiveSalary3 = findViewById(R.id.when_receive_salary_daily);

        spinner(currentDesignation,mCurrentDesignation);
        spinner(workingExperience,mExperience);
        spinner(skill,mSkill);
        spinner(yearOfExperience,mExperience);
        spinner(disability,mDisability);
        spinner(workingField,mWorkingField);

        validate_currentDesignation = findViewById(R.id.validate_current_designation);
        validate_workingExperience = findViewById(R.id.validate_working_experience);
        validate_skill =findViewById(R.id.validate_skill);
        validate_yearOfExperience =findViewById(R.id.validate_years_of_experience);
        validate_noOfCoworker =findViewById(R.id.validate_no_of_coworker);
        validate_disability =findViewById(R.id.validate_disability);
        validate_workingField = findViewById(R.id.validate_favorite);
        validate_currentSalary =findViewById(R.id.validate_current_salary);
        validate_receiveSalary =findViewById(R.id.validate_receive_salary_when);


        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username", usereName);
        data.put("password", passWord);
        HttpRequestAsync httpRequestAsync = new HttpRequestAsync(headers, data);
        String userResult = null;
        try {
            userResult = httpRequestAsync.execute("http://10.10.0.4:8000/api/display_screen3/", "POST").get();
            Log.d("user result ------", userResult);
            JSONObject jsonObject = new JSONObject(userResult);
            Log.d("jsonobject ---", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("screen3");
            Log.d("json arrays ---", jsonArray.toString());
            current_designation = new String[jsonArray.length()];
            working_years_cc = new String[jsonArray.length()];
            skills = new String[jsonArray.length()];
            experiences = new String[jsonArray.length()];
            no_coworker = new String[jsonArray.length()];
            disability_ = new String[jsonArray.length()];
            department = new String[jsonArray.length()];
            current_salary = new String[jsonArray.length()];
            current_salary_duration = new String[jsonArray.length()];
            for(i = 0 ; i< jsonArray.length(); i++)
            {
                try {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    Log.d("jsonobject 3  ---", jsonObject3.toString());
//                    stringArray.add(jsonArray.get(i).toString());
                    current_designation[i] = jsonObject3.getString("current_designation");
                    working_years_cc[i] = jsonObject3.getString("working_years_cc");
                    skills[i] = jsonObject3.getString("skills");
                    experiences[i] = jsonObject3.getString("experiences");
                    no_coworker[i] = jsonObject3.getString("no_coworker");
                    disability_[i] = jsonObject3.getString("disability");
                    department[i] = jsonObject3.getString("department");
                    current_salary[i] = jsonObject3.getString("current_salary");
                    current_salary_duration[i] = jsonObject3.getString("current_salary_duration");
                    Log.d("emaillllllll", current_salary_duration[i]);
                    if(current_designation[i].equals("null"))
                        currentDesignation.setText("សូមជ្រើសរើស | Please select");
                    else
                        currentDesignation.setText(current_designation[i]);
                    if(working_years_cc[i].equals("null"))
                        workingExperience.setText("សូមជ្រើសរើស | Please select");
                    else
                        workingExperience.setText(working_years_cc[i]);
                    if(skills[i].equals("null"))
                        skill.setText("សូមជ្រើសរើស | Please select");
                    else
                        skill.setText(skills[i]);
                    if(experiences[i].equals("null"))
                        yearOfExperience.setText("សូមជ្រើសរើស | Please select");
                    else
                        yearOfExperience.setText(experiences[i]);
                    if(no_coworker[i].equals("null"))
                        numberOfCoworker.setText("");
                    else
                        numberOfCoworker.setText(no_coworker[i]);
                    if(disability_[i].equals("null"))
                        disability.setText("សូមជ្រើសរើស | Please select");
                    else
                        disability.setText(disability_[i]);
                    if(department[i].equals("null"))
                        workingField.setText("សូមជ្រើសរើស | Please select");
                    else
                        workingField.setText(department[i]);

                    switch (current_salary[i]) {
                        case "100-150$":
                            mCurrentSalary.check(R.id.current_salary_100_150);
                            mCurrentSalary1.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mCurrentSalary1.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "150-200$":
                            mCurrentSalary.check(R.id.current_salary_150_200);
                            mCurrentSalary2.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mCurrentSalary2.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "200-250$":
                            mCurrentSalary.check(R.id.current_salary_200_250);
                            mCurrentSalary3.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mCurrentSalary3.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.i("this is empty", "lol");
                            break;
                    }


                    switch (current_salary_duration[i]) {
                        case "រាល់ខែ\nMonthly":
                            mReceiveSalaryTime.check(R.id.when_receive_salary_monthly);
                            mReceiveSalary1.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceiveSalary1.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "រាល់សប្តាហ៍\nWeekly":
                            mReceiveSalaryTime.check(R.id.when_receive_salary_weekly);
                            mReceiveSalary2.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceiveSalary2.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case "រាល់ថ្ងៃ\nDaily":
                            mReceiveSalaryTime.check(R.id.when_receive_salary_daily);
                            mReceiveSalary3.setBackgroundColor(getResources().getColor(R.color.blueDark));
                            mReceiveSalary3.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Log.i("this is empty", "lol");
                            break;
                    }

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

        //change radio button color
        mCurrentSalary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myCurrentSalary =(String) radioButton.getText();
                mCurrentSalary1.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mCurrentSalary1.setTextColor(getResources().getColor(R.color.black));
                mCurrentSalary2.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mCurrentSalary2.setTextColor(getResources().getColor(R.color.black));
                mCurrentSalary3.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mCurrentSalary3.setTextColor(getResources().getColor(R.color.black));
                switch(i){
                    case R.id.current_salary_100_150:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.current_salary_150_200:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.current_salary_200_250:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        mReceiveSalaryTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Log.d("this what", radioButton.getText().toString());
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                myReceiveSalaryTime =(String) radioButton.getText();
                mReceiveSalary1.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceiveSalary1.setTextColor(getResources().getColor(R.color.black));
                mReceiveSalary2.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceiveSalary2.setTextColor(getResources().getColor(R.color.black));
                mReceiveSalary3.setBackground(getResources().getDrawable(R.drawable.border_shape));
                mReceiveSalary3.setTextColor(getResources().getColor(R.color.black));
                switch(i){
                    case R.id.when_receive_salary_monthly:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.when_receive_salary_weekly:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.when_receive_salary_daily:
                        radioButton.setBackgroundColor(getResources().getColor(R.color.blueDark));
                        radioButton.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });

        //end change color radio button

        btnWorkingDetailForm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    String currentDesignationValue = currentDesignation.getText().toString().trim();
                    String workingExperienceValue = workingExperience.getText().toString().trim();
                    String skillValue = skill.getText().toString().trim();
                    String yearOfExperienceValue = yearOfExperience.getText().toString().trim();
                    String noCoworkerValue = numberOfCoworker.getText().toString().trim();
                    String disabilityValue = disability.getText().toString().trim();
                    String workingFieldValue = workingField.getText().toString().trim();
                    String currentSalaryValue = myCurrentSalary;
                    String receiveSalaryTimeValue = myReceiveSalaryTime;

                    Log.d(WorkingDetailsActivity.class.getSimpleName(),
                            currentDesignationValue + " = " +
                            workingExperienceValue +" = " +
                            skillValue + " = " +
                            yearOfExperienceValue + " = " +
                            noCoworkerValue + " = "+
                            disabilityValue + " = " +
                            workingFieldValue + " = " +
                            currentSalaryValue + " = " +
                            receiveSalaryTimeValue);
                    ValidationHelper2 mValidationHelper = new ValidationHelper2(
                            currentDesignationValue,
                            workingExperienceValue,
                            skillValue,
                            yearOfExperienceValue,
                            noCoworkerValue,
                            disabilityValue,
                            workingFieldValue,
                            currentSalaryValue,
                            receiveSalaryTimeValue);
                    if (mValidationHelper.isCurrentDesignationEmpty()) {
                        Log.d("Vea error trong na vea=======", "currentjob");
                        validate_currentDesignation.setText(R.string.fieldValidation);
                    }
                    else {
                        validate_currentDesignation.setText("");
                    }
                    Log.d("Vea error trong na vea=======", "working experience");
                    if (mValidationHelper.isWorkExperienceEmpty())
                        validate_workingExperience.setText(R.string.fieldValidation);
                    else validate_workingExperience.setText("");
                    Log.d("Vea error trong na vea=======", "skill");
                    if (mValidationHelper.isSkillEmpty())
                        validate_skill.setText(R.string.fieldValidation);
                    else validate_skill.setText("");
                    Log.d("Vea error trong na vea=======", "coworker");
                    if (mValidationHelper.isNumberOfCoworker())
                        validate_noOfCoworker.setText(R.string.fieldValidation);
                    else validate_noOfCoworker.setText("");
                    Log.d("Vea error trong na vea=======", "disabilty");
                    if (mValidationHelper.isDisabilityEmpty())
                        validate_disability.setText(R.string.fieldValidation);
                    else validate_disability.setText("");
                    Log.d("Vea error trong na vea=======", " years");
                    if (mValidationHelper.isYearOfExperienceEmpty())
                        validate_yearOfExperience.setText(R.string.fieldValidation);
                    else validate_yearOfExperience.setText("");
                    Log.d("Vea error trong na vea=======", "working field");
                    if (mValidationHelper.isWorkingFieldEmpty())
                        validate_workingField.setText(R.string.fieldValidation);
                    else validate_workingField.setText("");
                    Log.d("Vea error trong na vea=======", "");
                    if (mValidationHelper.isCurrentSalaryEmpty())
                        validate_currentSalary.setText(R.string.fieldValidation);
                    else validate_currentSalary.setText("");
                    Log.d("Vea error trong na vea=======", "");
//                    if (mValidationHelper.isReceiveSalaryWhenEmpty())
//                        validate_receiveSalary.setText(R.string.fieldValidation);
//                    else validate_receiveSalary.setText("");
                    Log.d("Vea error trong na vea=======", "");
                    if (mValidationHelper.isReadyForWorkingDetailForm()){
                        showProgressDialog("Processing...");
                        ArrayMap<String, String> headers = new ArrayMap<>();
                        ArrayMap<String, String> data = new ArrayMap<>();

                        data.put("user", userID);
                        data.put("current_designation", currentDesignationValue);
                        data.put("working_years_cc", workingExperienceValue);
                        data.put("skills", skillValue);
                        data.put("experiences", yearOfExperienceValue);
                        data.put("no_coworker", noCoworkerValue);
                        data.put("disability", disabilityValue);
                        data.put("department", workingFieldValue);
                        data.put("current_salary", currentSalaryValue);
                        data.put("current_salary_duration", receiveSalaryTimeValue);
                        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
                        try {
                            String result = myhttp.execute("http://10.10.0.4:8000/api/submit_screen3/", "POST").get();
                            System.out.print("Welcome my result " + result);
                            if (result.equals("failed")){
                                dismissProgressDialog();
//                                presentDialog("Summit form failed!", "Server is not running...");
                            }
                            else if (result.equals("false : 400")){
                                dismissProgressDialog();
//                                presentDialog("Summit form failed!", "Username already exist...");
                            }
                            else if (result.equals("false : 401")){
                                dismissProgressDialog();
//                                presentDialog("Summit form failed!", "Server is not running...");
                            }
                            else if (result.equals("false : 201")){
                                dismissProgressDialog();
//                                presentDialog("Summit form failed!", "Server is not running...");
                            }
                            else {
//                                dismissProgressDialog();
                                Intent intent = new Intent(WorkingDetailsActivity.this,FamilyActivity.class);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Intent intent = new Intent(WorkingDetailsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
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
        progressDialog = new ProgressDialog(WorkingDetailsActivity.this);
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
