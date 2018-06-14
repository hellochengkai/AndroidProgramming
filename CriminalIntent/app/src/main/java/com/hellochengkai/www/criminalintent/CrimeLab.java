package com.hellochengkai.www.criminalintent;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-13.
 */

public class CrimeLab {
    Context context;
    private Map<UUID,Crime> uuidCrimeMap = new HashMap<>();
    private Map<Integer,Crime> positionCrimeMap = new HashMap<>();
    private CrimeLab(Context context) {
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
            crime.setPosition(i);
            uuidCrimeMap.put(crime.getmId(),crime);
            positionCrimeMap.put(crime.getPosition(),crime);
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

    public Crime getCrime(UUID id) {
        return uuidCrimeMap.get(id);
    }
    public Crime getCrime(int position) {
        return positionCrimeMap.get(position);
    }
    public int size(){
        return positionCrimeMap.size();
    }
}
