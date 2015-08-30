package com.strv.rxjavademo.Event;

import com.strv.rxjavademo.model.GithubUserModel;

import rx.Observable;


/**
 * Created by adamcerny on 30/08/15.
 */
public class RetrofitRequestEvent
{
	private Observable<GithubUserModel> mRequest;


	public Observable<GithubUserModel> getRequest()
	{
		return mRequest;
	}


	public void setRequest(Observable<GithubUserModel> request)
	{
		mRequest = request;
	}
}
