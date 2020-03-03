package com.unity3d.player;

import android.app.Activity;
import android.os.Bundle;

import com.mreoz.MRZStart;
import com.mreoz.R;

public class UnityPlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_player);
        MRZStart.LoadMe(this);
    }
}
