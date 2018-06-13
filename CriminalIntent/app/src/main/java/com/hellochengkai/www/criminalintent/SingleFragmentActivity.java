package com.hellochengkai.www.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by chengkai on 18-6-13.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    private static final String TAG = "SingleFragmentActivity";
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fragmentManager = getSupportFragmentManager();
        addFragment(R.id.fragment_container);
    }
    private void addFragment(int rID)
    {
        Fragment fragment = fragmentManager.findFragmentById(rID);
        if(fragment == null){
            Log.d(TAG, "onCreate: new CrimeFragment(); ");
            fragment = createFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(rID, fragment).commit();
        }
    }

}
