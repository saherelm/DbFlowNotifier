package ir.saherelm.testdbflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import ir.saherelm.testdbflow.database.model.ContactModel;
import ir.saherelm.testdbflow.database.model.ContactModel_Table;
import ir.saherelm.testdbflow.requirement.ContactModelAdapter;

public class ShowListActivity extends AppCompatActivity {

    private static final String TAG = "sh_ShowListActivity";

    private Button mBtnRefresh;
    private TextView mTvNoItem;

    private RecyclerView mRvItems;
    private ContactModelAdapter mAdapter;
    private ContactAdapterObserver mAdapterObserver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        Log.i(TAG, "onCreate");

        prepareUI(savedInstanceState);
        refreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAdapter.unregisterAdapterDataObserver(mAdapterObserver);
        mAdapterObserver = null;
        mAdapter = null;
    }



    private void prepareUI(Bundle savedInstanceState) {
        Log.i(TAG, "prepareUI");

        mTvNoItem = findViewById(R.id.tvNoItem);
        mBtnRefresh = findViewById(R.id.btnRefresh);

        mRvItems = findViewById(R.id.rvItems);
        mAdapter = new ContactModelAdapter();
        mAdapterObserver = new ContactAdapterObserver();
        mAdapter.registerAdapterDataObserver(mAdapterObserver);
        mRvItems.setAdapter(mAdapter);
    }

    private void refreshData() {
        Log.i(TAG, "refreshData");

        mAdapter.clear();
        List<ContactModel> mItems = SQLite.select()
                .from(ContactModel.class)
                .orderBy(ContactModel_Table.first_name, true)
                .queryList();
        mAdapter.addRange(mItems);
    }

    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnRefresh) {
            handleRefreshAction();
        }
    }

    private void handleRefreshAction() {
        Log.i(TAG, "handleRefreshAction");

        refreshData();
    }

    private void handleAdapterDataChange() {
        Log.i(TAG, "handleAdapterDataChange");

        if (mAdapter.getItemCount() == 0) {
            mTvNoItem.setVisibility(View.VISIBLE);
            mRvItems.setVisibility(View.GONE);
        } else {
            mTvNoItem.setVisibility(View.GONE);
            mRvItems.setVisibility(View.VISIBLE);
        }
    }


    private class ContactAdapterObserver extends RecyclerView.AdapterDataObserver {

        public ContactAdapterObserver() {
            super();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            handleAdapterDataChange();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            onChanged();
        }

    }
}
