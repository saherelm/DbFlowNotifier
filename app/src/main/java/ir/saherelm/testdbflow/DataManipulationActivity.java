package ir.saherelm.testdbflow;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
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
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.UUID;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ir.saherelm.testdbflow.database.model.ContactModel;

public class DataManipulationActivity extends AppCompatActivity {

    private static final String TAG = "sh_DataManipulate";

    private TextInputLayout mTipContactPosition;
    private TextInputLayout mTipId;
    private TextInputLayout mTipFirstName;
    private TextInputLayout mTipLastName;
    private TextInputLayout mTipPhoneNumber;

    private TextInputEditText mEtContactPosition;
    private TextInputEditText mEtId;
    private TextInputEditText mEtFirstName;
    private TextInputEditText mEtLastName;
    private TextInputEditText mEtPhoneNumber;

    private Button mBtnRetrieve;
    private Button mBtnUpdate;
    private Button mBtnRemove;
    private Button mBtnClear;

    private ContactModel mCurrentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manipulation);
        Log.i(TAG, "onCreate");

        prepareUI(savedInstanceState);
    }



    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnClear) {
            handleClearAction();
        } else if (view == mBtnRetrieve) {
            handleRetrieveAction();
        } else if (view == mBtnRemove) {
            handleRemoveAction();
        } else if (view == mBtnUpdate) {
            handleUpdateAction();
        }
    }

    private void prepareUI(Bundle savedInstanceState) {
        Log.i(TAG, "prepareUI");

        mTipContactPosition = findViewById(R.id.tipContactPosition);
        mTipId = findViewById(R.id.tipId);
        mTipFirstName = findViewById(R.id.tipFirstName);
        mTipLastName = findViewById(R.id.tipLastName);
        mTipPhoneNumber = findViewById(R.id.tipPhoneNumber);

        mEtContactPosition = findViewById(R.id.etContactPosition);
        mEtId = findViewById(R.id.etId);
        mEtFirstName = findViewById(R.id.etFirstName);
        mEtLastName = findViewById(R.id.etLastName);
        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);

        mBtnRetrieve = findViewById(R.id.btnRetrieve);
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mBtnRemove = findViewById(R.id.btnRemove);
        mBtnClear = findViewById(R.id.btnClear);

        mEtContactPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTipContactPosition.setError(null);
            }
        });
        mEtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTipId.setError(null);
            }
        });
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

        mCurrentContact = null;

        mEtContactPosition.setText(null);
        mEtId.setText(null);
        mEtFirstName.setText(null);
        mEtLastName.setText(null);
        mEtPhoneNumber.setText(null);

        mEtContactPosition.requestFocus();
    }

    private void handleRetrieveAction() {
        Log.i(TAG, "handleRetrieveAction");

        boolean isError = false;
        String mStrPosition = mEtContactPosition.getText().toString().trim();
        if (TextUtils.isEmpty(mStrPosition)) {
            isError = true;
            mTipContactPosition.setError("Input Contact Position");
            return;
        }

        int mPosition = Integer.valueOf(mStrPosition);
        ContactModel mContact = SQLite.select()
                .from(ContactModel.class)
                .limit(1)
                .offset(mPosition)
                .querySingle();

        if (mContact == null) {
            isError = true;
            mTipContactPosition.setError("Invalid Position");
            return;
        }

        mCurrentContact = mContact;
        fillCurrentContactModel();
    }

    private void fillCurrentContactModel() {
        Log.i(TAG, "fillCurrentContactModel");

        if (mCurrentContact == null) {
            return;
        }

        mEtId.setText(mCurrentContact.id.toString());
        mEtFirstName.setText(mCurrentContact.firstName);
        mEtLastName.setText(mCurrentContact.lastName);
        mEtPhoneNumber.setText(mCurrentContact.phoneNumber);
    }

    private void handleRemoveAction() {
        Log.i(TAG, "handleRemoveAction");

        AlertDialog mAlert = new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeCurrentContact();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void removeCurrentContact() {
        Log.i(TAG, "removeCurrentContact");

        if (mCurrentContact == null) {
            return;
        }

        boolean aBoolean = mCurrentContact.delete();
        if (aBoolean) {
            String mStrMsg = "Contact Deleted Successfully";
            Toast.makeText(DataManipulationActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();
            Log.i(TAG, mStrMsg);
            return;
        }

        String mStrMsg = "Error in Removing Contact";
        Toast.makeText(DataManipulationActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, mStrMsg);
    }

    private void handleUpdateAction() {
        Log.i(TAG, "handleUpdateAction");

        if (mCurrentContact == null) {
            mTipContactPosition.setError("Load a Contact First ...");
            return;
        }

        boolean isError = false;
        String mStrId = mEtId.getText().toString().trim();
        String mStrFirstName = mEtFirstName.getText().toString().trim();
        String mStrLastName = mEtLastName.getText().toString().trim();
        String mStrPhoneNumber = mEtPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mStrId)) {
            isError = true;
            mTipId.setError("Required");
        }
        if (TextUtils.isEmpty(mStrFirstName)) {
            isError = true;
            mTipFirstName.setError("Required");
        }

        if (TextUtils.isEmpty(mStrLastName)) {
            isError = true;
            mTipLastName.setError("Required");
        }

        if (TextUtils.isEmpty(mStrPhoneNumber)) {
            isError = true;
            mTipPhoneNumber.setError("Required");
        }

        if (isError) {
            return;
        }

        if (!mCurrentContact.id.toString().equals(mStrId)) {
            mTipContactPosition.setError("Current and Edited Contact are not same");
            return;
        }

        ContactModel mItem = new ContactModel();
        mItem.id = UUID.fromString(mStrId);
        mItem.firstName = mStrFirstName;
        mItem.lastName = mStrLastName;
        mItem.phoneNumber = mStrPhoneNumber;

        if (mCurrentContact.equals(mItem)) {
            mTipContactPosition.setError("There is no Change");
            return;
        }

        mCurrentContact.firstName = mStrFirstName;
        mCurrentContact.lastName = mStrLastName;
        mCurrentContact.phoneNumber = mStrPhoneNumber;

        boolean aBoolean = mCurrentContact.save();
        String mStrMsg = "";
        if (aBoolean) {
            mStrMsg = "Contact Update Successfully";
            Log.i(TAG, mStrMsg);
            Toast.makeText(DataManipulationActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();

            return;
        }

        mStrMsg = "Contact Update Failed";
        Log.e(TAG, mStrMsg);
        Toast.makeText(DataManipulationActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();
    }

}
