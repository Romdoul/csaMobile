package com.totalmaster.csa;

import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReliablilityRateActivity extends android.support.v4.app.DialogFragment {
    private RatingBar rb;
    private TextView value, score;
    private Button done;
//
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reliablility_rate, container, false);
        findView(view);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void findView(View view) {
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
//        int width = displaymetrics.widthPixels;
//        getActivity().getWindow().setLayout((int)(width*0.8),(int)(height*0.5));
////        DisplayMetrics dm = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(dm);
////
////
////        int width = dm.widthPixels;
////        int height = dm.heightPixels;
//
////        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));
//
//        Rate on Reliability
        rb = view.findViewById(R.id.rating);
        value = view.findViewById(R.id.value);
        score = view.findViewById(R.id.score);
        done = view.findViewById(R.id.done);
//
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v==1.0)
                    value.setText("អន់ណាស់");
                else if (v==2.0)
                    value.setText("អន់");
                else if (v==3.0)
                    value.setText("មធ្យម");
                else if (v==4.0)
                    value.setText("ល្អ");
                else if (v==5.0)
                    value.setText("ល្អណាស់");
                else
                    value.setText("");
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                Intent intent = new Intent(getContext(), EvaluateCoWorkerActivity.class);
//                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reliablility_rate);
//    }
}
