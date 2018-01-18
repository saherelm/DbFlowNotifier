package ir.saherelm.testdbflow.database.model;

/**
 * Created by SaherElm on 17/01/2018.
 */

public interface XObserverBaseModelCallback {
    void call(Class<? extends XBaseModel> table, @XObservableBaseModel.Action int action);
}
