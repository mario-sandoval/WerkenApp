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
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.data.model.EmpresaVo;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.data.model.RetiroVo;

public class RetiroDbHelper extends DbHelper<RetiroVo>{

    private final String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ( {1} INTEGER PRIMARY KEY, {2} INTEGER, {3} DATE, {4} TEXT, {5} TEXT, {6} TEXT, {7} TEXT, {8} TEXT, {9} INTEGER)",
            TABLE_NAME, _ID, EMPRESA, FECHA, DIRECCION, COMUNA, CONTACTO, FONO, OBSERVACION, MENSAJERO);
    private final String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    private final String SORT_ORDER = MessageFormat.format("{0} ASC",CODIGO);
    private static final String TABLE_NAME = "estado";

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

    public RetiroDbHelper(@Nullable Context context) {
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
    public List<RetiroVo> getFilterRecords(String query) {
        // Filter results WHERE "id" = id
        String selection = CONTACTO + " LIKE ? OR " + DIRECCION + " LIKE ? " ;
        String[] selectionArgs = { "%"+query+"%"};

        Cursor cursor = sqlDb.query(TABLE_NAME, projection, selection, selectionArgs, null, null, SORT_ORDER);
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
