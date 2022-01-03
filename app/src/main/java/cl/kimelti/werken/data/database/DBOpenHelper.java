package cl.kimelti.werken.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import cl.kimelti.werken.App;
import cl.kimelti.werken.service.EstadoService;

public class  DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper instance;

    public static synchronized DBOpenHelper getInstance(){
        if (instance == null) {
            instance = new DBOpenHelper(App.getContext());
        }
        return instance;
    }

    private DBOpenHelper(@Nullable Context context){
        super(context,DbConstants.DATABASE_NAME,null,DbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstadoEntry.SQL_CREATE);
        db.execSQL(EnvioEntry.SQL_CREATE);
        db.execSQL(RetiroEntry.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(EstadoEntry.SQL_DELETE);
        db.execSQL(EnvioEntry.SQL_DELETE);
        db.execSQL(RetiroEntry.SQL_DELETE);
        onCreate(db);
    }
}
