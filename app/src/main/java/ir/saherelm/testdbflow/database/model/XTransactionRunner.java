package ir.saherelm.testdbflow.database.model;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import ir.saherelm.testdbflow.database.XAppDb;

/**
 * this is a Transaction Runner for DBFlow which Notify Observable when
 * it's running Successfully ...
 *
 * Created by SaherElm on 18/01/2018.
 */

public class XTransactionRunner {

    /**
     * a NOtifier Class which represent which table and which notifier must fired on succed ...
     *
     * you must pass an Erray of this class to executeTransactionMethod ...
     */
    public static class Notifier {
        public XObservableBaseModel notifier;
        public Class<? extends XBaseModel> table;

        public Notifier(XObservableBaseModel notifier, Class<? extends XBaseModel> table) {
            this.notifier = notifier;
            this.table = table;
        }
    }

    /**
     * this method execute a transaction and notify Observable after succeed ...
     *
     * @param execute an instance of ITransaction interface which must runs on DatabaseWrapper ...
     * @param success an instance of Transaction.Success Callback which calls when Transaction Done Successfully ...
     * @param error an instance of Transaction.Error Callback which calls when an Error happens on Transaction Execution ...
     * @param notifiers an Array of Notifier objects which determines which Models and Observables must Notifies after execution succeed ...
     */
    @SuppressWarnings("uncheck")
    public static void executeTransaction(
            ITransaction execute,
            final Transaction.Success success,
            Transaction.Error error,
            final Notifier[] notifiers) {

        FlowManager.getDatabase(XAppDb.class)
                .beginTransactionAsync(execute)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        success.onSuccess(transaction);

                        for (Notifier mNotifier : notifiers) {
                            mNotifier.notifier.change(mNotifier.table, XObservableBaseModel.ACTION_CHANGE);
                        }
                    }
                })
                .error(error)
                .execute();
    }
}
