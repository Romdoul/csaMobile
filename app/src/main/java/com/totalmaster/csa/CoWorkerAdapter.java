package com.totalmaster.csa;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CoWorkerAdapter extends BaseAdapter {
    private Context context;
    private List<String> userList;

    public CoWorkerAdapter(Context mContext, List<String> mUserList){
        this.context = mContext;
        this.userList = mUserList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.co_worker_listview, null);
        TextView name = (TextView) v.findViewById(R.id.co_worker_name);
        Button evaluate = (Button) v.findViewById(R.id.evaluate_co_worker);

        name.setText(userList.get(i));
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Congratulation!! You clicked on item " + i);
                Intent intent=new Intent(context,EvaluateCoWorkerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return v;
    }
}