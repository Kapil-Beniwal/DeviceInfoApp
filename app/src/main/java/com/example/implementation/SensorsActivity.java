package com.example.implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        //actionbar
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("CPU");
//            actionBar.setSubtitle("Detailed list of available sensors");
//            //set back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sensors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //listview
        mListView = findViewById(R.id.sensor_lv);

        SensorManager sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors=sensorManager.getSensorList(Sensor.TYPE_ALL);
        mListView.setAdapter(new MySensorAdapter(this,R.layout.sensor_row,sensors));

        //total number of avail. sensors in device  i.e. list size
        String total=mListView.getCount()+" ";
        TextView totalSensors=findViewById(R.id.countSens);
        totalSensors.setText("Total Sensors :"+total);
        Toast.makeText(this,total+"sensors detected...",Toast.LENGTH_LONG).show();
    }
    //custom adapter class
    public static class MySensorAdapter extends ArrayAdapter<Sensor>{
        private  int textViewResourceId;

        private static class ViewHolder{
            TextView itemView;

        }
        //construcotr
        MySensorAdapter(Context context, int textViewResourceId, List<Sensor>items){
            super(context,textViewResourceId,items);
            this.textViewResourceId=textViewResourceId;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder=null;


            if (convertView==null){
                convertView= LayoutInflater.from(this.getContext()).inflate(textViewResourceId,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.itemView=(TextView)convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            Sensor items=getItem(position);
            //get all sensor list
            if (items!=null){
                viewHolder.itemView.setText("Name :"+items.getName()
                +"\n Int Type :"+items.getType()
                +"\nString Type :"+sensorTypeToString(items.getType())
                +"\nVendor :"+items.getVendor()
                +"\nVersion :"+items.getVersion()
                +"\nResolution :"+items.getResolution()
                +"\nPower :"+items.getPower()+"mAh"
                +"\nMaximum Range"+items.getMaximumRange());
            }
            return convertView;
        }
    }

    public static String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_TEMPERATURE:
                return "Temperature";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Orientation";
            case Sensor.TYPE_PRESSURE:
                return "Type Pressure";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            case Sensor.TYPE_HEART_BEAT:
                return "Heart Beat";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_POSE_6DOF:
                return "Pose 6DOF";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            default:
                return "Unknown";

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
