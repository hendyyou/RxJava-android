package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strv.rxjavademo.Event.ApiCallEvent;
import com.strv.rxjavademo.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.functions.Func3;


/**
 * Created by adamcerny on 24/08/15.
 */
public class OperatorFragment extends BaseFragment
{
	private String[] mApiCallCodes =
			{
				"{\"api_call_response\": 200}",
			 	"{\"api_call_response\": 500}",
				"{\"api_call_response\": 200}",
				"{\"api_call_response\": 404}",
				"{\"api_call_response\": 400}"
			};

	private String[] mApiCallContent =
			{
				"{\"call_content\": \"Rx is like a god! \"}",
				"{\"call_content\": \"LISP, really? \"}",
				"{\"call_content\": \"Cpp - nice \"}",
				"{\"call_content\": \"C& - oh \"}",
				"{\"call_content\": \"need language, man! \"}"
			};


	@Bind(R.id.fragment_operator_test_1_textview)
	TextView mTest1TextView;


	@OnClick(R.id.fragment_operator_button_lambda)
	public void clickLambdaButton()
	{
		mTest1TextView.setText("");
		runLambdaTest();
	}


	@OnClick(R.id.fragment_operator_button_rxandroid)
	public void clickRxandroidButton()
	{
		mTest1TextView.setText("");
		runRxandroidTest();
	}
	

	public static OperatorFragment newInstance()
	{
		OperatorFragment fragment = new OperatorFragment();
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_operator, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	private void runLambdaTest()
	{
		//Create clock - i.e. how often we want to update UI
		Observable<Long> sampleObservable = Observable.interval(500, TimeUnit.MILLISECONDS);

		//Simulate response from API calls - we got 5 events/responses at once
		Observable<String> apiCallCodeObservable = Observable.from(mApiCallCodes);

		//Simulate response from API calls - we got 5 events/responses
		//but also simulate that these API calls are delayed by 1 second
		//from each other
		Observable<Long> clock = Observable.interval(1000, TimeUnit.MILLISECONDS);
		Observable<String> apiCallContentObservable = Observable.from(mApiCallContent)
				.zipWith(clock, (content, time) ->
				{
					return content;
				});

		mCompositeSubscription.add(
			//Show how we can synchronize API calls with different completion time
			Observable.zip(sampleObservable, apiCallCodeObservable, apiCallContentObservable, (a, b, c) ->
				{
					ApiCallEvent event = new ApiCallEvent();
					event.setSample(a.intValue());
					event.setCode(b);
					event.setContent(c);
					return event;
				})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(item ->
			{
				mTest1TextView.setText(item.getSample() + " " + item.getCode() + " " + item.getContent() + "\n" + mTest1TextView.getText());
			})
		);
	}

	private void runRxandroidTest()
	{
		//Create clock - i.e. how often we want to update UI
		Observable<Long> sampleObservable = Observable.interval(500, TimeUnit.MILLISECONDS);

		//Simulate response from API calls - we got 5 events/responses at once
		Observable<String> apiCallCodeObservable = Observable.from(mApiCallCodes);

		//Simulate response from API calls - we got 5 events/responses
		//but also simulate that these API calls are delayed by 1 second
		//from each other
		Observable<Long> clock = Observable.interval(1000, TimeUnit.MILLISECONDS);
		Observable<String> apiCallContentObservable = Observable.from(mApiCallContent)
				.zipWith(clock, new Func2<String, Long, String>()
				{
					@Override
					public String call(String s, Long aLong)
					{
						return s;
					}
				});

		mCompositeSubscription.add(
			//Show how we can synchronize API calls with different completion time
			Observable.zip(sampleObservable, apiCallCodeObservable, apiCallContentObservable, new Func3<Long, String, String, ApiCallEvent>()
			{
				@Override
				public ApiCallEvent call(Long aLong, String s, String s2)
				{
					ApiCallEvent event = new ApiCallEvent();
					event.setSample(aLong.intValue());
					event.setCode(s);
					event.setContent(s2);
					return event;
				}
			})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Action1<ApiCallEvent>()
			{
				@Override
				public void call(ApiCallEvent apiCallEvent)
				{
					mTest1TextView.setText(apiCallEvent.getSample() + " " + apiCallEvent.getCode() + " " + apiCallEvent.getContent() + "\n" + mTest1TextView.getText());
				}
			})
		);
	}
}
