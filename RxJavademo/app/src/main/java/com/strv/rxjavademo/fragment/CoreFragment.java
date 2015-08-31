package com.strv.rxjavademo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strv.rxjavademo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CoreFragment extends BaseFragment
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
		showFragment(ClickCounterFragment.class.getName(), ClickCounterFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_edittext_button)
	public void clickEditTextDemo()
	{
		showFragment(EditTextFragment.class.getName(), EditTextFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_form_button)
	public void clickFormDemo()
	{
		showFragment(FormFragment.class.getName(), FormFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_autocomplete_button)
	public void clickAutocompleteDemo()
	{
		showFragment(AutocompleteFragment.class.getName(), AutocompleteFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_retrofit_button)
	public void clickRetroFitDemo()
	{
		showFragment(RetrofitCustomCacheFragment.class.getName(), RetrofitCustomCacheFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_retrofit_bus_button)
	public void clickRetroFitBusDemo()
	{
		showFragment(RetrofitRxBusCacheFragment.class.getName(), RetrofitRxBusCacheFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_firebase_button)
	public void clickFirebaseDemo()
	{
		showFragment(FirebaseFragment.class.getName(), FirebaseFragment.newInstance());
	}


	@OnClick(R.id.fragment_core_operator_button)
	public void clickOperatorDemo()
	{
		showFragment(OperatorFragment.class.getName(), OperatorFragment.newInstance());
	}


	private void showFragment(String className, Fragment fragment)
	{
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.addToBackStack(className)
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter_pop, R.anim.slide_exit_pop)
				.replace(android.R.id.content,
						fragment,
						className)
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
