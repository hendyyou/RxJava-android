package com.strv.rxjavademo.cache;

import com.strv.rxjavademo.model.GithubUserModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


/**
 * Created by adamcerny on 29/08/15.
 */
public class RequestCache
{
	private static RequestCache mInstance;

	private List<Observable<GithubUserModel>> mGithubRequests = new ArrayList<Observable<GithubUserModel>>();
	private List<GithubUserModel> mDataCache = new ArrayList<GithubUserModel>();

	private RequestCache()
	{

	}


	static public RequestCache getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new RequestCache();
		}

		return mInstance;
	}


	public void saveRequest(Observable<GithubUserModel> request)
	{
		mGithubRequests.add(request);
	}


	public void saveData(GithubUserModel data)
	{
		mDataCache.add(data);
	}


	public List<Observable<GithubUserModel>> getRequests()
	{
		return mGithubRequests;
	}


	public List<GithubUserModel> getData()
	{
		return mDataCache;
	}


	public void deleteRequest(Observable<GithubUserModel> request)
	{
		mGithubRequests.remove(request);
	}


	public void deleteCache()
	{
		mGithubRequests.clear();
		mDataCache.clear();
	}


	public boolean hasRequests()
	{
		return !mGithubRequests.isEmpty();
	}

	public boolean hasData()
	{
		return !mDataCache.isEmpty();
	}
}
