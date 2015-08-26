package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.strv.rxjavademo.R;
import com.strv.rxjavademo.adapter.CardsGithubAdapter;
import com.strv.rxjavademo.model.GithubUserModel;
import com.strv.rxjavademo.service.GithubService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by adamcerny on 24/08/15.
 */
public class RetrofitFragment extends Fragment
{
	@Bind(R.id.fragment_retrofit_example_button)
	Button mLoadInfoButton;

	@Bind(R.id.fragment_retrofit_example_recycler)
	RecyclerView mRecyclerView;

	private CardsGithubAdapter mCardAdapter;


	public static RetrofitFragment newInstance()
	{
		RetrofitFragment fragment = new RetrofitFragment();
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
		View layout = inflater.inflate(R.layout.fragment_retrofit_example, container, false);
		ButterKnife.bind(this, layout);
		renderView();
		return layout;
	}


	@OnClick(R.id.fragment_retrofit_example_button)
	public void clickLoadLambdaData()
	{
		GithubService service = new RestAdapter.Builder()
									.setEndpoint(GithubService.BASE_ENDPOINT)
									.build()
									.create(GithubService.class);

		for(String username : RetrofitFragment.getGithubusernames)
		{
			service.getUser(username)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<GithubUserModel>()
					{
						@Override
						public void onNext(GithubUserModel item)
						{
							mCardAdapter.addGithubItem(item);
						}


						@Override
						public void onCompleted()
						{
						}


						@Override
						public void onError(Throwable e)
						{
							System.out.println("FUCK : error is : " + e.getLocalizedMessage());
						}
					});
		}
	}


	private void renderView()
	{
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mCardAdapter = new CardsGithubAdapter();
		mRecyclerView.setAdapter(mCardAdapter);
	}


	@Override
	public void onPause()
	{
		super.onPause();
	}


	public static List<String> getGithubusernames = new ArrayList<String>() {{
		add("strvcom");
		add("linkedin");
		add("tumblr");
		add("square");
		add("google");
		add("stripe");
		add("angular");
		add("facebook");
		add("rails");
	}};
}
