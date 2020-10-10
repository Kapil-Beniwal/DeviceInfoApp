package com.example.implementation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Process;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

public class CpuActivity extends AppCompatActivity {
    ProcessBuilder mProcessBuilder;
    String holder="";
    String[] DATA={"/system/bin/cat","/proc/cpuinfo"};
    InputStream mInputStream;
    java.lang.Process mProcess;
    byte []mByteArray;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
        //actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CPU");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ActionBar actionBar=getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setTitle("CPU");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }

        //ListView
        mListView=findViewById(R.id.cpuList);

        //getting information of cpu
        mByteArray= new byte[1024];
        try {
            mProcessBuilder=new ProcessBuilder(DATA);
            mProcess=mProcessBuilder.start();
            mInputStream=mProcess.getInputStream();
            while (mInputStream.read(mByteArray)!=-1){
                holder=holder+new String(mByteArray);
            }
            //close input stream
            mInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }


        //adapter
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        android.R.id.text1, Collections.singletonList(holder));
        //set adapter to lv
        mListView.setAdapter(adapter);
    }
}
