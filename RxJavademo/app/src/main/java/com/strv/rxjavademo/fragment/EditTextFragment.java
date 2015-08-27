package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.strv.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by adamcerny on 24/08/15.
 */
public class EditTextFragment extends BaseFragment
{
	private Observable<OnTextChangeEvent> mEdittextLambdaObservable;
	private Observable<OnTextChangeEvent> mEdittextRxAndroidObservable;

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
		mEdittextLambdaObservable = WidgetObservable.text(mLambdaEditText, false);

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
		mEdittextRxAndroidObservable = WidgetObservable.text(mRxAndroidEditText, false);

		mCompositeSubscription.add(
			mEdittextRxAndroidObservable.filter(new Func1<OnTextChangeEvent, Boolean>()
			{
				@Override
				public Boolean call(OnTextChangeEvent onTextChangeEvent)
				{
					return onTextChangeEvent.text().toString().endsWith("test");
				}

			})
			.map(new Func1<OnTextChangeEvent, String>()
			{
				@Override
				public String call(OnTextChangeEvent onTextChangeEvent)
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
