package com.ldroid.kwei.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.ui.BaseActivity;
import com.ldroid.kwei.module.login.LoginActivity;

public class StartActivity extends BaseActivity {


    @Override
    protected void initPreparation() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.ac_start);
        startAnimActivity(LoginActivity.class);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
