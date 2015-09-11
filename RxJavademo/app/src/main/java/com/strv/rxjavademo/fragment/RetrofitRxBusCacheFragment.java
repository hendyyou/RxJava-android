package com.strv.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.strv.rxjavademo.Event.RetrofitRequestEvent;
import com.strv.rxjavademo.R;
import com.strv.rxjavademo.adapter.CardsGithubAdapter;
import com.strv.rxjavademo.bus.RxCustomCacheBus;
import com.strv.rxjavademo.model.GithubUserModel;
import com.strv.rxjavademo.service.GithubService;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by adamcerny on 24/08/15.
 */
public class RetrofitRxBusCacheFragment extends BaseFragment
{
	@Bind(R.id.fragment_retrofit_example_recycler)
	RecyclerView mRecyclerView;

	@Bind(R.id.fragment_retrofit_example_progress)
	ProgressBar mProgressBar;

	private CardsGithubAdapter mCardAdapter;

	private RxCustomCacheBus mRxBusRequestCache = RxCustomCacheBus.getInstance();


	public static RetrofitRxBusCacheFragment newInstance()
	{
		RetrofitRxBusCacheFragment fragment = new RetrofitRxBusCacheFragment();
		return fragment;
	}


	public RetrofitRxBusCacheFragment()
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

		GithubService service = new Retrofit.Builder()
				.baseUrl(GithubService.BASE_ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(GithubService.class);

		mProgressBar.setVisibility(View.VISIBLE);

		for(String username : RetrofitCustomCacheFragment.getGithubusernames)
		{
			Observable<GithubUserModel> request = service.getUser(username).cache();

			mCompositeSubscription.add(
				request
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.delay(2, TimeUnit.SECONDS)
					.observeOn(AndroidSchedulers.mainThread())
					.doOnUnsubscribe(() ->
					{
						RetrofitRequestEvent event = new RetrofitRequestEvent();
						event.setRequest(request);
						mRxBusRequestCache.publish(event);
					})
					.subscribe(item ->
					{
						mCardAdapter.addGithubItem(item);

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

		GithubService service = new Retrofit.Builder()
				.baseUrl(GithubService.BASE_ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(GithubService.class);

		mProgressBar.setVisibility(View.VISIBLE);

		for(String username : RetrofitCustomCacheFragment.getGithubusernames)
		{
			Observable<GithubUserModel> request = service.getUser(username).cache();

			mCompositeSubscription.add(
				request
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.delay(2, TimeUnit.SECONDS)
					.observeOn(AndroidSchedulers.mainThread())
					.doOnUnsubscribe(new Action0()
					{
						@Override
						public void call()
						{
							RetrofitRequestEvent event = new RetrofitRequestEvent();
							event.setRequest(request);
							mRxBusRequestCache.publish(event);
						}
					})
					.subscribe(new Subscriber<GithubUserModel>()
					{
						@Override
						public void onNext(GithubUserModel item)
						{
							mCardAdapter.addGithubItem(item);

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
		if(mRxBusRequestCache.hasEventsOfType(RetrofitRequestEvent.class))
		{
			reSubscribe();
		}
	}


	private void reSubscribe()
	{
		mProgressBar.setVisibility(View.VISIBLE);

		mRxBusRequestCache.observe(RetrofitRequestEvent.class)
			.subscribe(item ->
				item.getRequest()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(response ->
					{
						mCardAdapter.addGithubItem(response);

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
					() -> {})
						)
			.unsubscribe();
	}
}
