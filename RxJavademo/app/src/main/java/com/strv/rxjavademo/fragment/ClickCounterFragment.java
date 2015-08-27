package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.strv.rxjavademo.R;
import com.strv.rxjavademo.RxJavaDemoApplication;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by adamcerny on 24/08/15.
 */
public class ClickCounterFragment extends BaseFragment
{
	private Observable<OnClickEvent> mButtonLambdaObservable;
	private Observable<OnClickEvent> mButtonRxAndroidObservable;

	private Subscription mButtonLambdaSubscription;
	private Subscription mButtonRxAndroidSubscription;

	@Bind(R.id.fragment_click_counter_button_lambda)
	Button mLambdaCounterButton;

	@Bind(R.id.fragment_click_counter_button_rxandroid)
	Button mRxAndroidCounterButton;


	public static ClickCounterFragment newInstance()
	{
		ClickCounterFragment fragment = new ClickCounterFragment();
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
		View layout = inflater.inflate(R.layout.fragment_click_counter, container, false);
		ButterKnife.bind(this, layout);
		renderView();
		return layout;
	}


	private void renderView()
	{
		//****************************************
		//RxAndroid + Lambda approach
		//****************************************
		mButtonLambdaObservable = ViewObservable.clicks(mLambdaCounterButton, false);

		mButtonLambdaSubscription = mButtonLambdaObservable.map(clickEvent ->
		{
			return 1;
		})
		.buffer(3, TimeUnit.SECONDS)
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(integersList ->
		{
			int counter = integersList.size();
			if(counter > 0)
			{
				Toast.makeText(getActivity(), "Clicked " + counter + " times", Toast.LENGTH_SHORT).show();
			}
		});

		mCompositeSubscription.add(mButtonLambdaSubscription);


		//****************************************
		//RxAndroid approach
		//****************************************
		mButtonRxAndroidObservable = ViewObservable.clicks(mRxAndroidCounterButton, false);

		mButtonRxAndroidSubscription = mButtonRxAndroidObservable.map(new Func1<OnClickEvent, Integer>()
		{
			@Override
			public Integer call(OnClickEvent onClickEvent)
			{
				return 1;
			}

		})
		.buffer(3, TimeUnit.SECONDS)
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(new Action1<List<Integer>>()
		{
			@Override
			public void call(List<Integer> integers)
			{
				int counter = integers.size();

				if(counter > 0)
				{
					Toast.makeText(getActivity(), "Clicked " + counter + " times", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mCompositeSubscription.add(mButtonRxAndroidSubscription);
	}
}
