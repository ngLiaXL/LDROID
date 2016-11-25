package com.ldroid.kwei.common.ui.lib.switchbutton;
//package com.kyleduo.switchbutton;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.Toast;
//
//import com.kyleduo.switchbutton.Configuration;
//import com.kyleduo.switchbutton.SwitchButton;
///*
// * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:tools="http://schemas.android.com/tools"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:background="#eeeeee"
//    android:orientation="vertical"
//    android:paddingBottom="@dimen/activity_vertical_margin"
//    android:paddingLeft="@dimen/activity_horizontal_margin"
//    android:paddingRight="@dimen/activity_horizontal_margin"
//    android:paddingTop="@dimen/activity_vertical_margin"
//    tools:context="com.kyleduo.switchbuttondemo.StyleActivity" >
//
//    <TextView
//        style="@style/StyleTitleText"
//        android:text="@string/style_default" />
//
//    <com.kyleduo.switchbutton.SwitchButton
//        android:id="@+id/sb_default"
//        style="@style/SwitchButtonStyle" />
//
//    <TextView
//        style="@style/StyleTitleText"
//        android:text="@string/style_md" />
//
//    <com.kyleduo.switchbutton.SwitchButton
//        android:id="@+id/sb_md"
//        style="@style/MaterialDesignStyle" />
//
//    <TextView
//        style="@style/StyleTitleText"
//        android:text="@string/style_ios" />
//
//    <com.kyleduo.switchbutton.SwitchButton
//        android:id="@+id/sb_ios"
//        style="@style/SwitchButtonStyle"
//        app:animationVelocity="8"
//        app:measureFactor="1.5"
//        app:offDrawable="@drawable/ios_off"
//        app:onColor="#43d95d"
//        app:thumbDrawable="@drawable/ios_thumb"
//        app:thumb_marginBottom="-8dp"
//        app:thumb_marginLeft="-5dp"
//        app:thumb_marginRight="-5dp"
//        app:thumb_marginTop="-2.5dp" />
//
//    <TextView
//        style="@style/StyleTitleText"
//        android:text="@string/style_change" />
//
//    <LinearLayout
//        android:id="@+id/incode_container"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:gravity="center_vertical"
//        android:orientation="horizontal" >
//
//        <com.kyleduo.switchbutton.SwitchButton
//            android:id="@+id/sb_changeface_control"
//            style="@style/SwitchButtonStyle" />
//    </LinearLayout>
//
//    <TextView
//        style="@style/StyleTitleText"
//        android:text="@string/enable_title" />
//
//    <LinearLayout
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:gravity="center_vertical"
//        android:orientation="horizontal" >
//
//        <com.kyleduo.switchbutton.SwitchButton
//            android:id="@+id/sb_enable"
//            style="@style/SwitchButtonStyle"
//            app:animationVelocity="8"
//            app:offDrawable="@drawable/img_back_off"
//            app:onDrawable="@drawable/img_back_on"
//            app:thumbDrawable="@drawable/selector_thumb"
//            app:thumb_marginBottom="2dp"
//            app:thumb_marginLeft="3dp"
//            app:thumb_marginRight="3dp"
//            app:thumb_marginTop="3dp" />
//    </LinearLayout>
//
//</LinearLayout>
// */
//public class StyleActivity extends Activity {
//
//	private SwitchButton sbDefault, sbChangeFaceControl, sbIOS, sbInCode, sbEnable, sbMd;
//	private SwitchButton[] sbs = new SwitchButton[5];
//	private ViewGroup container;
//	private boolean newConf = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_style);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//
//		container = (ViewGroup) findViewById(R.id.incode_container);
//
//		sbDefault = (SwitchButton) findViewById(R.id.sb_default);
//		sbIOS = (SwitchButton) findViewById(R.id.sb_ios);
//		sbChangeFaceControl = (SwitchButton) findViewById(R.id.sb_changeface_control);
//		sbEnable = (SwitchButton) findViewById(R.id.sb_enable);
//		sbMd = (SwitchButton) findViewById(R.id.sb_md);
//
//		sbInCode = new SwitchButton(this);
//		container.addView(sbInCode, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//		sbs[0] = sbDefault;
//		sbs[1] = sbIOS;
//		sbs[2] = sbChangeFaceControl;
//		sbs[3] = sbInCode;
//		sbs[4] = sbMd;
//
//		sbDefault.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Toast.makeText(StyleActivity.this, "Default style button, new state: " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		sbChangeFaceControl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if (!newConf) {
//					Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
//					conf.setThumbMargin(2);
//					conf.setVelocity(8);
//					conf.setThumbWidthAndHeight(24, 14);
//					conf.setRadius(6);
//					conf.setMeasureFactor(2f);
//					sbInCode.setConfiguration(conf);
//				} else {
//					Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
//					sbInCode.setConfiguration(conf);
//				}
//				newConf = isChecked;
//			}
//		});
//
//		sbEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				for (SwitchButton sb : sbs) {
//					sb.setEnabled(isChecked);
//				}
//			}
//		});
//
//		sbEnable.setChecked(true);
//	}
//
//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		if (item.getItemId() == android.R.id.home) {
//			finish();
//			return true;
//		}
//		return super.onMenuItemSelected(featureId, item);
//	}
//}
