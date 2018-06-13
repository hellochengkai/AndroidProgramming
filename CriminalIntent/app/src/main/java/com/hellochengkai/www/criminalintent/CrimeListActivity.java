package com.hellochengkai.www.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by chengkai on 18-6-13.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
