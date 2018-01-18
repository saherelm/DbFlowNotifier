package ir.saherelm.testdbflow.database.model;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * the Base model class of Database Entities ...
 *
 * Created by SaherElm on 13/01/2018.
 */

public abstract class XBaseModel extends BaseModel {

    // if this vale is true the model fire actions to observables ...
    private static boolean observe;

    // if this vale is true thie model fire each action seperately to observables
    // and if false the model just fire ACTION_CHANGE to observables ...
    private static boolean observeActions;

    // the Observable Object for each Model ...
    private static XObservableBaseModel mObservable;

    // static definitions ...
    static {
        mObservable = new XObservableBaseModel();
    }

    /**
     * get Model Observe state ...
     *
     * @return a Boolean value which represent Notifier is available or not ...
     */
    public static boolean isObserve() {
        return XBaseModel.observe;
    }

    /**
     * set Model notifier state ...
     *
     * @param observe a boolean value ...
     */
    public static void setObserve(boolean observe) {
        XBaseModel.observe = observe;
    }

    /**
     * get Observe Actions state ...
     *
     * @return a boolean value which represent observable notifies each
     * Action seperately or just notifies ACTION_CHANGE ...
     */
    public static boolean isObserveActions() {
        return observeActions;
    }

    /**
     * set Observe Data manipulation Actions ...
     *
     * @param observeActions a boolean value ...
     */
    public static void setObserveActions(boolean observeActions) {
        XBaseModel.observeActions = observeActions;
    }

    /**
     * accessing model observable ...
     *
     * @return an instance of XObservable class which prepared for this Model ...
     */
    public static XObservableBaseModel getModelObservable() {
        return mObservable;
    }

    public XBaseModel() {
        super();
    }

    //
    // region Data Manipulating methods which Notfify Actions ...
    @Override
    public boolean save() {
        boolean result = super.save();
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_SAVE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public boolean save(@NonNull DatabaseWrapper databaseWrapper) {
        boolean result = super.save(databaseWrapper);
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_SAVE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public boolean delete() {
        boolean result = super.delete();
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_DELETE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public boolean delete(@NonNull DatabaseWrapper databaseWrapper) {
        boolean result = super.delete(databaseWrapper);
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_DELETE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public boolean update() {
        boolean result = super.update();
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_UPDATE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public boolean update(@NonNull DatabaseWrapper databaseWrapper) {
        boolean result = super.update(databaseWrapper);
        if (result
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_UPDATE);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public long insert() {
        long result = super.insert();
        if (result > 0
                && isObserve()) {
            if (isObserveActions()) {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_INSERT);
            } else {
                mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
            }
        }
        return result;
    }

    @Override
    public long insert(DatabaseWrapper databaseWrapper) {
        long result = super.insert(databaseWrapper);
        if (isObserveActions()) {
            mObservable.change(this.getClass(), XObservableBaseModel.ACTION_INSERT);
        } else {
            mObservable.change(this.getClass(), XObservableBaseModel.ACTION_CHANGE);
        }
        return result;
    }
    // endregion

}


