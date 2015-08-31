package com.strv.rxjavademo.Event;

/**
 * Created by adamcerny on 31/08/15.
 */
public class ApiCallEvent
{
	private int mSample;
	private String mCode;
	private String mContent;


	public int getSample()
	{
		return mSample;
	}


	public void setSample(int sample)
	{
		this.mSample = sample;
	}


	public String getCode()
	{
		return mCode;
	}


	public void setCode(String code)
	{
		this.mCode = code;
	}


	public String getContent()
	{
		return mContent;
	}


	public void setContent(String content)
	{
		this.mContent = content;
	}
}
