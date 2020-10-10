package com.example.implementation;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter{
    MyAdapter adapter;
    ArrayList<Model>filterList;

    public CustomFilter(ArrayList<Model> filterList,MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }
//filtering occurs
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //check constr.validity
        if(constraint!=null && constraint.length()>0){
            //change to upper case
            constraint=constraint.toString().toUpperCase();
            // store our filtered models
            ArrayList<Model> filteredModel=new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                //check
                if(filterList.get(i).getName().toUpperCase().contains(constraint)){
                    //add model to filtered model
                    filteredModel.add(filterList.get(i));
                }
            }
            results.count=filteredModel.size();
            results.values=filteredModel;
        }
        else{
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models=(ArrayList<Model>)results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}
