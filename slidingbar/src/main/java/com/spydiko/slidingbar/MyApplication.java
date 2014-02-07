package com.spydiko.slidingbar;

import android.app.Activity;
import android.app.Application;

/**
 * Created by jim on 7/2/2014.
 */
public class MyApplication extends Application {

	private Activity useActivity;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void setUseActivity(Activity useActivity) {
		this.useActivity = useActivity;
	}

	public Activity getUseActivity() {
		return useActivity;
	}
}
