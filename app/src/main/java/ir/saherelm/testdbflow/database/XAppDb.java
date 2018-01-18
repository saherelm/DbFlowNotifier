package ir.saherelm.testdbflow.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * the main database class for application ...
 *
 * Created by SaherElm on 13/01/2018.
 */

@SuppressWarnings("ALL")
@Database(name = XAppDb.NAME, version = XAppDb.VERSION)
public class XAppDb {

    public static final String NAME = "XAppDb";

    public static final int VERSION = 1;
}
