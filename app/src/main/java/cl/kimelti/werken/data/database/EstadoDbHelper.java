package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.CODIGO;
import static cl.kimelti.werken.data.database.DbConstants.DESCRIPCION;
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.data.model.EstadoVo;

public class EstadoDbHelper extends DbHelper<EstadoVo>{

    private final String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ({1} INTEGER PRIMARY KEY, {2} TEXT, {3} TEXT, {4} INTEGER)",TABLE_NAME, _ID, NOMBRE, DESCRIPCION, CODIGO);
    private final String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    private final String SORT_ORDER = MessageFormat.format("{0} ASC",CODIGO);
    private static final String TABLE_NAME = "estado";

    private final String[] projection = {
            _ID,
            NOMBRE,
            DESCRIPCION,
            CODIGO
    };

    public EstadoDbHelper(@Nullable Context context) {
        super(context);
    }

    @Override
    public String getSqlCreate() {
        return SQL_CREATE;
    }

    @Override
    public String getSqlDelete() {
        return SQL_DELETE;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getProjection() {
        return projection;
    }

    @Override
    public String getOrder() {
        return SORT_ORDER;
    }

    @Override
    public List<EstadoVo> getFilterRecords(String query) {
        // Filter results WHERE "id" = id
        String selection = NOMBRE + " LIKE ? ";
        String[] selectionArgs = { "%"+query+"%"};

        Cursor cursor = sqlDb.query(TABLE_NAME, projection, selection, selectionArgs, null, null, SORT_ORDER);
        ArrayList<EstadoVo> outList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                EstadoVo vo = buildVo(cursor);
                outList.add(vo);
            }
        }
        cursor.close();
        return outList;
    }

    @Override
    public EstadoVo buildVo(Cursor cursor) {
        EstadoVo vo = new EstadoVo();
        if(cursor != null) {
            vo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            vo.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE)));
            vo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION)));
            vo.setCodigo(cursor.getInt(cursor.getColumnIndexOrThrow(CODIGO)));
        }

        return vo;
    }

    @Override
    public ContentValues getValues(EstadoVo vo) {
        ContentValues values = new ContentValues();
        values.put(_ID, vo.getId());
        values.put(NOMBRE, vo.getNombre());
        values.put(DESCRIPCION, vo.getDescripcion());
        values.put(CODIGO, vo.getCodigo());
        return values;
    }
}
