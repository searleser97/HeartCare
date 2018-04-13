package san.com.heartcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        PressureFragment.OnFragmentInteractionListener, ChartFragment.OnFragmentInteractionListener,
        ReportFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_pressure:
                System.out.println("holaaque chow");
                fragment = new PressureFragment();
                break;
            case R.id.navigation_chart:
                System.out.println("holaaque chow2");
                fragment = new ChartFragment();
                break;
            case R.id.navigation_report:
                System.out.println("holaaque chow3");
                fragment = new ReportFragment();
                break;
            case R.id.navigation_settings:
                Intent intent = new Intent(this, BluetoothActivity.class);
                startActivity(intent);
                System.out.println("holaaque chow4");
//                fragment = new SettingsFragment();
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
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
}
