package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.strv.rxjavademo.R;
import com.strv.rxjavademo.adapter.CardsGithubAdapter;
import com.strv.rxjavademo.cache.RequestCache;
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
import rx.schedulers.Schedulers;


/**
 * Created by adamcerny on 24/08/15.
 */
public class RetrofitCustomCacheFragment extends BaseFragment
{
	@Bind(R.id.fragment_retrofit_example_recycler)
	RecyclerView mRecyclerView;

	@Bind(R.id.fragment_retrofit_example_progress)
	ProgressBar mProgressBar;

	private CardsGithubAdapter mCardAdapter;

	private RequestCache mCache;

	public static RetrofitCustomCacheFragment newInstance()
	{
		RetrofitCustomCacheFragment fragment = new RetrofitCustomCacheFragment();
		return fragment;
	}


	public RetrofitCustomCacheFragment()
	{
		super();
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
		handleCache();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_retrofit, container, false);
		ButterKnife.bind(this, layout);
		return layout;
	}


	@OnClick(R.id.fragment_retrofit_lambda_example_button)
	public void clickLoadLambdaData()
	{
		if(mProgressBar.getVisibility() == View.VISIBLE)
			return;

		mCardAdapter.clearAdapter();

		GithubService service = new RestAdapter.Builder()
				.setEndpoint(GithubService.BASE_ENDPOINT)
				.build()
				.create(GithubService.class);

		mProgressBar.setVisibility(View.VISIBLE);

		for(String username : RetrofitCustomCacheFragment.getGithubusernames)
		{
			Observable<GithubUserModel> request = service.getUser(username).cache();
			mCache.saveRequest(request);

			mCompositeSubscription.add(
					request
							.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.delay(2, TimeUnit.SECONDS)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(item ->
									{
										mCardAdapter.addGithubItem(item);
										mCache.saveData(item);
										mCache.deleteRequest(request);

										if(mCardAdapter.getItemCount() == RetrofitCustomCacheFragment.getGithubusernames.size())
										{
											mProgressBar.setVisibility(View.GONE);
										}
									},
									error ->
									{
										mProgressBar.setVisibility(View.GONE);
										System.out.println("FUCK : error is : " + error.getLocalizedMessage());
									},
									() -> {
									}
							)
			);
		}
	}


	@OnClick(R.id.fragment_retrofit_rxandroid_example_button)
	public void clickLoadRxAndroidData()
	{
		if(mProgressBar.getVisibility() == View.VISIBLE)
			return;

		mCardAdapter.clearAdapter();

		GithubService service = new RestAdapter.Builder()
				.setEndpoint(GithubService.BASE_ENDPOINT)
				.build()
				.create(GithubService.class);

		mProgressBar.setVisibility(View.VISIBLE);

		for(String username : RetrofitCustomCacheFragment.getGithubusernames)
		{
			Observable<GithubUserModel> request = service.getUser(username).cache();
			mCache.saveRequest(request);

			mCompositeSubscription.add(
					request
							.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.delay(2, TimeUnit.SECONDS)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Subscriber<GithubUserModel>()
							{
								@Override
								public void onNext(GithubUserModel item)
								{
									mCardAdapter.addGithubItem(item);
									mCache.saveData(item);
									mCache.deleteRequest(request);

									if(mCardAdapter.getItemCount() == RetrofitCustomCacheFragment.getGithubusernames.size())
									{
										mProgressBar.setVisibility(View.GONE);
									}
								}


								@Override
								public void onCompleted()
								{
								}


								@Override
								public void onError(Throwable e)
								{
									mProgressBar.setVisibility(View.GONE);
									System.out.println("FUCK : error is : " + e.getLocalizedMessage());
								}
							})
			);
		}
	}


	private void renderView()
	{
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mCardAdapter = new CardsGithubAdapter();
		mRecyclerView.setAdapter(mCardAdapter);
	}


	private void handleCache()
	{
		mCache = RequestCache.getInstance();
		if(mCache.hasRequests())
		{
			reSubscribe();
		}

		if(mCache.hasData())
		{
			mCardAdapter.addGithubItems(mCache.getData());
		}
	}


	private void reSubscribe()
	{
		mProgressBar.setVisibility(View.VISIBLE);

		for(Observable<GithubUserModel> request : mCache.getRequests())
		{
			mCompositeSubscription.add(
					request
							.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(item ->
									{
										mCardAdapter.addGithubItem(item);
										mCache.deleteRequest(request);
										mCache.saveData(item);

										if(mCardAdapter.getItemCount() == RetrofitCustomCacheFragment.getGithubusernames.size())
										{
											mProgressBar.setVisibility(View.GONE);
										}
									},
									error ->
									{
										mProgressBar.setVisibility(View.GONE);
										System.out.println("FUCK : error is : " + error.getLocalizedMessage());
									},
									() -> {
									}
							)
			);
		}

	}


	public static List<String> getGithubusernames = new ArrayList<String>() {{
		add("strvcom");
		add("linkedin");
		add("tumblr");
		add("square");
		add("google");
		add("torvalds");
		add("marak");
		add("facebook");
		add("rails");
	}};
}
