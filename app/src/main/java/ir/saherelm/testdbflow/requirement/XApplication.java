package ir.saherelm.testdbflow.requirement;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import ir.saherelm.testdbflow.database.XAppDb;

/**
 * the main Application Class ...
 *
 * Created by SaherElm on 13/01/2018.
 */

public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //
        // initialize FlowDb ...
        FlowManager.init(
                new FlowConfig.Builder(this)
                .addDatabaseConfig(
                        new DatabaseConfig.Builder(XAppDb.class)
                        .build()
                )
                .build()
        );

//        FlowManager.init(new FlowConfig.Builder(this)
//            .addDatabaseConfig(new DatabaseConfig.Builder(XAppDb.class)
//                    .modelNotifier(DirectModelNotifier.get())
//                    .build())
//                .build());

        //
//        String mUriAuthority = getApplicationInfo().packageName + ".provider";
//        Log.i("sh_XAPP", "data provider : " + mUriAuthority);
//        XAppProvider.mDbObserver = new FlowContentObserver(mUriAuthority);
//        XAppProvider.mDbObserver.registerForContentChanges(getApplicationContext(), ContactModel.class);
//        XAppProvider.mDbObserver.setNotifyAllUris(true);
    }
}
