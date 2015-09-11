package com.strv.rxjavademo.service;

import com.strv.rxjavademo.model.GithubUserModel;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;


/**
 * Created by adamcerny on 26/08/15.
 */
public interface GithubService
{
	final String BASE_ENDPOINT = "https://api.github.com/";

	@GET("users/{company}")
	Observable<GithubUserModel> getUser(@Path("company") String company);
}
