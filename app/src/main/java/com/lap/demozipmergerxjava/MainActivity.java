package com.lap.demozipmergerxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func3;
import rx.functions.FuncN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnZip;
    private Button btnMerge;
    private Button btnZipList;
    private Button btnMergeList;
    private TextView txtZip;
    private TextView txtMerge;
    private TextView txtZipList;
    private TextView txtMergeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnZip = (Button) findViewById(R.id.btn_zip);
        btnMerge = (Button) findViewById(R.id.btn_merge);
        btnZipList = (Button) findViewById(R.id.btn_zip_list);
        btnMergeList = (Button) findViewById(R.id.btn_merge_list);

        btnMerge.setOnClickListener(this);
        btnZip.setOnClickListener(this);
        btnMergeList.setOnClickListener(this);
        btnZipList.setOnClickListener(this);

        txtMerge = (TextView) findViewById(R.id.txt_merge);
        txtZip = (TextView) findViewById(R.id.txt_zip);
        txtMergeList = (TextView) findViewById(R.id.txt_merge_list);
        txtZipList = (TextView) findViewById(R.id.txt_zip_list);
    }

    private void zip() {
        Observable.zip(createObservable(1), createObservable(2), createObservable(3),
                new Func3<Integer, Integer, Integer, DataZip>() {
                    @Override
                    public DataZip call(Integer integer, Integer integer2, Integer integer3) {
                        return new DataZip(integer, integer2, integer3);
                    }
                }).subscribe(new Subscriber<DataZip>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DataZip zip) {
                txtZip.setText(zip.numberOne + " " + zip.numberTwo + " " + zip.numberThree);
            }
        });
    }

    private void zipList() {
        Observable.zip(createListObservable(), new FuncN<DataZip>() {
            @Override
            public DataZip call(Object... args) {
                return new DataZip((int) args[0], (int) args[1], (int) args[2]);
            }
        }).subscribe(new Subscriber<DataZip>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(DataZip zip) {
                txtZipList.setText(zip.numberOne + " " + zip.numberTwo + " " + zip.numberThree);
            }
        });
    }

    private void merge() {
        Observable.merge(createObservable(1), createObservable(2), createObservable(3))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("MainActivity", " -> onNext: ----------------: " + integer);
                        txtMerge.setText(txtMerge.getText() + " " + integer);
                    }
                });
    }

    private void mergeList() {
        Observable.merge(createListObservable()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                txtMergeList.setText(txtMergeList.getText() + " " +  o);
            }
        });
    }

    private Observable<Integer> createObservable(int data) {
        return Observable.just(data);
    }

    private List<Observable<?>> createListObservable() {
        List<Observable<?>> result = new ArrayList<>();
        result.add(createObservable(1));
        result.add(createObservable(2));
        result.add(createObservable(3));
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zip:
                zip();
                break;
            case R.id.btn_merge:
                merge();
                break;
            case R.id.btn_zip_list:
                zipList();
                break;
            case R.id.btn_merge_list:
                mergeList();
                break;
        }
    }

    class DataZip {
        int numberOne;
        int numberTwo;
        int numberThree;

        DataZip(int numberOne, int numberTwo, int numberThree) {
            this.numberOne = numberOne;
            this.numberTwo = numberTwo;
            this.numberThree = numberThree;
        }
    }
}
