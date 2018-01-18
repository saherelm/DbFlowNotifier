package ir.saherelm.testdbflow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import ir.saherelm.testdbflow.database.model.ContactModel;
import ir.saherelm.testdbflow.database.model.ContactModel_Table;
import ir.saherelm.testdbflow.requirement.ContactModelAdapter;

public class RealListActivity extends AppCompatActivity {

    private static final String TAG = "sh_RealListActivity";

    private TextView mTextView4;
    private RecyclerView mRvItems;
    private Button mBtnRemove;
    private TextInputLayout mTipPosition;
    private TextInputEditText mEtPosition;
    private FlowQueryList<ContactModel> mQueryList;
    private ContactModelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_list);
        Log.i(TAG, "onCreate");

        prepareUI(savedInstanceState);
        refreshData();
    }



    private void prepareUI(Bundle savedInstanceState) {
        Log.i(TAG, "prepareUI");

        mTextView4 = findViewById(R.id.textView4);
        mBtnRemove = findViewById(R.id.btnRemove);
        mTipPosition = findViewById(R.id.tipPosition);
        mEtPosition = findViewById(R.id.etPosition);

        mRvItems = findViewById(R.id.rvItems);
        mAdapter = new ContactModelAdapter();
        mRvItems.setAdapter(mAdapter);
    }

    private void refreshData() {
        Log.i(TAG, "refreshData");

        mAdapter.clear();
        if (mQueryList == null) {
            mQueryList = SQLite.select().from(ContactModel.class).orderBy(ContactModel_Table.first_name, true).flowQueryList();
            mQueryList.registerForContentChanges(this);
            mQueryList.addOnCursorRefreshListener(new FlowCursorList.OnCursorRefreshListener<ContactModel>() {
                @Override
                public void onCursorRefreshed(@NonNull FlowCursorList<ContactModel> cursorList) {
                    Log.e(TAG, "onListRefreshed");
                    mAdapter.clear();
                    mAdapter.addRange(mQueryList);
                }
            });
            mAdapter.addRange(mQueryList);
        }
    }

    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnRemove) {
            handleRemoveAction();
        }
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

        boolean isError = false;
        String mStrPosition = mEtPosition.getText().toString().trim();
        if (TextUtils.isEmpty(mStrPosition)) {
            isError = true;
            mTipPosition.setError("Input Contact Position");
            return;
        }

        int mPosition = Integer.valueOf(mStrPosition);
        ContactModel mContact = SQLite.select()
                .from(ContactModel.class)
                .orderBy(ContactModel_Table.first_name, true)
                .limit(1)
                .offset(mPosition)
                .querySingle();

        if (mContact == null) {
            isError = true;
            mTipPosition.setError("Invalid Position");
            return;
        }


        String mStrMsg = "";
        if (mContact.delete()) {
            mStrMsg = "Contact Deleted Successfully";

            Log.i(TAG, mStrMsg);
            Toast.makeText(RealListActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();
            //mQueryList.refresh();

            return;
        }

        mStrMsg = "Error in Removing Contact";

        Log.e(TAG, mStrMsg);
        Toast.makeText(RealListActivity.this, mStrMsg, Toast.LENGTH_SHORT).show();

    }

}
