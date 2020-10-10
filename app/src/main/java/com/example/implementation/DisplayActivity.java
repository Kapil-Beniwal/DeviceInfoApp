package com.example.implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {
    //array items to display in list view

    private  String titles[];
    private String descriptins[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //actionbar
//        ActionBar actionBar=getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setTitle("Display");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Screensize in pixel
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        String resolution=width+"x"+height +"pixel";
        //physical size in inch
        double x=Math.pow(width/dm.xdpi,2);
        double y=Math.pow(height/dm.ydpi,2);

        double screenInches=Math.sqrt(x+y);//this will return a floating value
        NumberFormat form=NumberFormat.getNumberInstance();//limit number of values after fraction
        form.setMinimumFractionDigits(2);//minimum two values after fraction
        form.setMaximumFractionDigits(2);//maximum two values after fraction

        String screenInchesOutput=form.format(screenInches);

        //Refresh rate

        Display display=((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating=display.getRefreshRate();

        NumberFormat form1=NumberFormat.getNumberInstance();//limit number of values after fraction
        form1.setMinimumFractionDigits(2);//minimum two values after fraction
        form1.setMaximumFractionDigits(2);//maximum two values after fraction
        String outputRefreshRating=form1.format(refreshRating);

        titles=new String[]{"Resolution","Density","Physical Size","Refresh Rate","Orientation"};
        descriptins=new String[]{resolution,DisplayMetrics.DENSITY_XHIGH+" dpi(xhdpi)",screenInchesOutput+" inch",outputRefreshRating+" Hz",this.getResources().getConfiguration().orientation+""};
        //this.getResources().getConfiguration().orientation will return 1 or 2
        //1 means portrait orientation
        //2 means landscape orientation






        //list view
        ListView list=findViewById(R.id.displayList);
        //adpater
        MyAdapter adapter=new MyAdapter(this,titles,descriptins);
        list.setAdapter(adapter);

    }
 // custom adapter

    private class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        //constructor
        MyAdapter(Context c,String[] titles,String[] descriptios){
            super(c,R.layout.tworow,R.id.title,titles);
            this.context=c;
            this.myTitles=titles;
            this.myDescriptions=descriptios;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.tworow,parent,false);

            //views from tworow.xml
            TextView myTitle=row.findViewById(R.id.titleTv);
            TextView myDescriptions=row.findViewById(R.id.descTv);

            //set text to these views
            myTitle.setText(titles[position]);
            myDescriptions.setText(descriptins[position]);
            return row;
        }
    }
    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
    //hide search view from activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}
