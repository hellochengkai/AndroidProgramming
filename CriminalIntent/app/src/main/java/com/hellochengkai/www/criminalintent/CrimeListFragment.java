package com.hellochengkai.www.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-13.
 */

public class CrimeListFragment extends Fragment {
    private static final String TAG = CrimeListFragment.class.getSimpleName();
    private RecyclerView mCrimeRecyclerView;
    private CrimeLab crimeLab;
    private static final int CRIME_VIEW_TYPE_DEF = 0;
    private static final int CRIME_VIEW_TYPE_CALL110 = 1;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_crime:{
                Crime crime = new Crime();
                crimeLab.addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getmId());
                startActivity(intent);
                return true;
            }
            case R.id.show_subtitle:
            {
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        crimeLab = CrimeLab.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCrimeRecyclerView.setLayoutManager(linearLayoutManager);
        updataUI(null);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        MenuItem menuItem = menu.findItem(R.id.show_subtitle);
        if(!mSubtitleVisible){
            menuItem.setTitle(R.string.show_subtitle);
        }else {
            menuItem.setTitle(R.string.hide_subtitle);
        }
        updateSubtitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        updataUI(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:{
//                UUID uuid = CrimeFragment.getResultCrimeID(data);
//                updataUI(null);
            }
        }
    }

    private void updataUI(UUID uuid) {
        CrimeAdapter crimeAdapter = (CrimeAdapter) mCrimeRecyclerView.getAdapter();
        if(crimeAdapter != null){
            Crime crime = crimeLab.getCrime(uuid);
            if(crime != null){
                int position = crime.getPosition();
                if(position > 0){
                    crimeAdapter.notifyItemChanged(position);
                    Log.d(TAG, "updataUI: notifyItemChanged " + position);
                    return;
                }
            }
            crimeAdapter.notifyDataSetChanged();
        }else {
            crimeAdapter = new CrimeAdapter(crimeLab);
            mCrimeRecyclerView.setAdapter(crimeAdapter);
        }
        updateSubtitle();
    }
    private void updateSubtitle() {
        int crimeCount = crimeLab.size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
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
            crimeTDateTV.setText(crime.getDateStr());
            if (crime.ismSolved()) {
                crimeSolvedImg.setVisibility(View.VISIBLE);
            } else {
                crimeSolvedImg.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if(crime == null){
                return;
            }
//            startActivityForResult(CrimeActivity.newIntent(getContext(),crime.getmId()),1);
//            startActivityForResult(CrimePagerActivity.newIntent(getContext(),crime.getmId()),1);
            startActivity(CrimePagerActivity.newIntent(getContext(),crime.getmId()));
        }
    }
//
//    private class CrimeHolder110 extends CrimeHolder
//            implements View.OnClickListener {
//        public CrimeHolder110(LayoutInflater inflater, ViewGroup viewGroup) {
//            super(R.layout.list_item_crime_110, inflater, viewGroup);
//        }
//
//        @Override
//        public void onClick(View v) {
//            super.onClick(v);
//            Toast.makeText(getContext(), crime.getmTitle(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private CrimeLab crimeLab;

        public CrimeAdapter(CrimeLab crimeLab) {
            this.crimeLab = crimeLab;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
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
                crimeHolder.bindData(crimeLab.getCrime(position));
            }
//            else if (holder instanceof CrimeHolder110) {
//                CrimeHolder110 crimeHolder = (CrimeHolder110) holder;
//                crimeHolder.bindData(crimeLab.getCrime(position));
//            }
        }

        @Override
        public int getItemCount() {
            return crimeLab.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (crimeLab.getCrime(position).isNeedCall110()) {
                return CRIME_VIEW_TYPE_CALL110;
            }
            return CRIME_VIEW_TYPE_DEF;
        }
    }
}
