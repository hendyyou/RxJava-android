package com.strv.rxjavademo.bus;

import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * As you can see, we are using another subject than in our other bus RxCustomBus
 * Now we are using ReplaySubject which is able to cache events - you can set how many event
 * and for how long. In our case, we are caching latest 9 API calls - however, you can also
 * cache i.e. over time span
 *
 * Created by adamcerny on 30/08/15.
 */
public class RxCustomCacheBus
{
	private static RxCustomCacheBus mInstance;

	//SerializedSubject is very important - we want to be able to publish/subscribe items
	//on different threads - http://reactivex.io/RxJava/javadoc/rx/subjects/SerializedSubject.html
	private final Subject<Object, Object> rxBus = new SerializedSubject<>(ReplaySubject.createWithSize(9));

	private RxCustomCacheBus()
	{

	}


	static public RxCustomCacheBus getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new RxCustomCacheBus();
		}

		return mInstance;
	}


	/**
	 * General method for publishing of events
	 * @param obj
	 */
	public void publish(Object obj)
	{
		rxBus.onNext(obj);
	}


	/**
	 * General method for observing on all events
	 * @return
	 */
	public Observable<Object> observe()
	{
		return rxBus;
	}


	/**
	 * Method to observe on certain Event class
	 * @param eventType
	 * @param <T>
	 * @return
	 */
	public <T extends Object> Observable<T> observe(final Class<T> eventType)
	{
		return rxBus.filter(eventType::isInstance)
				.cast(eventType);
	}


	/**
	 *
	 * @param eventType
	 * @param <T>
	 * @return Return true if subject has any event of certain type, otherwise return false
	 */
	public <T extends Object> boolean hasEventsOfType(final Class<T> eventType)
	{
		Object[] events = rxBus.getValues();

		int count = 0;
		for(Object e : events)
		{
			if(eventType.isInstance(e))
			{
				count++;
			}
		}

		return (count > 0);
	}
}
