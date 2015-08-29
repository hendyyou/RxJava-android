package com.strv.rxjavademo.fragment;

import android.support.v4.app.Fragment;

import com.strv.rxjavademo.RxJavaDemoApplication;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by adamcerny on 28/08/15.
 */
public class BaseFragment extends Fragment
{
	protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		RxJavaDemoApplication.getRefWatcher(getActivity()).watch(this);
		mCompositeSubscription.unsubscribe();
		ButterKnife.unbind(this);
	}
}
