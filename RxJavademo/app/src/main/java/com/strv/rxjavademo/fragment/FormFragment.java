package com.strv.rxjavademo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.strv.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnCheckedChangeEvent;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * Created by adamcerny on 24/08/15.
 */
public class FormFragment extends Fragment
{
	private Observable<Boolean> mEdittextLambdaObservable;
	private Observable<Boolean> mSwitchLambdaObservable;

	private Observable<Boolean> mEdittextRxAndroidObservable;
	private Observable<Boolean> mSwitchRxAndroidObservable;


	@Bind(R.id.fragment_form_lambda_example_edittext)
	EditText mLambdaEditText;

	@Bind(R.id.fragment_form_lambda_example_switch)
	Switch mLambdaSwitch;

	@Bind(R.id.fragment_form_lambda_example_button)
	Button mLambdaButton;

	@Bind(R.id.fragment_form_rxandroid_example_edittext)
	EditText mRxAndroidEditText;

	@Bind(R.id.fragment_form_rxandroid_example_switch)
	Switch mRxAndroidSwitch;

	@Bind(R.id.fragment_form_rxandroid_example_button)
	Button mRxAndroidButton;


	public static FormFragment newInstance()
	{
		FormFragment fragment = new FormFragment();
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
		View layout = inflater.inflate(R.layout.fragment_form, container, false);
		ButterKnife.bind(this, layout);
		renderView();
		return layout;
	}


	private void renderView()
	{
		//****************************************
		//RxAndroid + Lambda + method references approach
		//****************************************
		mEdittextLambdaObservable = WidgetObservable.text(mLambdaEditText, false)
				.map(event ->
						event.text().toString())
				.map(text -> text.length() > 8)
				.observeOn(AndroidSchedulers.mainThread());


		mSwitchLambdaObservable = WidgetObservable.input(mLambdaSwitch, false)
				.map(a -> a.value())
				.observeOn(AndroidSchedulers.mainThread());


		mEdittextLambdaObservable
				.distinctUntilChanged()
				.map(stateChanged ->
						stateChanged ? Color.BLACK : Color.RED)
				.subscribe(
						mLambdaEditText::setTextColor);



		Observable.combineLatest(mEdittextLambdaObservable, mSwitchLambdaObservable, (a,b) ->
				a && b)
				.distinctUntilChanged()
				.subscribe(
						mLambdaButton::setEnabled);


		//****************************************
		//RxAndroid approach
		//****************************************
		mEdittextRxAndroidObservable = WidgetObservable.text(mRxAndroidEditText, false)
		.map(new Func1<OnTextChangeEvent, String>()
		{
			@Override
			public String call(OnTextChangeEvent onTextChangeEvent)
			{
				return onTextChangeEvent.text().toString();
			}

		})
		.map(new Func1<String, Boolean>()
		{
			@Override
			public Boolean call(String text)
			{
				return text.length() > 8;
			}

		})
		.observeOn(AndroidSchedulers.mainThread());


		mSwitchRxAndroidObservable = WidgetObservable.input(mRxAndroidSwitch, false)
		.map(new Func1<OnCheckedChangeEvent, Boolean>()
		{
			@Override
			public Boolean call(OnCheckedChangeEvent event)
			{
				return event.value();
			}
		})
		.observeOn(AndroidSchedulers.mainThread());


		mEdittextRxAndroidObservable
		.distinctUntilChanged()
		.map(new Func1<Boolean, Integer>()
		{
			@Override
			public Integer call(Boolean stateChanged)
			{
				return stateChanged ? Color.BLACK : Color.RED;
			}
		})
		.subscribe(new Action1<Integer>()
		{
			@Override
			public void call(Integer color)
			{
				mRxAndroidEditText.setTextColor(color);
			}
		});


		Observable.combineLatest(mEdittextRxAndroidObservable, mSwitchRxAndroidObservable, new Func2<Boolean, Boolean, Boolean>()
		{
			@Override
			public Boolean call(Boolean aBoolean, Boolean aBoolean2)
			{
				return aBoolean && aBoolean2;
			}
		})
		.distinctUntilChanged()
		.subscribe(new Action1<Boolean>()
		{
			@Override
			public void call(Boolean aBoolean)
			{
				mRxAndroidButton.setEnabled(aBoolean);
			}
		});
	}


	@Override
	public void onPause()
	{
		super.onPause();
	}
}
