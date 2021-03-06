package com.robosoft.rxjavaexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.robosoft.rxjavaexample.R;
import com.robosoft.rxjavaexample.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Rahul Kumar Pandey on 17-05-2017.
 */

public class ColorsActivity extends AppCompatActivity {

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        createObservable();
    }

    private void initView() {
        setContentView(R.layout.activity_colors);
        RecyclerView colorListView = (RecyclerView) findViewById(R.id.color_list);
        colorListView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        colorListView.setAdapter(mRecyclerViewAdapter);
    }

    private void createObservable() {
        Observable<List<String>> listObservable = Observable.just(getColorList());
        mDisposable = listObservable.subscribe(colors -> mRecyclerViewAdapter.setStrings(colors));
    }

    private static List<String> getColorList() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("pink");
        colors.add("brown");
        return colors;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
