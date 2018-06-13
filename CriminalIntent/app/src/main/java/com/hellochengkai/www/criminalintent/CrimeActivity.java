package com.hellochengkai.www.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.hellochengkai.www.criminalintent.crime_id";
    @Override
    protected Fragment createFragment() {
        UUID id = (UUID)getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(id);
    }

    public static void startCrimeActivity(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        context.startActivity(intent);
    }
}
