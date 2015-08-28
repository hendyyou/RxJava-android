package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.strv.rxjavademo.R;

import java.util.concurrent.TimeUnit;

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
public class AutocompleteFragment extends BaseFragment
{
	private Observable<OnTextChangeEvent> mEdittextLambdaObservable;
	private Observable<OnTextChangeEvent> mEdittextRxAndroidObservable;

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
		if(getArguments() != null)
		{

		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_autocomplete, container, false);
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
			mEdittextLambdaObservable
			.debounce(300, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(event ->
					mLambdaTextView.setText("Handle autocomplete request: " + event.text() + "\n" + mLambdaTextView.getText()))
		);


		//****************************************
		//RxAndroid approach
		//****************************************
		mEdittextRxAndroidObservable = WidgetObservable.text(mRxAndroidEditText, false);

		mCompositeSubscription.add(
			mEdittextRxAndroidObservable
			.debounce(300, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Action1<OnTextChangeEvent>()
			{
				@Override
				public void call(OnTextChangeEvent event)
				{
					mRxAndroidTextView.setText("Handle autocomplete request: " + event.text() + "\n" + mRxAndroidTextView.getText());
				}
			})
		);
	}
}
