package com.strv.rxjavademo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.strv.rxjavademo.cache.RequestCache;
import com.strv.rxjavademo.fragment.CoreFragment;


public class CoreActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction()
					.replace(android.R.id.content, CoreFragment.newInstance(), this.toString())
					.commit();
		}
	}


	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		RequestCache.getInstance().deleteCache();
	}
}
