package com.strv.rxjavademo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.strv.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * Created by adamcerny on 24/08/15.
 */
public class FormFragment extends BaseFragment
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
		View layout = inflater.inflate(R.layout.fragment_form, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	private void renderView()
	{
		//****************************************
		//RxAndroid + Lambda + method references approach
		//****************************************
		mEdittextLambdaObservable = RxTextView.textChangeEvents(mLambdaEditText)
				.map(event ->
						event.text().toString())
				.map(text -> text.length() > 8)
				.observeOn(AndroidSchedulers.mainThread());


		mSwitchLambdaObservable = RxCompoundButton.checkedChanges(mLambdaSwitch)
				.observeOn(AndroidSchedulers.mainThread());


		mCompositeSubscription.add(
				mEdittextLambdaObservable
						.distinctUntilChanged()
						.map(stateChanged ->
								stateChanged ? Color.BLACK : Color.RED)
						.subscribe(
								mLambdaEditText::setTextColor)
		);


		mCompositeSubscription.add(
				Observable.combineLatest(mEdittextLambdaObservable, mSwitchLambdaObservable, (a, b) ->
						a && b)
						.distinctUntilChanged()
						.subscribe(
								mLambdaButton::setEnabled)
		);


		//****************************************
		//RxAndroid approach
		//****************************************
		mEdittextRxAndroidObservable = RxTextView.textChangeEvents(mRxAndroidEditText)
		.map(new Func1<TextViewTextChangeEvent, String>()
		{
			@Override
			public String call(TextViewTextChangeEvent onTextChangeEvent)
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


		mSwitchRxAndroidObservable = RxCompoundButton.checkedChanges(mRxAndroidSwitch)
		.observeOn(AndroidSchedulers.mainThread());


		mCompositeSubscription.add(
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
						})
		);

		mCompositeSubscription.add(
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
			})
		);


	}
}
