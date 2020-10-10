package com.example.implementation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class UserAppActivity extends AppCompatActivity {
    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;

    ListView userInstalledApptv;
    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        //actionbar
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("User Apps");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Apps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        userInstalledApptv = findViewById(R.id.installed_app_list);
        //call method to get installed apps
        installedApps=getInstalledApps();
        //Adapter
        installedAppAdapter=new AppAdapter(UserAppActivity.this,installedApps);
        //set adapter
        userInstalledApptv.setAdapter(installedAppAdapter);
        //list item click listener
        userInstalledApptv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //options to display alert dialog
                String[]options={"Open App","App Info","Uninstall"};
                //Alert dialog Builder
                AlertDialog.Builder builder=new AlertDialog.Builder(UserAppActivity.this);
                //set title
                builder.setTitle("Choose Action");

                //set options
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      if (which==0){
                          //it means "open app is clicked"
                          Intent intent=getPackageManager().getLeanbackLaunchIntentForPackage(installedApps.get(i).packages);
                          if (intent!=null){
                              startActivity(intent);
                          }
                          else{
                              //if anything wrong dispay error message
                              Toast.makeText(UserAppActivity.this,"Error,Please rty again",Toast.LENGTH_LONG).show();
                          }
                      }
                      if (which==1){
                          //app info is clicked
                          Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                          intent.setData(Uri.parse("package:"+installedApps.get(i).packages));
                          //show package name
                          Toast.makeText(UserAppActivity.this,installedApps.get(i).packages,Toast.LENGTH_LONG).show();
                          startActivity(intent);//start/open settings activity

                      }
                      if (which==2){
                          //uninstall
                          String packages=installedApps.get(i).packages;
                          Intent intent=new Intent(Intent.ACTION_DELETE);
                          intent.setData(Uri.parse("package:"+packages));
                          startActivity(intent);
                          recreate();//restart activity to update applist after uninstalling app
                      }
                    }
                });
                //show dialog
                builder.show();
            }
        });

        //getting total number of installed apps
        String size=userInstalledApptv.getCount()+"";
        //show in text view
        TextView countApps=findViewById(R.id.countApps);
        countApps.setText("Total Installed Apps:"+size);
    }

    //creating applist class
    public class AppList {
        private String name;
        Drawable icon;
        String packages;
        String version;

        //constructor
        AppList(String name, Drawable icon, String packages, String version) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
    //create AppAdapter

    public class  AppAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        List<AppList>listStorage;

        //constructor
        AppAdapter(Context context,List<AppList> customizedlistView){
            //layout inflator
            layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage=customizedlistView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder listViewHoler = new ViewHolder();
            if (convertView==null){
                listViewHoler=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.modelapps,parent,false);

                listViewHoler.textInListView=convertView.findViewById(R.id.list_app_name);
                listViewHoler.imageListView=convertView.findViewById(R.id.app_icon);
                listViewHoler.packageInListView=convertView.findViewById(R.id.app_package);
                listViewHoler.versionInListView=convertView.findViewById(R.id.version);

                convertView.setTag(listViewHoler);
            }
            else {
                listViewHoler=(ViewHolder)convertView.getTag();
            }
            //set data to our views
            listViewHoler.textInListView.setText(listStorage.get(position).getName());
            listViewHoler.imageListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHoler.packageInListView.setText(listStorage.get(position).getPackages());
            listViewHoler.versionInListView.setText(listStorage.get(position).getVersion());
            return convertView;  //ret the whole view
        }
        class ViewHolder{
            //our views from modelApp xml
            TextView textInListView;
            ImageView imageListView;
            TextView packageInListView;
            TextView versionInListView;
        }
    }
    //get app information

    private List<AppList> getInstalledApps(){
        apps=new ArrayList<AppList>();
        packs=getPackageManager().getInstalledPackages(0);
        for (int i =0;i<packs.size();i++){
            PackageInfo p=packs.get(i);
           if ((!isSystemPackage(p))){
               String appName=p.applicationInfo.loadLabel(getPackageManager()).toString();
         //get application icon
               Drawable icon=p.applicationInfo.loadIcon(getPackageManager());
               //package
               String packags=p.applicationInfo.packageName;
               //version
               String version=p.versionName;

               //add data
               apps.add(new AppList(appName,icon,packags,version));
           }
        }
        return  apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo){
        //to check  id app is not system app
        return (pkgInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)!=0;
    }


    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
