package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.strv.rxjavademo.Event.FirebaseDataEvent;
import com.strv.rxjavademo.R;
import com.strv.rxjavademo.bus.RxCustomBus;
import com.strv.rxjavademo.service.FirebaseService;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by adamcerny on 24/08/15.
 */
public class FirebaseFragment extends BaseFragment
{
	@Bind(R.id.fragment_firebase_lambda_example_textview)
	TextView mLambdaTextView;

	@Bind(R.id.fragment_firebase_rxandroid_example_textview)
	TextView mRxAndroidTextView;

	@Bind(R.id.fragment_firebase_example_progress)
	ProgressBar mProgressBar;

	public static FirebaseFragment newInstance()
	{
		FirebaseFragment fragment = new FirebaseFragment();
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(getArguments() != null)
		{

		}
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		renderView();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_firebase, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	@OnClick(R.id.fragment_firebase_button_lambda)
	public void clickFirebaseGet()
	{
		mProgressBar.setVisibility(View.VISIBLE);
		FirebaseService.getInstance().getData();
	}


	private void renderView()
	{

		//****************************************
		//RxAndroid + Lambda approach
		//****************************************
		mCompositeSubscription.add(
			RxCustomBus.getInstance().observe(FirebaseDataEvent.class)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(event ->
					{
						mLambdaTextView.setText(event.getFirebaseDataString());
						mProgressBar.setVisibility(View.GONE);
					},
					error ->
					{
						mProgressBar.setVisibility(View.GONE);
					},
					() -> {}
				)
		);

		//****************************************
		//RxAndroid approach
		//****************************************
		mCompositeSubscription.add(
				RxCustomBus.getInstance().observe(FirebaseDataEvent.class)
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber<FirebaseDataEvent>()
								   {
									   @Override
									   public void onCompleted()
									   {

									   }


									   @Override
									   public void onError(Throwable e)
									   {
										   mProgressBar.setVisibility(View.GONE);
									   }


									   @Override
									   public void onNext(FirebaseDataEvent event)
									   {
											   mRxAndroidTextView.setText(event.getFirebaseDataString());
											   mProgressBar.setVisibility(View.GONE);
									   }
								   }
						)
		);
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();
		FirebaseService.getInstance().unregisterDataObserver();
	}
}
