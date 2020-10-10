package com.example.implementation;

import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.implementation.MyAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private MyAdapter mAdapter;


    private TextView mManufacturerTv,mAndroidVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        //recyclerView
        mrecyclerView=findViewById(R.id.myRecycler);
        //set its properties
        mrecyclerView.setLayoutManager(new GridLayoutManager(this,2));//grid view with 2 columns in each row
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adapter
        mAdapter=new MyAdapter(this,getmodel());
        mrecyclerView.setAdapter(mAdapter);

        //get device manuf. info
        String mManufacturer= Build.MANUFACTURER;
        //getting device model
        String model=Build.MODEL;
        //getting device android version
        String version=Build.VERSION.RELEASE;
        //getting android version name
        String verName=Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
        //setting these to our views in collapsing toolabr
        mManufacturerTv =findViewById(R.id.name_model);
        mAndroidVersionTv=findViewById(R.id.android_version);


        mManufacturerTv.setText(mManufacturer.toUpperCase()+""+model);
        mAndroidVersionTv.setText(version+""+verName);
        try {
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.JELLY_BEAN){
                Glide.with(this).load(R.drawable.android41).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.JELLY_BEAN_MR1){
                Glide.with(this).load(R.drawable.android41).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.JELLY_BEAN_MR2){
                Glide.with(this).load(R.drawable.android41).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.KITKAT){
                Glide.with(this).load(R.drawable.android44).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP){
                Glide.with(this).load(R.drawable.android50).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP_MR1){
                Glide.with(this).load(R.drawable.android60).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M){
                Glide.with(this).load(R.drawable.android60).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.N){
                Glide.with(this).load(R.drawable.android70).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.N_MR1){
                Glide.with(this).load(R.drawable.android70).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.O){
                Glide.with(this).load(R.drawable.android80).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.O_MR1){
                Glide.with(this).load(R.drawable.android80).into((ImageView)findViewById(R.id.backdrop));
            }
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.P){
                Glide.with(this).load(R.drawable.android90).into((ImageView)findViewById(R.id.backdrop));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout=findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //set collapsing toolbar
        collapsingToolbarLayout.setTitle("Device Info");
    }

    //add models to array
    private ArrayList<Model>getmodel(){
        ArrayList<Model>models=new ArrayList<>();
        Model p=new Model();

        p=new Model();
        p.setName("General");
        p.setImg(R.drawable.general);
        models.add(p);

        p=new Model();
        p.setName("Device Id");
        p.setImg(R.drawable.devid);
        models.add(p);

        p=new Model();
        p.setName("Display");
        p.setImg(R.drawable.display);
        models.add(p);

        p=new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p=new Model();
        p.setName("User Apps");
        p.setImg(R.drawable.userapps);
        models.add(p);

        p=new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.systemapps);
        models.add(p);

        p=new Model();
        p.setName("Memory");
        p.setImg(R.drawable.memory);
        models.add(p);

        p=new Model();
        p.setName("CPU");
        p.setImg(R.drawable.cpu);
        models.add(p);
        p=new Model();
        p.setName("Sensors");
        p.setImg(R.drawable.sensor);
        models.add(p);
        p=new Model();
        p.setName("SIM");
        p.setImg(R.drawable.sim);
        models.add(p);
        return models;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //this fun is called when button in keyboard is pressed
                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //this fun calls whenever we type in searchview
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getGroupId();
        //handle other menu item clicks
        if (id==R.id.action_setting){
            Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
