package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.CODIGO;
import static cl.kimelti.werken.data.database.DbConstants.DESCRIPCION;
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.data.model.EstadoVo;

public class EstadoDao extends Dao<EstadoVo> {

    private final String[] projection = {
            _ID,
            NOMBRE,
            DESCRIPCION,
            CODIGO
    };

    public EstadoDao(SQLiteDatabase sqlDb) {
        super(sqlDb);
    }

    @Override
    public String getTableName() {
        return EstadoEntry.TABLE_NAME;
    }

    @Override
    public String[] getProjection() {
        return projection;
    }

    @Override
    public String getOrder() {
        return EstadoEntry.SORT_ORDER;
    }

    @Override
    public List<EstadoVo> getFilterRecords(String query) {
        // Filter results WHERE "id" = id
        String selection = NOMBRE + " LIKE ? ";
        String[] selectionArgs = { "%"+query+"%"};

        Cursor cursor = sqlDb.query(getTableName(), getProjection(), selection, selectionArgs, null, null, getOrder());
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
