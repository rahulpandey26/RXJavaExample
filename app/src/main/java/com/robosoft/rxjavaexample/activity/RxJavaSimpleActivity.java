package com.robosoft.rxjavaexample.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.robosoft.rxjavaexample.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaSimpleActivity extends AppCompatActivity {

    private CompositeDisposable disposable = new CompositeDisposable();
    private int value = 0;

    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        SystemClock.sleep(10000); // simulate delay
        emitter.onNext(5);
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavasimple);
        findViewById(R.id.serverbutton).setOnClickListener(v -> {
            v.setEnabled(false); // disables the button until execution has finished
            Disposable subscribe = serverDownloadObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(integer -> {
                        updateTheUserInterface(integer); // this methods updates the ui
                        v.setEnabled(true); // enables it again
                    });
            disposable.add(subscribe);
        });

        findViewById(R.id.toastbutton).setOnClickListener(view1 -> {
            Toast.makeText(this, "Still active " + value++, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateTheUserInterface(int integer) {
        TextView textView = (TextView) findViewById(R.id.resultView);
        textView.setText(String.valueOf(integer));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
