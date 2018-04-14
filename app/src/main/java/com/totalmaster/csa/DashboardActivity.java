package com.totalmaster.csa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class DashboardActivity extends AppCompatActivity {
    private SharedPreferences spref;
    SharedPreferences.Editor editor;
    private Button registerBtn,desiredCondition,currentCondition,family,coWorker,evaluation,educationBackground, logout;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        spref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = spref.edit();
        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Are you sure ?");
                builder.setMessage("Logout from this device.");
                builder.setCancelable(false);

                builder.setPositiveButton(
                        "Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editor.putBoolean("loggedIn", false);
                                editor.apply();
                                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        registerBtn = findViewById(R.id.register);
        desiredCondition = findViewById(R.id.desired_con);
        currentCondition = findViewById(R.id.work_condition);
        family = findViewById(R.id.family);
        coWorker = findViewById(R.id.co_worker);
        evaluation = findViewById(R.id.evaluation);
        educationBackground = findViewById(R.id.education);
        Log.d("Vea error ey vea========", "Tov krom tt 1");

        navigateScreen(registerBtn,RegisterActivity.class);
        Log.d("Vea error ey vea========", "Tov krom tt 2");
        navigateScreen(desiredCondition,WorkingConditionActivity.class);
        navigateScreen(currentCondition,WorkingDetailsActivity.class);
        navigateScreen(family,FamilyActivity.class);
        navigateScreen(evaluation,EvaluationActivity.class);
        navigateScreen(educationBackground,EducationBackgroundActivity.class);
        navigateScreen(coWorker,CoWorkerActivity.class);
    }

    public void navigateScreen(Button btn, final Class next){
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this,next);
                Log.d("Vea error ey vea========", "Tov krom tt 3");
                startActivity(i);
                Log.d("Vea error ey vea========", "Tov krom tt 4");
            }
        });
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
    }
}
