package com.robosoft.rxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul Kumar Pandey on 17-05-2017.
 */

public class BooksActivity extends AppCompatActivity {
    private Disposable mDisposable;
    private RecyclerView mBooksRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private RestClient mRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        mRestClient = new RestClient(this);
        initView();
        createObservable();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.loader);
        mBooksRecyclerView = (RecyclerView) findViewById(R.id.books_list);
        mBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        mBooksRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void createObservable() {
        Observable<List<String>> booksObservable =
                Observable.fromCallable(() -> mRestClient.getFavoriteBooks());
        mDisposable = booksObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(strings -> displayBooks(strings));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void displayBooks(List<String> books) {
        mRecyclerViewAdapter.setStrings(books);
        mProgressBar.setVisibility(View.GONE);
        mBooksRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable !=null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
