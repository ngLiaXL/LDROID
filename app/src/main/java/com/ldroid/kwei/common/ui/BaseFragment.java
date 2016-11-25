/**
 * 
 */
package com.ldroid.kwei.common.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.lib.eventbus.EventBus;
import com.ldroid.kwei.common.util.TimeUtils;
import com.ldroid.kwei.common.util.ToastUtils;
import com.ldroid.kwei.dao.ConfigDao;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	public ConfigDao mConfigDao = ConfigDao.getInstance();
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPresenter();
		initUI();
		initListener();
		initData();
	}

	protected abstract void initPresenter();

	protected abstract void initUI();

	protected abstract void initListener();

	protected abstract void initData();

	public void showDateTimeDialog(final TextView textView) {
		TimeUtils.getDateDialog(getActivity(), new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				updateTime(year, monthOfYear, dayOfMonth, textView);
			}

			private void updateTime(int year, int monthOfYear, int dayOfMonth, TextView textview) {
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				textview.setText(df.format(calendar.getTime()));
			}
		}).show();
	}

	public void register(Object subscriber) {
		if (!EventBus.getDefault().isRegistered(subscriber)) {
			EventBus.getDefault().register(subscriber);
		}
	}

	public void post(Object... event) {
		for (Object e : event) {
			EventBus.getDefault().post(e);
		}
	}

	public void unregister(Object subscriber) {
		if (EventBus.getDefault().isRegistered(subscriber)) {
			EventBus.getDefault().unregister(subscriber);
		}
	}

	/**
	 * 
	 */
	public BaseFragment() {
	}

	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}

	/*
	 * 
	 * titlebar 分类
	 * 
	 * 1、标题 2、左返回/左图片 + 标题 3、标题 + 右文字/右图片 4、左返回/左图片 + 标题 + 右文字/右图片
	 */
	/**
	 * 1、只有标题
	 * 
	 * @Title: initTopBarLayoutByTitle
	 * @throws
	 */
	public void initTopBarForOnlyTitle(String titleName) {
		TextView title = (TextView) findViewById(R.id.tv_main_title);
		title.setText(titleName);
		findViewById(R.id.rl_title_bar_left).setVisibility(View.GONE);
		findViewById(R.id.rl_title_bar_right).setVisibility(View.GONE);
	}

	/**
	 * 2、左返回/左图片/左文字 + 标题
	 * 
	 * @param titleName
	 * @param leftName
	 * @param leftDrawable
	 */
	public void initTopBarForLeft(String titleName, String leftName, Drawable leftDrawable) {
		TextView title = (TextView) findViewById(R.id.tv_main_title);
		title.setText(titleName);

		TextView tVLeft = (TextView) findViewById(R.id.tv_title_bar_left);
		tVLeft.setVisibility(leftDrawable == null ? View.VISIBLE : View.GONE);
		tVLeft.setText(leftName);

		ImageView iVLeft = (ImageView) findViewById(R.id.iv_title_bar_left);
		iVLeft.setVisibility(leftDrawable != null ? View.VISIBLE : View.GONE);
		if (leftDrawable != null)
			iVLeft.setBackgroundDrawable(leftDrawable);

		findViewById(R.id.rl_title_bar_left).setVisibility(View.VISIBLE);
		findViewById(R.id.rl_title_bar_left).setOnClickListener(this);
		findViewById(R.id.rl_title_bar_right).setVisibility(View.GONE);
	}

	/**
	 * 3、标题 + 右文字/右图片
	 * 
	 * @param titleName
	 * @param rightName
	 * @param rdrawable
	 */
	public void initTopBarForRight(String titleName, String rightName, Drawable rdrawable) {
		TextView title = (TextView) findViewById(R.id.tv_main_title);
		title.setText(titleName);

		TextView tVRight = (TextView) findViewById(R.id.tv_title_bar_right);
		tVRight.setVisibility(rdrawable == null ? View.VISIBLE : View.GONE);
		tVRight.setText(rightName);

		ImageView iVRight = (ImageView) findViewById(R.id.iv_title_bar_right);
		iVRight.setVisibility(rdrawable != null ? View.VISIBLE : View.GONE);
		if (rdrawable != null)
			iVRight.setBackgroundDrawable(rdrawable);

		findViewById(R.id.rl_title_bar_left).setVisibility(View.GONE);
		findViewById(R.id.rl_title_bar_right).setVisibility(View.VISIBLE);
		findViewById(R.id.rl_title_bar_right).setOnClickListener(this);

	}

	/**
	 * 4、左返回/左图片 + 标题 + 右文字/右图片
	 * 
	 * @param titleName
	 * @param leftName
	 * @param leftDrawable
	 * @param rightName
	 * @param rightDrawable
	 */
	public void initTopBarForBoth(String titleName, String leftName, Drawable leftDrawable,
			String rightName, Drawable rightDrawable) {
		TextView title = (TextView) findViewById(R.id.tv_main_title);
		title.setText(titleName);

		TextView tVLeft = (TextView) findViewById(R.id.tv_title_bar_left);
		tVLeft.setVisibility(leftDrawable == null ? View.VISIBLE : View.GONE);
		tVLeft.setText(leftName);

		ImageView iVLeft = (ImageView) findViewById(R.id.iv_title_bar_left);
		iVLeft.setVisibility(leftDrawable != null ? View.VISIBLE : View.GONE);
		if (leftDrawable != null)
			iVLeft.setBackgroundDrawable(leftDrawable);

		TextView tVRight = (TextView) findViewById(R.id.tv_title_bar_right);
		tVRight.setVisibility(rightDrawable == null ? View.VISIBLE : View.GONE);
		tVRight.setText(rightName);

		ImageView iVRight = (ImageView) findViewById(R.id.iv_title_bar_right);
		iVRight.setVisibility(rightDrawable != null ? View.VISIBLE : View.GONE);
		if (rightDrawable != null)
			iVRight.setBackgroundDrawable(rightDrawable);

		findViewById(R.id.rl_title_bar_left).setVisibility(View.VISIBLE);
		findViewById(R.id.rl_title_bar_right).setVisibility(View.VISIBLE);
		findViewById(R.id.rl_title_bar_left).setOnClickListener(this);
		findViewById(R.id.rl_title_bar_right).setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_title_bar_left:
			getActivity().finish();
			break;
		}
	}

	public void startAnimActivity(Intent intent) {
		startActivity(intent);
	}

	public void startAnimActivity(Class<?> cla) {
		startActivity(new Intent(getActivity(), cla));
	}

	public void showToast(String s) {
		ToastUtils.showLongToast(getActivity(), s);
	}

}
