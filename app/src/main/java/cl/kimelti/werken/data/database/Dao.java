package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.DATABASE_NAME;
import static cl.kimelti.werken.data.database.DbConstants.DATABASE_VERSION;
import static cl.kimelti.werken.data.database.DbConstants.DATE_FORMAT_PATTERN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.kimelti.werken.data.model.AbstractVo;
import cl.kimelti.werken.data.model.EstadoVo;

public abstract class Dao<T extends AbstractVo> {

    protected SQLiteDatabase sqlDb;
    protected DateFormat dateFormat;


    public Dao(SQLiteDatabase sqlDb){
        this.sqlDb = sqlDb;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
    }

    /*
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("SQL Create:", getSqlCreate());
        db.execSQL(getSqlCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(getSqlDelete());
        onCreate(db);
    }
    */
    public void closeHelper(){
        this.sqlDb.close();
    }

    public abstract String getTableName();

    public abstract String[] getProjection();

    public abstract String getOrder();

    public long insert(T vo){
        ContentValues values = getValues(vo);

        return sqlDb.insert(getTableName(), null, values);
    }

    public void update(T vo){
        String whereClause = _ID + " = ? ";
        String[] whereArgs = { String.valueOf(vo.getId()) };
        // Create a new map of values, where column names are the keys
        ContentValues values = getValues(vo);

        sqlDb.update(getTableName(),values,whereClause,whereArgs);
    }

    public int delete(T vo){
        // Filter results WHERE "id" = id
        String whereClause = _ID + " = ?";
        String[] whereArgs = { String.valueOf(vo.getId()) };
        return sqlDb.delete(getTableName(), whereClause, whereArgs);
    }

    public List<T> getAllRecords(){
        Cursor cursor = sqlDb.query(getTableName(), getProjection(), null, null, null, null, getOrder());
        ArrayList<T> outList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {

                cursor.moveToNext();
                T vo = buildVo(cursor);
                outList.add(vo);
            }
        }
        cursor.close();
        return outList;
    }

    public void deleteAll(){
        sqlDb.delete(getTableName(), null, null);
    }

    public abstract List<T> getFilterRecords(String query);

    public T findById(int id){

        // Filter results WHERE "id" = id
        String selection = _ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };


        Cursor cursor = sqlDb.query(getTableName(), getProjection(), selection, selectionArgs, null, null, null);

        T vo = null;
        if(cursor.moveToNext()){
            vo = buildVo(cursor);
        }
        cursor.close();
        return vo;
    }

    public abstract T buildVo(Cursor cursor);

    public abstract ContentValues getValues(T vo);

}
