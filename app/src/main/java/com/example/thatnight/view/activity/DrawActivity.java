package com.example.thatnight.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thatnight.view.R;
import com.example.thatnight.view.adapter.ColorSpAdapter;
import com.example.thatnight.view.view.DrawView;

import java.util.Arrays;
import java.util.List;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawView mDrawView;
    private Spinner mSpPenColor;
    private Spinner mSpBackground;
    private ColorSpAdapter mAdapter;
    private String[] mStrings;
    private String[] colors = {};
    private List<String> mItems;

    private ImageButton mRevert, mCancel, mAdd, mRemove, mReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        mDrawView = (DrawView) findViewById(R.id.dv);
        mSpPenColor = (Spinner) findViewById(R.id.sp_pen);
        mSpBackground = (Spinner) findViewById(R.id.sp_bg);
        mRevert = (ImageButton) findViewById(R.id.ib_revert);
        mCancel = (ImageButton) findViewById(R.id.ib_cancel);
        mAdd = (ImageButton) findViewById(R.id.ib_add);
        mRemove = (ImageButton) findViewById(R.id.ib_remove);
        mReset = (ImageButton) findViewById(R.id.ib_reset);
        mRevert.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mRemove.setOnClickListener(this);
        mReset.setOnClickListener(this);

        mStrings = getResources().getStringArray(R.array.colors);
        mItems = Arrays.asList(mStrings);
        mAdapter = new ColorSpAdapter(this, mItems);
        mSpPenColor.setAdapter(mAdapter);
        mSpBackground.setAdapter(mAdapter);
        mSpPenColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawView.changePenColor(getViewColor(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpBackground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawView.setBackgroundColor(getViewColor(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpPenColor.setSelection(0);
        mSpBackground.setSelection(1);
    }

    public int getViewColor(int i) {
        switch (i) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.WHITE;
            case 2:
                return Color.GRAY;
            case 3:
                return Color.RED;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.YELLOW;
            case 7:
                return getResources().getColor(R.color.puple);
        }
        return Color.BLACK;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_revert:
                mDrawView.revert();
                break;
            case R.id.ib_cancel:
                mDrawView.cancel();
                break;
            case R.id.ib_add:
                mDrawView.add();
                break;
            case R.id.ib_remove:
                mDrawView.minus();
                break;
            case R.id.ib_reset:
                mDrawView.reset();
                mSpBackground.setSelection(1);
                mSpPenColor.setSelection(0);
                break;
        }
    }
}
