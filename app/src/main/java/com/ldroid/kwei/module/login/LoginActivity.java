package com.ldroid.kwei.module.login;

import android.content.Context;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.ui.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View {


	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void showLoading(String msg) {

	}

	@Override
	public void dismissLoading() {

	}

	@Override
	public void onError(String msg) {

	}

	@Override
	protected void initPreparation() {

	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.ac_login);
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

	@Override
	public void onRespLogin() {

	}
}
