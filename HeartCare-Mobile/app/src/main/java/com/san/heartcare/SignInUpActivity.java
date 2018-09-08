package com.san.heartcare;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class SignInUpActivity extends AppCompatActivity implements SignInFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener {
    public int fragmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        fragmentId = 1;
        loadFragment(new SignInFragment());
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (fragmentId == 2) {
            fragmentId = 1;
            loadFragment(new SignInFragment());
        } else {
            super.onBackPressed();
        }

    }
}
