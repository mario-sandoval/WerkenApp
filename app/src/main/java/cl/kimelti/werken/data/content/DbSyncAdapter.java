package cl.kimelti.werken.data.content;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import cl.kimelti.werken.data.task.DbAsyncTask;

public class DbSyncAdapter extends AbstractThreadedSyncAdapter {

    ContentResolver contentResolver;

    public DbSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    public DbSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
    }

    public DbSyncAdapter(Context context) {
        this(context, Boolean.TRUE);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try{
            //DbAsyncTask dbAsyncTask = new DbAsyncTask();
            //dbAsyncTask.execute();
        }catch(Exception ex) {
            Log.d("Error en onPerformSync",ex.getMessage());
        }
    }
}
