package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.strv.rxjavademo.Event.FirebaseDataEvent;
import com.strv.rxjavademo.R;
import com.strv.rxjavademo.bus.RxCustomBus;
import com.strv.rxjavademo.service.FirebaseService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;


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
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_firebase, container, false);
		ButterKnife.bind(this, layout);
		renderView();
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
			RxCustomBus.getInstance().observe()
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(event ->
					{
						if(event instanceof FirebaseDataEvent)
						{
							mLambdaTextView.setText(((FirebaseDataEvent) event).getFirebaseDataString());
							mProgressBar.setVisibility(View.GONE);
						}
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
				RxCustomBus.getInstance().observe()
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber<Object>()
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
									   public void onNext(Object o)
									   {
										   if(o instanceof FirebaseDataEvent)
										   {
											   mRxAndroidTextView.setText(((FirebaseDataEvent) o).getFirebaseDataString());
											   mProgressBar.setVisibility(View.GONE);
										   }
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
