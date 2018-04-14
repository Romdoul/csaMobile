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
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {
    private NiceSpinner age, DOB_month, DOB_day, DOB_year;

    private EditText country, fullName, address, phoneNumber, email, recommendation;
    private TextView country_validate, fullName_validate, age_validate, dob_validate, address_validate, phoneNumber_validate, email_validate;
    private static ProgressDialog progressDialog;
    private Button registerFormBtn;
    private List<String> mAge = new LinkedList<>(Arrays.asList("អាយុ | Age", "18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"));
    private List<String> mMonth = new LinkedList<>(Arrays.asList("ខែ | Month",
            "មករា | January",
            "កុម្ភៈ | February",
            "មីនា | March",
            "មេសា | April",
            "ឧសភា | May",
            "មិថុនា | June",
            "កក្កដា | July",
            "សីហា | August",
            "កញ្ញា | September",
            "តុលា | October",
            "វិច្ឆិកា | November",
            "ធ្នូ | December"
    ));
//    private List<String> mMonth = new LinkedList<>(Arrays.asList("ខែ | Month","01","02","03", "04","05", "06","07","08","09","10","11","12"));
    private List<String> mDay = new LinkedList<>(Arrays.asList("ថ្ងៃ | Day", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"));
    private List<String> mYear = new LinkedList<>(Arrays.asList("ឆ្នាំ | Year","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980"));
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userID,usereName,passWord;
    int i;
    ArrayList<String> stringArray = new ArrayList<String>();
    String[] country_=null;
    String[] fullName_=null, age_=null, dob_day=null, dob_month=null, dob_year=null, address_=null, phone_=null, email_=null, recommendation_;

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        country = findViewById(R.id.country);
        fullName = findViewById(R.id.fullName);
        age =  findViewById(R.id.age);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        DOB_month =  findViewById(R.id.month);
        DOB_day =  findViewById(R.id.day);
        DOB_year =  findViewById(R.id.year);
        country_validate = findViewById(R.id.validate_country);
        fullName_validate = findViewById(R.id.validate_fullname);
        age_validate = findViewById(R.id.validate_age);
        dob_validate = findViewById(R.id.validate_DOB);
        address_validate = findViewById(R.id.validate_address);
        phoneNumber_validate = findViewById(R.id.validate_phone_number);
        email_validate = findViewById(R.id.validate_email);
        recommendation = findViewById(R.id.recommendation);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userID",null);
        usereName = sharedPreferences.getString("username", null);
        passWord = sharedPreferences.getString("password", null);

        spinner(age,mAge);
        spinner(DOB_month,mMonth);
        spinner(DOB_day,mDay);
        spinner(DOB_year,mYear);


        String fullNameValue = fullName.getText().toString().trim();
        Log.d("full name ::::::::::::::", fullNameValue);
        Boolean isfirstlaunch = sharedPreferences.getBoolean("fullname", false);
        if(isfirstlaunch){
            if(fullNameValue.length()==0)
                Log.d("full name1 ::::::::::::::", "yab");
            else{
                Log.d("full name2 ::::::::::::::", "ohhh");
            }


        }else {
            editor.putBoolean("fullname", true);
            editor.apply();

            ArrayMap<String, String> headers = new ArrayMap<>();
            ArrayMap<String, String> data = new ArrayMap<>();
            data.put("username", usereName);
            data.put("password", passWord);
            HttpRequestAsync httpRequestAsync = new HttpRequestAsync(headers, data);
            String userResult = null;
            try {
                userResult = httpRequestAsync.execute("http://10.10.0.4:8000/api/display_screen1/", "POST").get();
                Log.d("user result ------", userResult);
                JSONObject jsonObject = new JSONObject(userResult);
                Log.d("jsonobject ---", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("screen1");
                Log.d("json arrays ---", jsonArray.toString());
                country_ = new String[jsonArray.length()];
                fullName_ = new String[jsonArray.length()];
                age_ = new String[jsonArray.length()];
                dob_day = new String[jsonArray.length()];
                dob_month = new String[jsonArray.length()];
                dob_year = new String[jsonArray.length()];
                address_ = new String[jsonArray.length()];
                phone_ = new String[jsonArray.length()];
                email_ = new String[jsonArray.length()];
                recommendation_ = new String[jsonArray.length()];
                for (i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                        Log.d("jsonobject 3  ---", jsonObject3.toString());
//                    stringArray.add(jsonArray.get(i).toString());
                        country_[i] = jsonObject3.getString("country");
                        fullName_[i] = jsonObject3.getString("full_name");
                        age_[i] = jsonObject3.getString("age");
                        dob_day[i] = jsonObject3.getString("dob_day");
                        dob_month[i] = jsonObject3.getString("dob_month");
                        dob_year[i] = jsonObject3.getString("dob_year");
                        address_[i] = jsonObject3.getString("address");
                        phone_[i] = jsonObject3.getString("phone");
                        email_[i] = jsonObject3.getString("email");
                        recommendation_[i] = jsonObject3.getString("who_recommend");

                        fullName.setText(fullName_[i]);
                        age.setText(age_[i]);
                        DOB_day.setText(dob_day[i]);
                        DOB_month.setText(dob_month[i]);
                        DOB_year.setText(dob_year[i]);
                        address.setText(address_[i]);
                        phoneNumber.setText(phone_[i]);
                        email.setText(email_[i]);
                        recommendation.setText(recommendation_[i]);


//                        if(country_[i].equals("null"))
//                            Log.d("whyyyyyy","sam");
////                        country.setText("");
//                        else
//                            country.setText(country_[i]);
//
//                        if(fullName_[i].equals("null"))
//                            fullName.setText("");
//                        else
//                            fullName.setText(fullName_[i]);
//                        if(age_[i].equals("null"))
//                            age.setText("អាយុ | Age");
//                        else
//                            age.setText(age_[i]);
//                        if(dob_day[i].equals("null"))
//                            DOB_day.setText("ថ្ងៃ | Day");
//                        else
//                            DOB_day.setText(dob_day[i]);
//                        if(dob_month[i].equals("null"))
//                            DOB_month.setText("ខែ | Month");
//                        else
//                            DOB_month.setText(dob_month[i]);
//                        if(dob_year[i].equals("null"))
//                            DOB_year.setText("ឆ្នាំ | Year");
//                        else
//                            DOB_year.setText(dob_year[i]);
//                        if(address_[i].equals("null"))
//                            address.setText("");
//                        else
//                            address.setText(address_[i]);
//                        if(phone_[i].equals("null"))
//                            phoneNumber.setText("");
//                        else
//                            phoneNumber.setText(phone_[i]);
//                        if(email_[i].equals("null"))
//                            email.setText("");
//                        else
//                            email.setText(email_[i]);
//                        if(recommendation_[i].equals("null"))
//                            recommendation.setText("");
//                        else
//                            recommendation.setText(recommendation_[i]);
//                        Log.d("full name---- ", fullName_[i]);
                    } catch (JSONException e) {
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
        }

        registerFormBtn =  findViewById(R.id.bntSummitRegistration);
        registerFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    String countryValue = country.getText().toString().trim();
                    String fullNameValue = fullName.getText().toString().trim();
                    String ageValue = age.getText().toString().trim();
                    String DOBDayValue = DOB_day.getText().toString();
                    String DOBMonthValue = DOB_month.getText().toString();
                    String DOBYearValue = DOB_year.getText().toString();
//                    String DOBValue = DOBMonthValue + "/" + DOBDayValue + "/" + DOBYearValue;
                    String addressValue = address.getText().toString().trim();
                    String phoneNumberValue = phoneNumber.getText().toString().trim();
                    String emailValue = email.getText().toString().trim();
                    String recommendation_who = recommendation.getText().toString().trim();

                    Log.d("full name  : --------", fullNameValue);

                    ValidationHelper mValidationHelper = new ValidationHelper(countryValue, fullNameValue, ageValue, DOBDayValue, DOBMonthValue, DOBYearValue, addressValue, phoneNumberValue, emailValue);

                    if (mValidationHelper.isCountryEmpty()){
                        country_validate.setText(R.string.fieldValidation);
                        country.requestFocus();
                    }
                    else country_validate.setText("");
                    if (mValidationHelper.isFullnameEmpty()){
                        fullName_validate.setText(R.string.fieldValidation);
                        fullName.requestFocus();
                    }
                    else fullName_validate.setText("");
                    if (mValidationHelper.isAgeEmpty()){
                        age_validate.setText(R.string.fieldValidation);
                        age.requestFocus();
                    }
                    else age_validate.setText("");
                    if (mValidationHelper.isDayEmpty()){
                        dob_validate.setText(R.string.fieldValidation);
                        DOB_day.requestFocus();
                    }
                    else dob_validate.setText("");
                    if (mValidationHelper.isMonthEmpty()){
                        dob_validate.setText(R.string.fieldValidation);
                        DOB_month.requestFocus();
                    }
                    else dob_validate.setText("");
                    if (mValidationHelper.isYearEmpty()){
                        dob_validate.setText(R.string.fieldValidation);
                        DOB_year.requestFocus();
                    }
                    else dob_validate.setText("");
                    if (mValidationHelper.isAddressEmpty()){
                        address_validate.setText(R.string.fieldValidation);
                        address.requestFocus();
                    }
                    else address_validate.setText("");
                    if (mValidationHelper.isPhoneNumberEmpty()){
                        phoneNumber_validate.setText(R.string.fieldValidation);
                        phoneNumber.requestFocus();
                    }
                    else phoneNumber_validate.setText("");
                    if (!mValidationHelper.isValidPhoneNumber()){
                        phoneNumber_validate.setText(R.string.fieldValidation);
                        phoneNumber.requestFocus();
                    }
                    else phoneNumber_validate.setText("");
                    if (mValidationHelper.isEmailEmpty()){
                        email_validate.setText(R.string.fieldValidation);
                        email.requestFocus();
                    }
                    else if (!mValidationHelper.isValidEmail()){
                        email_validate.setText(R.string.fieldValidation);
                        email.requestFocus();
                    }
                    else {
                        email_validate.setText("");
                    }
                    if (mValidationHelper.isReadyForRegisterForm()){
//                        showProgressDialog("Processing...");
                        summitRegisterForm(countryValue, fullNameValue, ageValue, DOBDayValue, DOBMonthValue, DOBYearValue, addressValue, phoneNumberValue, emailValue, recommendation_who);
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
    private void summitRegisterForm(final String successCountry, final String successFullName, final String successAge, final String successDobDay, final String successDobMonth, final String successDobYear, final String successAddress, final String successPhoneNumber, final String successEmail, final String successRecommendation){
        showProgressDialog("Processing...");
        //summit form
        String result = null;
        String mUserType = userID;
        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("user",mUserType);
        data.put("country", successCountry);
        data.put("full_name", successFullName);
        data.put("age", successAge);
        data.put("dob_day", successDobDay);
        data.put("dob_month", successDobMonth);
        data.put("dob_year", successDobYear);
        data.put("address", successAddress);
        data.put("phone", successPhoneNumber);
        data.put("email", successEmail);
        data.put("who_recommend", successRecommendation);

        HttpRequestAsync myhttp = new HttpRequestAsync(headers,data);
        try {
            result = myhttp.execute("http://10.10.0.4:8000/api/register/", "POST").get();
//            result = myhttp.execute("http://10.10.2.61:8000/api/display_user_profile_for_web", "GET").get();
            Log.d("Result Post method===",result);
            JSONObject jsonObject = new JSONObject(result);
            Log.d("submited screen 1 is ______",jsonObject.toString());

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("Are you sure ?");
            builder.setMessage("Logout from this device.");
            builder.setCancelable(false);

            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            editor.putString("name", successFullName);
                            editor.apply();
                            Log.d("already put user name : ", "yeeeeeeeeee");
                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    });

            android.support.v7.app.AlertDialog alert = builder.create();
            alert.show();

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
                Intent intent = new Intent(RegisterActivity.this,WorkingConditionActivity.class);
                startActivity(intent);
            }
            else {
                dismissProgressDialog();
                presentDialog("Summit form failed!", "Server is not running...");
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
        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void spinner(NiceSpinner niceSpinner,List dataset){
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
        progressDialog = new ProgressDialog(RegisterActivity.this);
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
