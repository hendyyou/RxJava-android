package com.strv.rxjavademo.service;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.strv.rxjavademo.Event.FirebaseDataEvent;
import com.strv.rxjavademo.bus.RxCustomBus;


/**
 * Created by adamcerny on 28/08/15.
 */
public class FirebaseService
{
	private final String FIREBASE_URL = "https://rxjava-android.firebaseio.com/data";

	private static FirebaseService mInstance;

	private Firebase mFirebaseData;
	private ValueEventListener mDataListener;

	private FirebaseService(){}

	public static FirebaseService getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new FirebaseService();
		}

		return mInstance;
	}


	public void getData()
	{
		mFirebaseData = new Firebase(FIREBASE_URL);

		mDataListener = mFirebaseData.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				FirebaseDataEvent event = new FirebaseDataEvent();
				event.setFirebaseDataString(dataSnapshot.toString());
				RxCustomBus.getInstance().publish(event);
			}


			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});
	}

	public void unregisterDataObserver()
	{
		if(mDataListener != null)
		{
			mFirebaseData.removeEventListener(mDataListener);
		}
	}
}
