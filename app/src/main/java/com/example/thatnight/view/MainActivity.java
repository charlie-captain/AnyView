package com.example.thatnight.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thatnight.view.activity.DrawActivity;
import com.example.thatnight.view.activity.WaveActivity;
import com.example.thatnight.view.view.DrawView;
import com.example.thatnight.view.view.WaveView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        mTitle = Arrays.asList("贝塞尔波浪", "贝塞尔画板");
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitle));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openActivity(WaveActivity.class);
                        break;
                    case 1:
                        openActivity(DrawActivity.class);
                        break;
                    default:

                        break;
                }
            }
        });
    }

    public void openActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }
}
