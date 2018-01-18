package ir.saherelm.testdbflow.database.model;

import android.support.annotation.NonNull;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by SaherElm on 17/01/2018.
 */

public class XObserverBaseModel implements Observer {

    private XObserverBaseModelCallback  mCallback;
    private XObservableBaseModel mObservable;

    public XObserverBaseModel(
            @NonNull XObservableBaseModel observable,
            @NonNull XObserverBaseModelCallback callback) {
        mCallback = callback;
        mObservable = observable;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == mObservable) {
            mCallback.call(mObservable.getTable(), mObservable.getAction());
        }
    }

}
