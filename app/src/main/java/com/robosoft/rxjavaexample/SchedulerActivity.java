package com.robosoft.rxjavaexample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.concurrent.Callable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul Kumar Pandey on 17-05-2017.
 */

public class SchedulerActivity extends AppCompatActivity {

    private Disposable subscription;
    private ProgressBar mProgressBar;
    private TextView mMessageTxt;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        initView();
        createObservable();
    }

    private void createObservable() {
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageTxt = (TextView) findViewById(R.id.messagearea);
        mButton = (Button) findViewById(R.id.scheduleLongRunningOperation);
        mButton.setOnClickListener(v -> {
//                mProgressBar.setVisibility(View.VISIBLE);
            Observable.fromCallable(callable).
                    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    doOnSubscribe(disposable ->
                            {
                                mProgressBar.setVisibility(View.VISIBLE);
                                mButton.setEnabled(false);
                                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "Progressbar set visible");
                            }
                    ).
                    subscribe(getDisposableObserver());
        });
    }

    Callable<String> callable = this::doSomethingLong; // method reference for lambda expression

    public String doSomethingLong() {
        SystemClock.sleep(1000);
        return "Hello";
    }

    /**
     * Observer
     * Handles the stream of data:
     */
    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "OnComplete");
                mProgressBar.setVisibility(View.INVISIBLE);
                mButton.setEnabled(true);
                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "Hidding Progressbar");
            }

            @Override
            public void onError(Throwable e) {
                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "OnError");
                mProgressBar.setVisibility(View.INVISIBLE);
                mButton.setEnabled(true);
                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "Hidding Progressbar");
            }

            @Override
            public void onNext(String message) {
                mMessageTxt.setText(mMessageTxt.getText().toString() + "\n" + "onNext " + message);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
