package com.totalmaster.csa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class EvaluationActivity extends AppCompatActivity {
    private ImageView button1;
    private ImageView button2;
    private ImageView button3;
    private ImageView button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("ការវាយតម្លៃ");

        button1 = findViewById(R.id.comment1);
        button2 = findViewById(R.id.comment2);
        button3 = findViewById(R.id.comment3);
        button4 = findViewById(R.id.comment4);

        comment(button1);
        comment(button2);
        comment(button3);
        comment(button4);
    }
    private void comment(ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EvaluationActivity.this,CommentActivity.class);
                startActivity(i);
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
