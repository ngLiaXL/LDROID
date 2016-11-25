/**
 * 
 */
package com.ldroid.kwei.interactor;

import com.ldroid.kwei.common.entities.OutputDataEntity;
import com.ldroid.kwei.common.lib.gson.reflect.TypeToken;
import com.ldroid.kwei.common.mvp.BaseInteractor;
import com.ldroid.kwei.common.net.ActionConstants;
import com.ldroid.kwei.common.net.ResponseListener;
import com.ldroid.kwei.entities.in.LoginInEntity;
import com.ldroid.kwei.entities.out.LoginOutEntity;

public class LoginInteractor extends BaseInteractor {

	private static final String TAG = "LoginInteractor";

	
	public void reqLogin(LoginInEntity in,
			ResponseListener<OutputDataEntity<LoginOutEntity>> listener) {
		in.setMethod(ActionConstants.ACTION_LOGIN);
		performRequest(in, listener, new TypeToken<OutputDataEntity<LoginOutEntity>>() {
		}.getType(), TAG);
		
	}
	
}
