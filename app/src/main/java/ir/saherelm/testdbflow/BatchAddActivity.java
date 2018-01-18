package ir.saherelm.testdbflow;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ir.saherelm.testdbflow.database.XAppDb;
import ir.saherelm.testdbflow.database.model.ContactModel;
import ir.saherelm.testdbflow.database.model.ContactModel_Table;
import ir.saherelm.testdbflow.database.model.XBaseModel;
import ir.saherelm.testdbflow.database.model.XObservableBaseModel;
import ir.saherelm.testdbflow.database.model.XObserverBaseModel;
import ir.saherelm.testdbflow.database.model.XObserverBaseModelCallback;
import ir.saherelm.testdbflow.database.model.XTransactionRunner;
import ir.saherelm.testdbflow.requirement.ContactModelAdapter;

public class BatchAddActivity extends AppCompatActivity {

    public static final String TAG = "sh_BatchAddActivity";

    private TextInputLayout mTipFirstName;
    private TextInputLayout mTipLastName;
    private TextInputLayout mTipPhoneNumber;

    private TextInputEditText mEtFirstName;
    private TextInputEditText mEtLastName;
    private TextInputEditText mEtPhoneNumber;

    private Button mBtnAddToDb;
    private Button mBtnClear;
    private Button mBtnAddToList;

    private RecyclerView mRvItems;
    private ContactModelAdapter mAdapter;

    private XObserverBaseModel mContactObserver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_add);

        prepareUI(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContactModel.getModelObservable().addObserver(mContactObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ContactModel.getModelObservable().deleteObserver(mContactObserver);
    }



    private void prepareUI(Bundle savedInstanceState) {
        Log.i(TAG, "prepareUI");

        mTipFirstName = findViewById(R.id.tipFirstName);
        mTipLastName = findViewById(R.id.tipLastName);
        mTipPhoneNumber = findViewById(R.id.tipPhoneNumber);

        mEtFirstName = findViewById(R.id.etFirstName);
        mEtLastName = findViewById(R.id.etLastName);
        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);

        mEtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTipFirstName.setError(null);
            }
        });
        mEtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTipLastName.setError(null);
            }
        });
        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTipPhoneNumber.setError(null);
            }
        });

        mBtnAddToDb = findViewById(R.id.btnAddToDb);
        mBtnClear = findViewById(R.id.btnClear);
        mBtnAddToList = findViewById(R.id.btnAddToList);

        mRvItems = findViewById(R.id.rvItems);
        mAdapter = new ContactModelAdapter();
        mRvItems.setAdapter(mAdapter);

        ContactModel.setObserve(true);
        ContactModel.setObserveActions(false);
        mContactObserver = new XObserverBaseModel(ContactModel.getModelObservable(), new XObserverBaseModelCallback() {
            @Override
            public void call(Class<? extends XBaseModel> table, int action) {
                Log.e(TAG, "Change Happens On Table : " + table.toString() + ", Action : " + action);
            }
        });
    }

    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnClear) {
            handleClearListAction();
        } else if (view == mBtnAddToList) {
            handleAddToListAction();
        } else if (view == mBtnAddToDb) {
            handleAddToDbAction();
        }
    }

    private void handleClearListAction() {
        Log.i(TAG, "handleClearListAction");

        AlertDialog mAlert = new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearList();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearList() {
        Log.i(TAG, "clearList");

        mAdapter.clear();
    }

    private void handleAddToListAction() {
        Log.i(TAG, "handleAddToListAction");

        boolean isError = false;
        String mStrFirstName = mEtFirstName.getText().toString().trim();
        String mStrLastName = mEtLastName.getText().toString().trim();
        String mStrPhoneNumber = mEtPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mStrFirstName)) {
            isError = true;
            mTipFirstName.setError("Input First Name");
        }

        if (TextUtils.isEmpty(mStrLastName)) {
            isError = true;
            mTipLastName.setError("Input First Name");
        }

        if (TextUtils.isEmpty(mStrPhoneNumber)) {
            isError = true;
            mTipPhoneNumber.setError("Input First Name");
        }

        if (isError) {
            return;
        }

        ContactModel mContact = new ContactModel();
        mContact.id = UUID.randomUUID();
        mContact.firstName = mStrFirstName;
        mContact.lastName = mStrLastName;
        mContact.phoneNumber = mStrPhoneNumber;

        mAdapter.add(mContact);
        clearUI();
    }

    private void clearUI() {
        Log.i(TAG, "clearUI");

        mEtFirstName.setText(null);
        mEtLastName.setText(null);
        mEtPhoneNumber.setText(null);

        mTipFirstName.setError(null);
        mTipLastName.setError(null);
        mTipPhoneNumber.setError(null);
    }

    private void handleAddToDbAction() {
        Log.i(TAG, "handleAddToDbAction");

        AlertDialog mAlert = new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addToDb();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void addToDb() {
        Log.i(TAG, "addToDb");

        XTransactionRunner.executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                List<ContactModel> mList = mAdapter.getData();
                for (ContactModel mItem : mList) {
                    ContentValues mValues = new ContentValues();

                    ModelAdapter<ContactModel> mContactTableObject = FlowManager.getModelAdapter(ContactModel.class);
                    mContactTableObject.bindToContentValues(mValues, mItem);
                    databaseWrapper.insertWithOnConflict(mContactTableObject.getTableName(), null, mValues, 0);
                }
            }
        }, new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                mAdapter.clear();

                String strMsg = "add all items ...";
                Log.i(TAG, strMsg);
                Toast.makeText(BatchAddActivity.this, strMsg, Toast.LENGTH_SHORT).show();
            }
        }, new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {

            }
        },  new XTransactionRunner.Notifier[] {
                new XTransactionRunner.Notifier(
                        ContactModel.getModelObservable(),
                        ContactModel.class)});
    }

}
