package com.zf.utils;

import android.app.Application;

import org.xutils.x;

public class XutilApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
	}
}
