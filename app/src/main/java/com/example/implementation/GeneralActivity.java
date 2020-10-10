package com.example.implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static android.os.SystemClock.uptimeMillis;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static java.lang.System.getProperty;
import java.util.concurrent.TimeUnit;

public class GeneralActivity extends AppCompatActivity {
    private  String titles[];
    private String descriptins[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        //actionbar
//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setTitle("General Info");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("General");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //calculating device up time
        long millisec=uptimeMillis();
        String upTime=String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millisec),
                TimeUnit.MILLISECONDS.toMinutes(millisec)-
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisec)),
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisec)));
        //Array of containing information
        titles=new String[]{"Model","Manufacturer","Device","Board","Hardware","Brand","Android Vresion","OS name","API level","Bootloader","Buid Number","Radio Version","Kernel","Android Runtime","Up Time"};
        descriptins=new String[]{Build.MODEL,Build.MANUFACTURER,Build.DEVICE,Build.BOARD,Build.HARDWARE,Build.BRAND,Build.VERSION.RELEASE,Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName(), Build.VERSION.SDK_INT+"",Build.BOOTLOADER,Build.FINGERPRINT,Build.getRadioVersion(),getProperty("os.arch"),"ART"+getProperty("java.vm.version"),upTime};
        ListView list=findViewById(R.id.generalList);

        //set adapter
        MyAdapter adapter=new MyAdapter(this,titles,descriptins);
        list.setAdapter(adapter);

    }

    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String mytitles[];
        String mydescriptions[];

        MyAdapter(Context c,String[] titles,String[] descriptions){
            super(c,R.layout.tworow,R.id.title,titles);
            this.context=c;
            this.mytitles=titles;
            this.mydescriptions=descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.tworow,parent,false);
            //views in tworow.xml
            TextView myTitle=row.findViewById(R.id.titleTv);
            TextView myDescription=row.findViewById(R.id.descTv);
            //set text to views
            myTitle.setText(titles[position]);
            myDescription.setText(descriptins[position]);
            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//when pressed go to back activity
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
