package com.hellochengkai.www.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-13.
 */

public class CrimeLab {
    private List crimeList = new ArrayList();
    Context context;
    private CrimeLab(Context context) {
        crimeList = new ArrayList<Crime>();
        this.context = context;
        for (int i = 0; i < 100; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            Crime crime = new Crime();
            stringBuilder.append(context.getResources().getString(R.string.crime))
                    .append(" #")
                    .append(i);
            crime.setmTitle(stringBuilder.toString());
            crime.setmSolved(i % 2 == 0);
            crime.setNeedCall110(i % 5 == 0);
            crimeList.add(crime);
        }
    }

    private static CrimeLab instance;

    public static CrimeLab getInstance(Context context) {
        if (instance == null) {
            synchronized (CrimeLab.class) {
                if (instance == null) {
                    instance = new CrimeLab(context);
                }
            }
        }
        return instance;
    }

    public List<Crime> getCrimeList() {
        return crimeList;
    }

    public Crime getCrime(UUID id){
        Iterator iterator = crimeList.iterator();
        while (iterator.hasNext()){
            Crime crime = (Crime) iterator.next();
            if(crime.getmId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
