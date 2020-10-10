package com.example.implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.text.format.Formatter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceIDActivity extends AppCompatActivity {
    //phone state permission
    private static final int READ_PHONE_STATE_PERMISSION = 1;
    private String titles[];
    private String descriptions[];
    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_i_d);
        //actionbar
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("Device ID");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DeviceId");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Android device id
        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //IMEI number
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //if system os is marshmellow or above need runtime permission
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED) {//permission was not granted
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_PERMISSION);//show popup for request
            } else {
                //permission was granted
                imei = tm.getDeviceId();
                simCardSerial = tm.getSimSerialNumber();
                simSubscriber = tm.getSubscriberId();
            }
        } else {
            //system os is <marshmello, no need runtime permission
            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriber = tm.getSubscriberId();
        }

        //IP Address
        String ipAddress= "No Internet Connection";
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        ipAddress = getMobileIPAddress();
                    }
                }

            }
        } else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null) {
            ipAddress = is3GEnabled + "";
            }
        }

        //wi-fi mac address
        String wifiAddress = "No wifi Connection";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            assert connManager != null;
            Network[] networks = connManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        wifiAddress = getWiFiIPAddress();
                    }
                }

            }
        } else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null) {
                wifiAddress = is3GEnabled + "";
            }
        }


        //Bluetooth mac address
        String bt = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");
        //array containing data
        titles = new String[]{"Android Device ID", "IMEI, MEID or ESN", "HardwareSerial Number", "Sim Card Serial Number", "SIM Subscriber ID", "IP Address", "Wi-Fi Mac Address", "Bluetooth Mac Address", "Build Fingerprint"};
        descriptions = new String[]{deviceid, imei, Build.SERIAL, simCardSerial, simSubscriber, ipAddress,wifiAddress, bt, Build.FINGERPRINT};

        ListView listView = findViewById(R.id.devIdList);
        //set adpter to list view
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(adapter);
    }

    private String getWiFiIPAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);//may be wrong library is imported for formatter
    }

    private static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }

        } catch (Exception e) {
        }
        return " ";
    }

    //create custom adapter class
    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitles[], myDescriptions[];

        public MyAdapter(Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //inflating tworow,xml
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow, parent, false);
            //views in tworow.xml
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDescr = row.findViewById(R.id.descTv);
            //set text to these views
            myTitle.setText(titles[position]);
            myDescr.setText(descriptions[position]);

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    recreate();//restart activity on permission granted

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
                    imei = tm.getDeviceId();
                simCardSerial = tm.getSimSerialNumber();
                simSubscriber = tm.getSubscriberId();
            }
            else{
                //permission was rejected
                Toast.makeText(this,"Enable READ_PHONE_STATE permission",Toast.LENGTH_SHORT).show();
               }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //hide search view from activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}
