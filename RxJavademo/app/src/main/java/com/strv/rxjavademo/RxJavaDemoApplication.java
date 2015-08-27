package com.strv.rxjavademo;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by adamcerny on 27/08/15.
 */
public class RxJavaDemoApplication extends Application
{
	private RefWatcher mRefWatcher;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mRefWatcher = LeakCanary.install(this);
	}


	public static RefWatcher getRefWatcher(Context context)
	{
		RxJavaDemoApplication application = (RxJavaDemoApplication) context.getApplicationContext();
		return application.mRefWatcher;
	}
}
