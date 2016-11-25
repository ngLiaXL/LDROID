/**
 *
 */
package com.ldroid.kwei.common.ui;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.lib.eventbus.EventBus;
import com.ldroid.kwei.common.mvp.BasePresenter;
import com.ldroid.kwei.common.ui.lib.appmsg.AppMsg;
import com.ldroid.kwei.common.ui.lib.sweetalert.SweetAlertDialog;
import com.ldroid.kwei.common.util.TimeUtils;
import com.ldroid.kwei.dao.ConfigDao;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {


    public Context mContext;
    public ConfigDao mConfigDao;
    public SweetAlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigDao = ConfigDao.getInstance();
        mContext = this;
        initPreparation();
        setContentView();
        initUI();
        initListener();
        initData();
    }

    protected abstract void initPreparation();

    protected abstract void setContentView();

    protected abstract void initUI();

    protected abstract void initListener();

    protected abstract void initData();


    public void showAlertMsg(String msg) {
        AppMsg appMsg = AppMsg.makeText(this, msg, AppMsg.STYLE_ALERT);
        appMsg.setAnimation(R.anim.slide_in_from_top, R.anim.slide_out_to_top);
        appMsg.show();
    }

    public void showDateTimeDialog(final TextView textView) {
        TimeUtils.getDateDialog(mContext, new OnDateSetListener() {
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


    public boolean checkLogin() {
        return mConfigDao.getMemberId() >= 0;
    }


    public Drawable getResourceDrawable(int id) {
        return getResources().getDrawable(id);
    }

    public void startAnimActivity(Intent intent) {
        startActivity(intent);
    }

    public void startAnimActivity(Class<?> cla) {
        startActivity(new Intent(this, cla));
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

    public void showLoadingDialog(String tips) {
        if (mProgressDialog == null) {
            mProgressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mProgressDialog.getProgressHelper().setBarColor(Color.parseColor("#3db394"));
        }
        mProgressDialog.setTitleText(tips);
        mProgressDialog.show();


    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    public void showAlertDialog(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(TextUtils.isEmpty(msg) ? "网络或服务器异常" : msg).setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).setOnTouchOutsideCanceled(true).show();
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
     * @throws
     * @Title: initTopBarLayoutByTitle
     */
    public void initTopBarForOnlyTitle(String titleName) {
        TextView title = (TextView) findViewById(R.id.tv_main_title);
        title.setText(titleName);
        findViewById(R.id.rl_title_bar_left).setVisibility(View.INVISIBLE);
        findViewById(R.id.rl_title_bar_right).setVisibility(View.INVISIBLE);
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
        tVLeft.setVisibility(leftDrawable == null ? View.VISIBLE : View.INVISIBLE);
        tVLeft.setText(leftName);

        ImageView iVLeft = (ImageView) findViewById(R.id.iv_title_bar_left);
        iVLeft.setVisibility(leftDrawable != null ? View.VISIBLE : View.INVISIBLE);
        if (leftDrawable != null)
            iVLeft.setBackgroundDrawable(leftDrawable);

        findViewById(R.id.rl_title_bar_left).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_title_bar_left).setOnClickListener(this);
        findViewById(R.id.rl_title_bar_right).setVisibility(View.INVISIBLE);
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
        tVRight.setVisibility(rdrawable == null ? View.VISIBLE : View.INVISIBLE);
        tVRight.setText(rightName);

        ImageView iVRight = (ImageView) findViewById(R.id.iv_title_bar_right);
        iVRight.setVisibility(rdrawable != null ? View.VISIBLE : View.INVISIBLE);
        if (rdrawable != null)
            iVRight.setBackgroundDrawable(rdrawable);

        findViewById(R.id.rl_title_bar_left).setVisibility(View.INVISIBLE);
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
        tVLeft.setVisibility(leftDrawable == null ? View.VISIBLE : View.INVISIBLE);
        tVLeft.setText(leftName);

        ImageView iVLeft = (ImageView) findViewById(R.id.iv_title_bar_left);
        iVLeft.setVisibility(leftDrawable != null ? View.VISIBLE : View.INVISIBLE);
        if (leftDrawable != null)
            iVLeft.setBackgroundDrawable(leftDrawable);

        TextView tVRight = (TextView) findViewById(R.id.tv_title_bar_right);
        tVRight.setVisibility(rightDrawable == null ? View.VISIBLE : View.INVISIBLE);
        tVRight.setText(rightName);

        ImageView iVRight = (ImageView) findViewById(R.id.iv_title_bar_right);
        iVRight.setVisibility(rightDrawable != null ? View.VISIBLE : View.INVISIBLE);
        if (rightDrawable != null)
            iVRight.setBackgroundDrawable(rightDrawable);

        findViewById(R.id.rl_title_bar_left).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_title_bar_right).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_title_bar_left).setOnClickListener(this);
        findViewById(R.id.rl_title_bar_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_title_bar_left:
                finish();
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypeInstance(Object o, int i) {
        try {

            ParameterizedType genericSuperclass = (ParameterizedType) o.getClass().getGenericSuperclass();
            Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
            Class<T> clazz = (Class<T>) actualTypeArguments[i];
            clazz.newInstance();


            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <P extends BasePresenter> P createPresenter(Object childClass, int typeIndex) {
        try {

            ParameterizedType genericSuperclass = (ParameterizedType) childClass.getClass()
                    .getGenericSuperclass();
            Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();

            Class<P> clazz = (Class<P>) actualTypeArguments[typeIndex];
            clazz.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

}
