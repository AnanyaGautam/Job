package com.example.jobqueue.ui.adapter;

import android.accounts.Account;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jobqueue.R;
import com.example.jobqueue.ui.viewHolder.ViewHolder;
import com.example.jobqueue.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context ctx;
    ArrayList<Employee> eList;

    public RecyclerAdapter(Context ctx, ArrayList<Employee> eList) {
        super();

        this.ctx = ctx;
        this.eList = eList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(ctx).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //viewHolder.id.setText((eList.get(i).getId()));
        viewHolder.name.setText(eList.get(i).getName());
        //viewHolder.age.setText(eList.get(i).getAge());
        viewHolder.salary.setText(eList.get(i).getSalary());

    }


    @Override
    public int getItemCount(){
        return eList == null ? 0 : eList.size();
    }

}