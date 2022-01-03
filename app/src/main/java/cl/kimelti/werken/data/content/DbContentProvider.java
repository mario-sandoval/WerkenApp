package cl.kimelti.werken.data.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cl.kimelti.werken.data.database.DBOpenHelper;

public class DbContentProvider extends ContentProvider {

    private static final int ALL_ESTADOS = 1;
    private static final int ALL_ENVIOS = 2;
    private static final int ALL_RETIROS = 3;
    private static final String AUTHORITY = "cl.kimelti.werken.data.content.dbcontentprovider";
    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "estados", ALL_ESTADOS);
        uriMatcher.addURI(AUTHORITY, "envios", ALL_ENVIOS);
        uriMatcher.addURI(AUTHORITY, "retiros", ALL_RETIROS);
    }

    @Override
    public boolean onCreate() {
        //DBOpenHelper.getInstance();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_ESTADOS:
                return "vnd.android.cursor.dir/"+AUTHORITY+".estados";
            case ALL_ENVIOS:
                //return "vnd.android.cursor.item/vnd.com.as400samplecode.contentprovider.countries";
                return "vnd.android.cursor.dir/"+AUTHORITY+".envios";
            case ALL_RETIROS:
                return "vnd.android.cursor.dir/"+AUTHORITY+".retiros";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
