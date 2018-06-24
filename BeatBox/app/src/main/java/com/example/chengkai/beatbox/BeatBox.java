package com.example.chengkai.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private AssetManager assetManager;
    private List<Sound> soundList = new ArrayList<>();
    public BeatBox(Context context) {
        assetManager = context.getAssets();
        loadSounds();
    }
    void loadSounds()
    {
        String[] soundNames = null;

        try {
            soundNames = assetManager.list(SOUNDS_FOLDER);
            Log.d(TAG, "loadSounds: " + soundNames.length);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadSounds: not find " + SOUNDS_FOLDER);
        }
        for (String filename : soundNames){
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            soundList.add(new Sound(assetPath));
        }
    }

    public List<Sound> getSoundList() {
        return soundList;
    }
}
