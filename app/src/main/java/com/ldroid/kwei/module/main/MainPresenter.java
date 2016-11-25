/**
 * 
 */
package com.ldroid.kwei.module.main;

import android.support.annotation.NonNull;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.entities.OutputDataEntity;
import com.ldroid.kwei.common.lib.volley.VolleyError;
import com.ldroid.kwei.common.net.ResponseListener;
import com.ldroid.kwei.entities.in.LoginInEntity;
import com.ldroid.kwei.entities.out.LoginOutEntity;
import com.ldroid.kwei.interactor.LoginInteractor;

public class MainPresenter implements MainContract.Presenter {

	private MainContract.View mView;
	private LoginInteractor mInteractor;

	/**
	 * 
	 */
	public MainPresenter(@NonNull MainContract.View view) {
		this.mView = view;
		mInteractor = new LoginInteractor();
	}

	@Override
	public void reqLogin(@NonNull String utel, @NonNull String upwd) {
		final LoginInEntity in = new LoginInEntity(utel, upwd);
		if (!in.checkInput()) {
			mView.onError(in.getErrors().get(0));
			return;
		}
		mView.showLoading(null);
		mInteractor.reqLogin(in, new ResponseListener<OutputDataEntity<LoginOutEntity>>() {

			@Override
			public void onResponse(OutputDataEntity<LoginOutEntity> response) {
				mView.dismissLoading();
				if (response == null) {
					mView.onError(mView.getContext().getString(R.string.common_net_error));
				} else {
					if (Code.SUCCESS.equals(response.code)) {
						mView.onRespLogin();
					} else {
						mView.onError(response.getErrorMsg());
					}
				}
			}

			@Override
			public void onErrorResponse(VolleyError error) {
				mView.dismissLoading();
				mView.onError(error.toString());
			}
		});

	}

}
