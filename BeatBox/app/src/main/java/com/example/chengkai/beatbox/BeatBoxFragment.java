package com.example.chengkai.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chengkai.beatbox.databinding.FragmentBeatBoxBinding;
import com.example.chengkai.beatbox.databinding.ListItemSoundBinding;

import java.util.List;

public class BeatBoxFragment extends Fragment {
    BeatBox beatBox;
    public static BeatBoxFragment newInstance() {

        Bundle args = new Bundle();

        BeatBoxFragment fragment = new BeatBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding fragmentBeatBoxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int i) {
//                if(i%2 == 0)
//                    return 1;
//                else
//                    return 3;
//            }
//        });
        fragmentBeatBoxBinding.recyclerView.setAdapter(new SoundAdapter(beatBox.getSoundList()));
        fragmentBeatBoxBinding.recyclerView.setLayoutManager(gridLayoutManager);
        return fragmentBeatBoxBinding.getRoot();
    }

    private class SoundHodler extends RecyclerView.ViewHolder {
        private ListItemSoundBinding listItemSoundBinding;

        public SoundHodler(ListItemSoundBinding listItemSoundBinding) {
            super(listItemSoundBinding.getRoot());
            this.listItemSoundBinding = listItemSoundBinding;
            listItemSoundBinding.setViewModel(new SoundViewModel(beatBox));
        }
        public void bindData(Sound sound)
        {
            listItemSoundBinding.getViewModel().setSound(sound);
            listItemSoundBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHodler> {
        private List<Sound> soundList;

        public SoundAdapter(List<Sound> soundList) {
            this.soundList = soundList;
        }

        @NonNull
        @Override
        public SoundHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            ListItemSoundBinding listItemSoundBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_sound, viewGroup, false);

            return new SoundHodler(listItemSoundBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHodler soundHodler, int i) {
            Sound sound = soundList.get(i);
            soundHodler.bindData(sound);
        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }
    }
}
