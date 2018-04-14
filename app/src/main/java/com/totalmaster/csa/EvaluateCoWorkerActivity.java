package com.totalmaster.csa;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EvaluateCoWorkerActivity extends AppCompatActivity {

    private Button evaluateReliability, evaluateHardWorking, evaluateTechnical, evaluateLeadership, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_co_worker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        evaluateReliability = (Button) findViewById(R.id.rateReliability);
        evaluateHardWorking = findViewById(R.id.rateHardWorking);
        evaluateTechnical = findViewById(R.id.rateTechnicalLevel);
        evaluateLeadership = findViewById(R.id.rateLeadership);
        doneBtn = (Button) findViewById(R.id.donebtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvaluateCoWorkerActivity.this, CoWorkerActivity.class);
                startActivity(intent);
            }
        });

        showDialogFragement(evaluateReliability, new ReliablilityRateActivity());
        showDialogFragement(evaluateHardWorking, new HardWorkingActivity());
        showDialogFragement(evaluateTechnical, new TechnicalLevelActivity());
        showDialogFragement(evaluateLeadership, new LeadershipActivity());

//        evaluateReliability.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("hiiiiiiiiiiiiiii");
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                DialogFragment newFragment = new ReliablilityRateActivity();
//                newFragment.show(fragmentManager, "dialog");
//            }
//        });

    }

    public void showDialogFragement(Button btn, final DialogFragment dialogFragment){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                dialogFragment.show(fragmentManager, "dialog");
            }
        });
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
