package com.san.heartcare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.github.mikephil.charting.data.Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.san.heartcare.models.HeartRate;
import com.san.heartcare.models.Location;
import java.sql.Timestamp;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        PressureFragment.OnFragmentInteractionListener, ChartFragment.OnFragmentInteractionListener,
        ReportFragment.OnFragmentInteractionListener, BluetoothFragment.OnFragmentInteractionListener {
        private DatabaseReference mDB;
        private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        mDB = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        loadFragment(new PressureFragment());
    }

    Fragment fragment = null;
    int fragmentId = 0;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_pressure:
                fragmentId = 1;
                fragment = new PressureFragment();
                break;
            case R.id.navigation_chart:
                fragmentId = 2;
                fragment = new ChartFragment();
                break;
            case R.id.navigation_report:
                fragmentId = 3;
                fragment = new ReportFragment();
                break;
            case R.id.navigation_settings:
                fragmentId = 4;
//                fragment = new BluetoothFragment();
                fragment = new SettingsFragment();
        }
        return loadFragment(fragment);
    }

    public boolean loadFragment(Fragment fragment) {
        System.out.println(fragment);
        if (fragment == null)
            return false;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fragmentId == 5) {
            fragmentId = 4;
            loadFragment(new SettingsFragment());
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = msg.arg1;
            int end = msg.arg2;
            switch(msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    String[] input = writeMessage.substring(begin, end - 1).split(" ");
                    String sensor1 = input[0];
                    String sensor2 = input[1];
                    try {
                        if (fragmentId == 1) {
                            TextView pressureText = findViewById(R.id.pressure);
                            pressureText.setText(sensor1 + " ");

                            if (((PressureFragment) fragment).aux == 1) {
                                ((PressureFragment) fragment).dataSet.removeLast();
                                ((PressureFragment) fragment).aux = 0;
                            }

                            ((PressureFragment) fragment).dataSet
                                    .addEntry(new Entry(((PressureFragment) fragment).dataSet.getEntryCount(),
                                            Float.parseFloat(sensor1)));

                            ((PressureFragment) fragment).data.notifyDataChanged();
                            ((PressureFragment) fragment).chart.notifyDataSetChanged();

                            ((PressureFragment) fragment).chart.invalidate();

                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            System.out.println(timestamp.toString());

                            String heartRateId = mDB.child("heart_rates").push().getKey();
                            mDB.child("users").child(mAuth.getCurrentUser().getUid()).child("heart_rates").child(heartRateId).setValue(true);
                            mDB.child("heart_rates").child(heartRateId).setValue(new HeartRate(sensor1, new Location(123, 222), timestamp.toString()));


                            TextView sensor2Text = findViewById(R.id.sensor2);
                            sensor2Text.setText(sensor2 + " units");

                            if (((PressureFragment) fragment).aux2 == 1) {
                                ((PressureFragment) fragment).dataSet2.removeLast();
                                ((PressureFragment) fragment).aux2 = 0;
                            }

                            ((PressureFragment) fragment).dataSet2
                                    .addEntry(new Entry(((PressureFragment) fragment).dataSet2.getEntryCount(),
                                            Float.parseFloat(sensor2)));

                            ((PressureFragment) fragment).data2.notifyDataChanged();
                            ((PressureFragment) fragment).chart2.notifyDataSetChanged();

                            ((PressureFragment) fragment).chart2.invalidate();
                        }
                    } catch (java.lang.NullPointerException e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
            }
        }
    };
}
