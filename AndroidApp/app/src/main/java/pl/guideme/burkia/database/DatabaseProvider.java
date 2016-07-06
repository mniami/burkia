package pl.guideme.burkia.database;

import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseProvider {
    //private final SQLiteDatabase mSQLiteDatabase;
    public DatabaseProvider(){
        //mSQLiteDatabase = new SQLiteDatabase();
    }
}
