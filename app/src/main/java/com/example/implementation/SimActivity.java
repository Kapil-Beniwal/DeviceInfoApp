package com.example.implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

public class SimActivity extends AppCompatActivity {
    //code for runtime permission
    private static final int READ_PHONE_STATE_CODE = 1;
    //array ro contsin data to display in list view
    private String titles[];
    private String descriptions[];
    //
    ListView mListView;
    //class to get sim info
    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("Sim");
//            actionBar.setSubtitle("SIM 1");
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lv from xml
        mListView = findViewById(R.id.simListView);
        //telephony manager
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //handling rntime permission for os marshmallow or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED) {
                //this will called when permission is not allowed
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                //show pop up for runtime permission
                requestPermissions(permissions, READ_PHONE_STATE_CODE);
            } else {
                //called on permission grant
                getPhoneInfo();

            }
        } else {
            //called when system os is < marshmellow
            getPhoneInfo();
        }
    }

    //function to get siminfo
    private void getPhoneInfo() {
        //sim state eg ready
        int ss = tm.getSimState();
        String simState = " ";
        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "Network Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO Error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "PERM Disabled";
                break;
        }
        //service provider
        String serviceProvider = tm.getSimOperatorName();
        String mobOperator = tm.getNetworkOperatorName();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String simID = tm.getSimSerialNumber();
        //unique device id
        String imei = tm.getDeviceId();
        String tmSubscriberID = tm.getSubscriberId();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String softVersion = tm.getDeviceSoftwareVersion();
        String country = tm.getSimCountryIso();
        String mcc_mnc = tm.getSimOperator();
        String voiceMailTag = tm.getVoiceMailAlphaTag();
        Boolean roamingStatus = tm.isNetworkRoaming();


        //adding this info to the arrays
        titles = new String[]{"Sim State",
                "Service Provider",
                "Mobile Operator Name",
                "Integrated Circuit Card Identifier(ICCID)",
                "Unique Device ID(IMEI)",
                "International Mobile Subscriber ID(IMSI)",
                "Device Software Version",
                "Mobile country Code(MCC)",
                "Mobile Country Code (MCC) + Mobile Network Code(MNC)",
                "Voicemail",
                "Roaming"};
        descriptions=new String[]{
                ""+simState,
                ""+serviceProvider,
                ""+mobOperator,
                ""+simID,
                ""+imei,
                ""+tmSubscriberID,
                ""+softVersion,
                ""+country,
                ""+mcc_mnc,
                ""+voiceMailTag,
                ""+roamingStatus
        };

        //setting adapter
        mListView.setAdapter(new MyAdapter(this,titles,descriptions));

    }

    //creating custom adapter class for lv
    private class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitles[];
        String myDescriptions[];

        //constructor
        MyAdapter(Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.tworow, parent, false);
            //text view from tworow.xml
            TextView myTitleTv = view.findViewById(R.id.titleTv);
            TextView myDescrTv = view.findViewById(R.id.descTv);
            //set text to these views
            myTitleTv.setText(titles[position]);
            myDescrTv.setText(descriptions[position]);
            return view;
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was allowed
                    getPhoneInfo();
                } else {
                    //permission was denied
                    Toast.makeText(this, "READ PHONE STATE permission is required", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
