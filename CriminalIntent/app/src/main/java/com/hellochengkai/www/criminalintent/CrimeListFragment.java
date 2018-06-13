package com.hellochengkai.www.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-13.
 */

public class CrimeListFragment extends Fragment {
    private static final String TAG = CrimeListFragment.class.getSimpleName();
    private RecyclerView mCrimeRecyclerView;

    private static final int CRIME_VIEW_TYPE_DEF = 0;
    private static final int CRIME_VIEW_TYPE_CALL110 = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updataUI(null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updataUI(null);
    }

    private void updataUI(UUID uuid) {
        CrimeAdapter crimeAdapter = (CrimeAdapter) mCrimeRecyclerView.getAdapter();
        if(crimeAdapter != null){
            int position = crimeAdapter.getPosition(uuid);
            if(position > 0){
                crimeAdapter.notifyItemChanged(position);
            }else {
                crimeAdapter.notifyDataSetChanged();
            }
        }else {
            CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
            crimeAdapter = new CrimeAdapter(crimeLab.getCrimeList());
            mCrimeRecyclerView.setAdapter(crimeAdapter);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        protected Crime crime;
        private TextView crimeTitleTV;
        private TextView crimeTDateTV;
        private ImageView crimeSolvedImg;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            crimeTitleTV = itemView.findViewById(R.id.crime_title);
            crimeTDateTV = itemView.findViewById(R.id.crime_date);
            crimeSolvedImg = itemView.findViewById(R.id.crime_solved);
            itemView.setOnClickListener(this);
        }

        public CrimeHolder(int rID, LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(rID, parent, false));
            crimeTitleTV = itemView.findViewById(R.id.crime_title);
            crimeTDateTV = itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }

        public void bindData(Crime crime) {
            this.crime = crime;
            crimeTitleTV.setText(crime.getmTitle() + "   " + crime.isNeedCall110());
            crimeTDateTV.setText(crime.getDate());
            if (crime.ismSolved()) {
                crimeSolvedImg.setVisibility(View.VISIBLE);
            } else {
                crimeSolvedImg.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(),crime.getmTitle(),Toast.LENGTH_SHORT).show();
            CrimeActivity.startCrimeActivity(getContext(), crime.getmId());
        }
    }

    private class CrimeHolder110 extends CrimeHolder
            implements View.OnClickListener {
        public CrimeHolder110(LayoutInflater inflater, ViewGroup viewGroup) {
            super(R.layout.list_item_crime_110, inflater, viewGroup);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            Toast.makeText(getContext(), crime.getmTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        public int getPosition(UUID uuid)
        {
            if(uuid == null)
                return -1;
            int i = 0;
            Iterator iterator = mCrimes.iterator();
            while (iterator.hasNext()){
                Crime crime = (Crime) iterator.next();
                if(crime.getmId().equals(uuid)){
                    return i;
                }else {
                    i++;
                }
            }
            return -1;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            Log.d(TAG, "onCreateViewHolder: " + viewType);
//            switch (viewType){
//                case CRIME_VIEW_TYPE_CALL110:{
//                    return new CrimeHolder110(layoutInflater,parent);
//                }
//            }
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof CrimeHolder) {
                CrimeHolder crimeHolder = (CrimeHolder) holder;
                crimeHolder.bindData(mCrimes.get(position));
            } else if (holder instanceof CrimeHolder110) {
                CrimeHolder110 crimeHolder = (CrimeHolder110) holder;
                crimeHolder.bindData(mCrimes.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isNeedCall110()) {
                return CRIME_VIEW_TYPE_CALL110;
            }
            return CRIME_VIEW_TYPE_DEF;
        }
    }
}
