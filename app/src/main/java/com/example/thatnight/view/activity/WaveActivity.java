package com.example.thatnight.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thatnight.view.R;
import com.example.thatnight.view.view.WaveView;

public class WaveActivity extends AppCompatActivity {

    private WaveView mWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);

        mWaveView = (WaveView) findViewById(R.id.wv);
        mWaveView.start();
    }
}
