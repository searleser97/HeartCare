package san.com.heartcare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.github.mikephil.charting.data.Entry;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        PressureFragment.OnFragmentInteractionListener, ChartFragment.OnFragmentInteractionListener,
        ReportFragment.OnFragmentInteractionListener, BluetoothFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
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
                fragment = new BluetoothFragment();
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        System.out.println(fragment);
        if (fragment == null)
            return false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int)msg.arg1;
            int end = (int)msg.arg2;
            switch(msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    try {
                        if (fragmentId == 1) {
                            TextView pressureText = findViewById(R.id.pressure);
                            pressureText.setText("BPM: " + writeMessage);

                            if (((PressureFragment) fragment).aux == 1) {
                                ((PressureFragment) fragment).dataSet.removeLast();
                                ((PressureFragment) fragment).aux = 0;
                            }

                            ((PressureFragment) fragment).dataSet
                                    .addEntry(new Entry(((PressureFragment) fragment).dataSet.getEntryCount(),
                                            Float.parseFloat(writeMessage)));

                            ((PressureFragment) fragment).data.notifyDataChanged();
                            ((PressureFragment) fragment).chart.notifyDataSetChanged();

                            ((PressureFragment) fragment).chart.invalidate();
                        }
                    } catch (java.lang.NullPointerException e) {
                        System.out.println(e.getStackTrace());
                    }
            }
        }
    };
}
