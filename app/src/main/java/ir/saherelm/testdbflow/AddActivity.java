package ir.saherelm.testdbflow;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import ir.saherelm.testdbflow.database.model.ContactModel;
import ir.saherelm.testdbflow.database.model.XBaseModel;
import ir.saherelm.testdbflow.database.model.XObserverBaseModel;
import ir.saherelm.testdbflow.database.model.XObserverBaseModelCallback;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "sh_TestAddActivity";

    private TextInputLayout mTipFirstName;
    private TextInputLayout mTipLastName;
    private TextInputLayout mTipPhoneNumber;

    private EditText mEtFirstName;
    private EditText mEtLastName;
    private EditText mEtPhoneNumber;

    private Button mBtnAdd;
    private Button mBtnClear;

    private TextView mTvInsertedId;

    private XObserverBaseModel mContactObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add);
        Log.i(TAG, "");

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
        Log.i(TAG, "onCreate");

        mTipFirstName = findViewById(R.id.tipFirstName);
        mTipLastName = findViewById(R.id.tipLastName);
        mTipPhoneNumber = findViewById(R.id.tipPhoneNumber);

        mEtFirstName = findViewById(R.id.etFirstName);
        mEtLastName = findViewById(R.id.etLastName);
        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);

        mBtnAdd = findViewById(R.id.btnAddToDb);
        mBtnClear = findViewById(R.id.btnClear);

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

        mTvInsertedId = findViewById(R.id.tvInsertedId);

        //
        // Here i add Notifier to find Changes on Table ...
        ContactModel.setObserve(true);
        ContactModel.setObserveActions(true);
        mContactObserver = new XObserverBaseModel(ContactModel.getModelObservable(), new XObserverBaseModelCallback() {
            @Override
            public void call(Class<? extends XBaseModel> table, int action) {
                Log.e(TAG, "Change Happens On Table : " + table.toString() + ", Action : " + action);
            }
        });


    }

    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnAdd) {
            handleAddAction();
        } else if (view == mBtnClear) {
            handleClearAction();
        }
    }

    private void handleAddAction() {
        Log.i(TAG, "handleAddAction");

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

        long aLong =  mContact.insert();
        if (aLong >= 0) {
            clearContent();
            mTvInsertedId.setText(String.valueOf(aLong));
        }
    }

    private void handleClearAction() {
        Log.i(TAG, "handleClearAction");

        AlertDialog mAlert = new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearContent();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearContent() {
        Log.i(TAG, "clearContent");

        //
        mTvInsertedId.setText(null);
        mEtFirstName.setText(null);
        mEtLastName.setText(null);
        mEtPhoneNumber.setText(null);

        //
        mTipFirstName.setError(null);
        mTipLastName.setError(null);
        mTipPhoneNumber.setError(null);

        //
        mEtFirstName.findFocus();
    }
}
