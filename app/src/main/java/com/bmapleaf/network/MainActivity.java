package com.bmapleaf.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IMyView {
    private MyPresenter myPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bmapleaf.network.R.layout.activity_main);
        myPresenter = new MyPresenter(this, this);

        myPresenter.testApi2("var1", "var2");
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequest() {
        //show progress
    }

    @Override
    public void onCompleted() {
        //hide progress
    }

    @Override
    public void testOK() {
        myPresenter.download("url");
    }
}
