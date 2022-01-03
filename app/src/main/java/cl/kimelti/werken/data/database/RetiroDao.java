package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.CODIGO;
import static cl.kimelti.werken.data.database.DbConstants.COMUNA;
import static cl.kimelti.werken.data.database.DbConstants.CONTACTO;
import static cl.kimelti.werken.data.database.DbConstants.DIRECCION;
import static cl.kimelti.werken.data.database.DbConstants.EMPRESA;
import static cl.kimelti.werken.data.database.DbConstants.FECHA;
import static cl.kimelti.werken.data.database.DbConstants.FONO;
import static cl.kimelti.werken.data.database.DbConstants.MENSAJERO;
import static cl.kimelti.werken.data.database.DbConstants.OBSERVACION;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.data.model.EmpresaVo;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.data.model.RetiroVo;

public class RetiroDao extends Dao<RetiroVo> {

    private final String[] projection = {
            _ID,
            EMPRESA,
            FECHA,
            DIRECCION,
            COMUNA,
            CONTACTO,
            FONO,
            OBSERVACION,
            MENSAJERO
    };

    public RetiroDao(SQLiteDatabase sqlDb) {
        super(sqlDb);
    }

    @Override
    public String getTableName() {
        return RetiroEntry.TABLE_NAME;
    }

    @Override
    public String[] getProjection() {
        return projection;
    }

    @Override
    public String getOrder() {
        return RetiroEntry.SORT_ORDER;
    }
    @Override
    public List<RetiroVo> getFilterRecords(String query) {
        // Filter results WHERE "id" = id
        String selection = CONTACTO + " LIKE ? OR " + DIRECCION + " LIKE ? " ;
        String[] selectionArgs = { "%"+query+"%"};

        Cursor cursor = sqlDb.query(getTableName(), getProjection(), selection, selectionArgs, null, null, getOrder());
        ArrayList<RetiroVo> outList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                RetiroVo vo = buildVo(cursor);
                outList.add(vo);
            }
        }
        cursor.close();
        return outList;
    }

    @Override
    public RetiroVo buildVo(Cursor cursor) {
        RetiroVo vo = new RetiroVo();
        if(cursor != null) {
            vo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

            EmpresaVo empresa = new EmpresaVo();
            empresa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(EMPRESA)));
            vo.setEmpresa(empresa);

            try {
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(FECHA));
                if(fecha != null){
                    vo.setFecha(dateFormat.parse(fecha));
                }
            } catch (ParseException e) {
                Log.d("Error al parsear fecha ",e.getMessage());
            }
            vo.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(DIRECCION)));
            vo.setComuna(cursor.getString(cursor.getColumnIndexOrThrow(COMUNA)));
            vo.setContacto(cursor.getString(cursor.getColumnIndexOrThrow(CONTACTO)));
            vo.setFono(cursor.getString(cursor.getColumnIndexOrThrow(FONO)));
            vo.setObservacion(cursor.getString(cursor.getColumnIndexOrThrow(OBSERVACION)));

            MensajeroVo mensajero = new MensajeroVo();
            mensajero.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MENSAJERO)));
            vo.setMensajero(mensajero);
        }

        return vo;
    }

    @Override
    public ContentValues getValues(RetiroVo vo) {
        ContentValues values = new ContentValues();
        values.put(_ID, vo.getId());
        values.put(EMPRESA, vo.getEmpresa().getId());
        if(vo.getFecha() != null){
            values.put(FECHA,dateFormat.format(vo.getFecha()));
        }
        values.put(DIRECCION, vo.getDireccion());
        values.put(COMUNA, vo.getComuna());
        values.put(CONTACTO, vo.getContacto());
        values.put(FONO, vo.getContacto());
        values.put(OBSERVACION, vo.getObservacion());
        values.put(MENSAJERO, vo.getMensajero().getId());

        return values;
    }

}
