package com.strv.rxjavademo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strv.rxjavademo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CoreFragment extends android.support.v4.app.Fragment
{

	public static CoreFragment newInstance()
	{
		CoreFragment fragment = new CoreFragment();
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
		View layout = inflater.inflate(R.layout.fragment_core, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	@OnClick(R.id.fragment_core_click_button)
	public void clickCounterDemo()
	{
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.addToBackStack(ClickCounterFragment.class.getName())
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter_pop, R.anim.slide_exit_pop)
				.replace(android.R.id.content,
						ClickCounterFragment.newInstance(),
						ClickCounterFragment.class.getName())
				.commit();
	}


	@OnClick(R.id.fragment_core_edittext_button)
	public void clickEditTextDemo()
	{
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.addToBackStack(EditTextExampleFragment.class.getName())
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter_pop, R.anim.slide_exit_pop)
				.replace(android.R.id.content,
						EditTextExampleFragment.newInstance(),
						EditTextExampleFragment.class.getName())
				.commit();
	}


	@OnClick(R.id.fragment_core_retrofit_button)
	public void clickRetroFitDemo()
	{
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.addToBackStack(RetrofitFragment.class.getName())
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter_pop, R.anim.slide_exit_pop)
				.replace(android.R.id.content,
						RetrofitFragment.newInstance(),
						RetrofitFragment.class.getName())
				.commit();
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}


	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}
