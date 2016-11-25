package com.ldroid.kwei.module.login;

import android.support.annotation.NonNull;

import com.ldroid.kwei.common.mvp.BasePresenter;
import com.ldroid.kwei.common.mvp.BaseView;

public interface LoginContract {

	interface View extends BaseView {
		void onRespLogin();
	}

	interface Presenter extends BasePresenter {

		void reqLogin(@NonNull String phone, @NonNull String pwd);

	}
}
