package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.strv.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by adamcerny on 24/08/15.
 */
public class EditTextFragment extends BaseFragment
{
	private Observable<TextViewTextChangeEvent> mEdittextLambdaObservable;
	private Observable<TextViewTextChangeEvent> mEdittextRxAndroidObservable;

	@Bind(R.id.fragment_edittext_lambda_example_edittext)
	EditText mLambdaEditText;

	@Bind(R.id.fragment_edittext_lambda_example_textview)
	TextView mLambdaTextView;

	@Bind(R.id.fragment_edittext_rxandroid_example_edittext)
	EditText mRxAndroidEditText;

	@Bind(R.id.fragment_edittext_rxandroid_example_textview)
	TextView mRxAndroidTextView;


	public static EditTextFragment newInstance()
	{
		EditTextFragment fragment = new EditTextFragment();
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
		View layout = inflater.inflate(R.layout.fragment_edittext, container, false);
		ButterKnife.bind(this, layout);
		renderView();
		return layout;
	}


	private void renderView()
	{
		//****************************************
		//RxAndroid + Lambda + method references approach
		//****************************************
		mEdittextLambdaObservable = RxTextView.textChangeEvents(mLambdaEditText);

		mCompositeSubscription.add(
			mEdittextLambdaObservable.filter(event ->
					event.text().toString().endsWith("test"))
			.map(event ->
					event.text().toString())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(mLambdaTextView::setText)
		);


		//****************************************
		//RxAndroid approach
		//****************************************
		mEdittextRxAndroidObservable = RxTextView.textChangeEvents(mRxAndroidEditText);

		mCompositeSubscription.add(
			mEdittextRxAndroidObservable.filter(new Func1<TextViewTextChangeEvent, Boolean>()
			{
				@Override
				public Boolean call(TextViewTextChangeEvent onTextChangeEvent)
				{
					return onTextChangeEvent.text().toString().endsWith("test");
				}

			})
			.map(new Func1<TextViewTextChangeEvent, String>()
			{
				@Override
				public String call(TextViewTextChangeEvent onTextChangeEvent)
				{
					return onTextChangeEvent.text().toString();
				}

			})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Action1<String>()
			{
				@Override
				public void call(String filteredString)
				{
					mRxAndroidTextView.setText(filteredString);
				}
			})
		);
	}
}
