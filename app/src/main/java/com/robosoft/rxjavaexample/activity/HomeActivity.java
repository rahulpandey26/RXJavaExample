package com.robosoft.rxjavaexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.robosoft.rxjavaexample.R;

/**
 * Created by Rahul Kumar Pandey on 17-05-2017.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.first).setOnClickListener(this);
        findViewById(R.id.second).setOnClickListener(this);
        findViewById(R.id.third).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.first:
                intent = new Intent(this,RxJavaSimpleActivity.class);
                startActivity(intent);
                break;
            case R.id.second:
                intent = new Intent(this,BooksActivity.class);
                startActivity(intent);
                break;
            case R.id.third:
                intent = new Intent(this,ColorsActivity.class);
                startActivity(intent);
                break;
            case R.id.four:
                intent = new Intent(this,SchedulerActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
