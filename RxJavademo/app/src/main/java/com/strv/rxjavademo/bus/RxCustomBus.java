package com.strv.rxjavademo.bus;

import com.strv.rxjavademo.fragment.ClickCounterFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * Created by adamcerny on 28/08/15.
 *
 * As you can see, we are using PublishSubject object. Once there is an observer,
 * subject publishes its events to it and they are no longer accessible.
 *
 * However, if you want to cache events to be able to send them to diferent subscribers,
 * you should use ReplaySubject.
 */
public class RxCustomBus
{
	private static RxCustomBus mInstance;

	//SerializedSubject is very important - we want to be able to publish/subscribe items
	//on different threads - http://reactivex.io/RxJava/javadoc/rx/subjects/SerializedSubject.html
	private final Subject<Object, Object> rxBus = new SerializedSubject<>(PublishSubject.create());

	private RxCustomBus()
	{

	}


	static public RxCustomBus getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new RxCustomBus();
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
}
