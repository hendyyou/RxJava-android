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

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Action1;


/**
 * Created by adamcerny on 24/08/15.
 */
public class AutocompleteFragment extends BaseFragment
{
	private Observable<TextViewTextChangeEvent> mEdittextLambdaObservable;
	private Observable<TextViewTextChangeEvent> mEdittextRxAndroidObservable;

	@Bind(R.id.fragment_autocomplete_lambda_example_edittext)
	EditText mLambdaEditText;

	@Bind(R.id.fragment_autocomplete_lambda_example_textview)
	TextView mLambdaTextView;

	@Bind(R.id.fragment_autocomplete_rxandroid_example_edittext)
	EditText mRxAndroidEditText;

	@Bind(R.id.fragment_autocomplete_rxandroid_example_textview)
	TextView mRxAndroidTextView;


	public static AutocompleteFragment newInstance()
	{
		AutocompleteFragment fragment = new AutocompleteFragment();
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
		View layout = inflater.inflate(R.layout.fragment_autocomplete, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	private void renderView()
	{
		//****************************************
		//RxAndroid + Lambda + method references approach
		//****************************************
		mEdittextLambdaObservable = RxTextView.textChangeEvents(mLambdaEditText);

		mCompositeSubscription.add(
			mEdittextLambdaObservable
			.debounce(300, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(event ->
					mLambdaTextView.setText("Handle autocomplete request: " + event.text() + "\n" + mLambdaTextView.getText()))
		);


		//****************************************
		//RxAndroid approach
		//****************************************
		mEdittextRxAndroidObservable = RxTextView.textChangeEvents(mRxAndroidEditText);

		mCompositeSubscription.add(
			mEdittextRxAndroidObservable
			.debounce(300, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Action1<TextViewTextChangeEvent>()
			{
				@Override
				public void call(TextViewTextChangeEvent event)
				{
					mRxAndroidTextView.setText("Handle autocomplete request: " + event.text() + "\n" + mRxAndroidTextView.getText());
				}
			})
		);
	}
}
