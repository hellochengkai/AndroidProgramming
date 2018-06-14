package com.hellochengkai.www.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-14.
 */

public class CrimePagerActivity extends AppCompatActivity{
    private static final String TAG = "CrimePagerActivity";
    private static final String EXTRA_CRIME_ID = "com.hellochengkai.www.criminalintent.crime_id";

    private CrimeLab crimeLab;
    private ViewPager viewPager;

    public static Intent newIntent(Context context, UUID crimeId)
    {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        crimeLab = CrimeLab.getInstance(this);
        viewPager = findViewById(R.id.crime_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = crimeLab.getCrime(position);
                return CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return crimeLab.size();
            }
        });
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        viewPager.setCurrentItem(crimeLab.getCrime(crimeId).getPosition());
    }
}
