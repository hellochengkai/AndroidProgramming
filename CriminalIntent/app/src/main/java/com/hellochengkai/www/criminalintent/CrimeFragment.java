package com.hellochengkai.www.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-12.
 */

public class CrimeFragment extends Fragment implements TextWatcher, CompoundButton.OnCheckedChangeListener,View.OnClickListener {
    private static final String TAG = "CrimeFragment";
    private static final String ARG_CRIME_ID = "crime_id";
    private static final int REQUEST_DATE = 0;
    private Crime crime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    public static CrimeFragment newInstance(UUID uuid) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CRIME_ID, uuid);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        crime = CrimeLab.getInstance(getContext()).getCrime((UUID) getArguments().getSerializable(ARG_CRIME_ID));
        if(crime == null){
            crime = new Crime();
            crime.setmTitle("无效的数据");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = view.findViewById(R.id.crime_title);
        mTitleField.setText(crime.getmTitle());
        mTitleField.addTextChangedListener(this);
        mDateButton = view.findViewById(R.id.crime_date);
        updataDate();
        mDateButton.setOnClickListener(this);
//        mDateButton.setEnabled(false);
        mSolvedCheckBox = view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(crime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    private void returnResult()
    {
        Intent intent = new Intent();
        intent.putExtra(ARG_CRIME_ID,crime.getmId());
        getActivity().setResult(1,intent);
    }

    public static UUID getResultCrimeID(Intent intent)
    {
        return (UUID) intent.getSerializableExtra(ARG_CRIME_ID);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged: "+ s.toString());
        crime.setmTitle(s.toString());
        returnResult();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.crime_solved: {
                if(isChecked != crime.ismSolved()){
                    crime.setmSolved(isChecked);
                    returnResult();
                }
                break;
            }
        }
    }
    private static final String DIALOG_DATE = "DialogDate";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crime_date:{
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(crime.getDate());
                datePickerFragment.setTargetFragment(this,REQUEST_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode){
            case REQUEST_DATE:{
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.Extra_DATE);
                crime.setDate(date);
                updataDate();
            }
        }
    }

    private void updataDate() {
        mDateButton.setText(crime.getDateStr());
    }
}
