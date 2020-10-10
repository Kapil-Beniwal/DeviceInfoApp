package com.example.implementation;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder>implements Filterable {

    Context c;
    ArrayList<Model> models,filterList;
    CustomFilter filter;


    public MyAdapter(Context ctx, ArrayList<Model> models) {
        this.c = ctx;
        this.models = models;
        this.filterList=models;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //convert xml to obj
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        MyHolder holder=new MyHolder(v);
        return holder;
    }
    //data bound to views
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //bind data
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());
        //handle item clicks
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //general
                if (models.get(pos).getName().equals("General")){
                    //start GeneralActivity on click
                    Intent intent=new Intent(c,GeneralActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("Device Id")){
                    Intent intent=new Intent(c,DeviceIDActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("Display")){
                    Intent intent=new Intent(c,DisplayActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("Battery")){
                    Intent intent=new Intent(c,BatteryActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("User Apps")){
                    Intent intent=new Intent(c,UserAppActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("System Apps")){
                    Intent intent=new Intent(c,SystenAppActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("Memory")){
                    Intent intent=new Intent(c,MemoryActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("CPU")){
                    Intent intent=new Intent(c,CpuActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("Sensors")){
                    Intent intent=new Intent(c,SensorsActivity.class);
                    c.startActivity(intent);
                }
                if (models.get(pos).getName().equals("SIM")){
                    Intent intent=new Intent(c,SimActivity.class);
                    c.startActivity(intent);
                }
            }
        });
    }

    //get total number of items
    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }
}

