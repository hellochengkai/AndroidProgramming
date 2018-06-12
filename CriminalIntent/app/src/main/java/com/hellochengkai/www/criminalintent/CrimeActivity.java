package com.hellochengkai.www.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CrimeActivity extends AppCompatActivity {
    private static final String TAG = "CrimeActivity";
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        fragmentManager = getSupportFragmentManager();
        addFragment(R.id.fragment_container);
    }
    private void addFragment(int rID)
    {
        Fragment fragment = fragmentManager.findFragmentById(rID);
        if(fragment == null){
            Log.d(TAG, "onCreate: new CrimeFragment(); ");
            fragment = new CrimeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(rID, fragment).commit();
        }
    }
}
