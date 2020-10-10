package com.example.implementation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {
    TextView mTvTotalRam, mTvFreeRam, mTvUsedRam;
    TextView mTvTotalRom, mTvFreeRom, mTvUsedRom;
    TextView mTvTotalHeap;
    TextView mTvPercRam, mTvPercRom;
    ProgressBar mPBRam, mPBRom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        //actionbar
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("Memory");
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Memory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTvTotalRam = findViewById(R.id.totalRam);
        mTvFreeRam = findViewById(R.id.freeRam);
        mTvUsedRam = findViewById(R.id.usedRam);

        mTvTotalRom = findViewById(R.id.totalRom);
        mTvFreeRom = findViewById(R.id.freeRom);
        mTvUsedRom = findViewById(R.id.usedRom);

        //progressbar of ram and rom
        mPBRam = findViewById(R.id.pbRam);
        mPBRom = findViewById(R.id.pbRom);

        mTvPercRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);

        mTvTotalHeap = findViewById(R.id.totalHeap);

        //RAM getting info
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float totalMem = memoryInfo.totalMem / (1024 * 1024);  //total RAM
        float freeMem = memoryInfo.availMem / (1024 * 1024);  //free RAM
        float usedMem = totalMem - freeMem;  //used RAM


        //%age of free RAM
        float freeMemPerc = freeMem / totalMem * 100;
        //%age of used RAM
        float usedMemPerc = usedMem / totalMem * 100;

        //free RAM %age decimal point conversion
        NumberFormat numFormatFreePerc = NumberFormat.getNumberInstance();
        numFormatFreePerc.setMinimumFractionDigits(1);
        numFormatFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormatFreePerc.format(freeMemPerc);

        //used RAM %age to decimal conversion
        NumberFormat numFormatUsedPerc = NumberFormat.getNumberInstance();
        numFormatUsedPerc.setMinimumFractionDigits(1);
        numFormatUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numFormatUsedPerc.format(usedMemPerc);

        //ROM getting info

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        float availableBlocks = stat.getAvailableBlocks();
        float totalROM = (totalBlocks * blockSize) / (1024 * 1024);//rom  in mb
        float freeROM = (availableBlocks * blockSize) / (1024 * 1024);//free rom in mb
        float usedROM = totalROM - freeROM;//value of rom in mb

        //ROM %age
        float freeRomPerc = (freeROM / totalROM * 100);
        float usedRomPerc = (usedROM / totalROM * 100);

        //Total rom decimal point conversion
        NumberFormat numFormatTotalRom = NumberFormat.getNumberInstance();
        numFormatTotalRom.setMinimumFractionDigits(1);
        numFormatTotalRom.setMaximumFractionDigits(1);
        String mTotalRom = numFormatTotalRom.format(totalROM);

        //free rom decimal conversion
        NumberFormat numFormatFreeRom = NumberFormat.getNumberInstance();
        numFormatFreeRom.setMinimumFractionDigits(1);
        numFormatFreeRom.setMaximumFractionDigits(1);
        String mFreeROM = numFormatFreeRom.format(freeROM);
        //used rom decimal point conversion
        NumberFormat numFormatUsedRom = NumberFormat.getNumberInstance();
        numFormatUsedRom.setMinimumFractionDigits(1);
        numFormatUsedRom.setMaximumFractionDigits(1);
        String mUsedRom = numFormatUsedRom.format(usedROM);
        //free rom %age decimal point conversion
        NumberFormat numFormatFreeRomPerc = NumberFormat.getNumberInstance();
        numFormatFreeRomPerc.setMinimumFractionDigits(1);
        numFormatFreeRomPerc.setMaximumFractionDigits(1);
        String mFreeRomPerc = numFormatFreeRomPerc.format(freeRomPerc);

        //used rom %age decimal conversion
        NumberFormat numFormatUsedRomPerc = NumberFormat.getNumberInstance();
        numFormatUsedRomPerc.setMinimumFractionDigits(1);
        numFormatUsedRomPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numFormatUsedRomPerc.format(usedRomPerc);


        //setting RAM info
        mTvTotalRam.setText(" " + totalMem + "MB");
        mTvFreeRam.setText(" " + freeMem + "MB" + "(" + mFreeMemPerc + "%)");
        mTvUsedRam.setText(" " + usedMem + "MB" + "(" + mUsedMemPerc + "%)");

        //setting used ROM
        mTvTotalRom.setText(" " + mTotalRom + "MB");
        mTvFreeRom.setText(" " + mFreeROM + "MB" + "(" + mFreeRomPerc + "%)");
        mTvUsedRom.setText(" " + mUsedRom + "MB" + "(" + mUsedRomPerc + "%)");

        //getting java heap
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        //setting java heap info
        mTvTotalHeap.setText(" " + maxMemory / (1024 * 1024) + "MB");

        //setting ram info to progressbar and text view on progressbar
        mTvPercRam.setText(mUsedMemPerc + "% Used");
        mPBRam.setProgress((int) usedMemPerc);

        //setting ROM info to progress bar
        mTvPercRom.setText(mUsedRomPerc + "% Used");
        mPBRom.setProgress((int) ((usedROM / totalROM) * 100));


    }

    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
