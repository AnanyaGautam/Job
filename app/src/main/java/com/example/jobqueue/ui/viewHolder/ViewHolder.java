package com.example.jobqueue.ui.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jobqueue.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView name,id,age,salary;

    public ViewHolder(View v){
        super(v);

        //id = v.findViewById(R.id.id);
        name = v.findViewById(R.id.name);
        //age = v.findViewById(R.id.age);
        salary = v.findViewById(R.id.salary);

    }

}
