package com.totalmaster.csa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoWorkerActivity extends AppCompatActivity {
    private Button button1;
    private ListView coWorker;
    private CoWorkerAdapter coWorkerAdapter;
    private List<String> userList;

    private SharedPreferences sharedPreferences;
    private String username, password, result;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_worker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);

        button1 = (Button) findViewById(R.id.evaluate_co_worker);
        coWorker = (ListView) findViewById(R.id.list_co_worker);
        userList = new ArrayList<>();

        ArrayMap<String, String> headers = new ArrayMap<>();
        ArrayMap<String, String> data = new ArrayMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpRequestAsync getRequest = new HttpRequestAsync(headers, data);
        try {
            result = getRequest.execute("http://10.10.0.4:8000/api/display_coworker/", "POST").get();
            System.out.println("I'm getting my result " + result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("coworker");

            for (int i=0; i<jsonArray.length(); i++){
                userList.add(jsonArray.getString(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        coWorkerAdapter = new CoWorkerAdapter(getApplicationContext(), userList);
        coWorker.setAdapter(coWorkerAdapter);

//        evaluate_co_worker(button1);
    }

    public void evaluate_co_worker(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CoWorkerActivity.this,EvaluateCoWorkerActivity.class);
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