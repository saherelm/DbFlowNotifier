package ir.saherelm.testdbflow.database.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Observable;

/**
 * a Simple Data base Model Notifier ...
 *
 * Created by SaherElm on 17/01/2018.
 */

public class XObservableBaseModel extends Observable {


    @IntDef({
            ACTION_INSERT,
            ACTION_DELETE,
            ACTION_UPDATE,
            ACTION_SAVE,
            ACTION_CHANGE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }
    public static final int ACTION_INSERT = 100;
    public static final int ACTION_DELETE = 101;
    public static final int ACTION_UPDATE = 102;
    public static final int ACTION_SAVE = 103;
    public static final int ACTION_CHANGE = 104;

    @Action
    private int mAction;
    private Class<? extends XBaseModel> mTable;

    public void change(Class<? extends XBaseModel> table, @Action int action) {
        this.mAction = action;
        this.mTable = table;
        setChanged();
        notifyObservers(action);
    }

    @Action
    public int getAction() {
        return mAction;
    }

    public Class<? extends XBaseModel> getTable() {
        return mTable;
    }

}
