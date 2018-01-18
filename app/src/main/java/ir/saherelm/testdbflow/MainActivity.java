package ir.saherelm.testdbflow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sh_MainActivity";

    private Button mBtnShowList;
    private Button mBtnAdd;
    private Button mBtnManipulate;
    private Button mBtnRealList;
    private Button mBtnBatchAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        prepareUI(savedInstanceState);
    }

    public void onClickView(View view) {
        Log.i(TAG, "onClickView");

        if (view == mBtnShowList) {
            //
            handleShowListAction();
        } else if (view == mBtnAdd) {
            //
            handleAddAction();
        } else if (view == mBtnManipulate) {
            //
            handleManipulateAction();
        } else if (view == mBtnRealList) {
            handleRealListAction();
        } else if (view == mBtnBatchAdd) {
            handleBatchAddAction();
        }
    }



    private void prepareUI(Bundle savedInstanceState) {
        Log.i(TAG, "prepareUI");

        mBtnShowList = findViewById(R.id.btnShowList);
        mBtnAdd = findViewById(R.id.btnAddToDb);
        mBtnManipulate = findViewById(R.id.btnManipulate);
        mBtnRealList = findViewById(R.id.btnRealList);
        mBtnBatchAdd = findViewById(R.id.btnBatchAdd);
    }

    private void loadActivity(@NonNull Class<? extends AppCompatActivity> dest, @Nullable Bundle args) {
        Intent mIntent = new Intent(this, dest);
        if (args != null) {
            mIntent.putExtras(args);
        }

        startActivity(mIntent);
    }

    private void handleManipulateAction() {
        Log.i(TAG, "handleManipulateAction");

        loadActivity(DataManipulationActivity.class, null);
    }

    private void handleAddAction() {
        Log.i(TAG, "handleAddAction");
        loadActivity(AddActivity.class, null);
    }

    private void handleShowListAction() {
        Log.i(TAG, "handleShowListAction");

        loadActivity(ShowListActivity.class, null);
    }

    private void handleRealListAction() {
        Log.i(TAG, "handleRealListAction");

        loadActivity(RealListActivity.class, null);
    }

    private void handleBatchAddAction() {
        Log.i(TAG, "handleBatchAddAction");

        loadActivity(BatchAddActivity.class, null);
    }

}
