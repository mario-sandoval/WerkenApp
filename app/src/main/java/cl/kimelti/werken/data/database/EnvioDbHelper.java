package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.CANTIDAD_BULTOS;
import static cl.kimelti.werken.data.database.DbConstants.CODIGO;
import static cl.kimelti.werken.data.database.DbConstants.COMENTARIO;
import static cl.kimelti.werken.data.database.DbConstants.COMUNA;
import static cl.kimelti.werken.data.database.DbConstants.DIRECCION;
import static cl.kimelti.werken.data.database.DbConstants.EMAIL_DESTINATARIO;
import static cl.kimelti.werken.data.database.DbConstants.EMPRESA;
import static cl.kimelti.werken.data.database.DbConstants.ESTADO;
import static cl.kimelti.werken.data.database.DbConstants.FECHA_CREACION;
import static cl.kimelti.werken.data.database.DbConstants.FECHA_ENTREGA;
import static cl.kimelti.werken.data.database.DbConstants.FOLIO;
import static cl.kimelti.werken.data.database.DbConstants.FONO;
import static cl.kimelti.werken.data.database.DbConstants.MENSAJERO;
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE;
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE_DESTINATARIO;
import static cl.kimelti.werken.data.database.DbConstants.NUMERO_TRACKING;
import static cl.kimelti.werken.data.database.DbConstants.ORDEN_TRABAJO;
import static cl.kimelti.werken.data.database.DbConstants.PARENTESCO;
import static cl.kimelti.werken.data.database.DbConstants.RECIBE_TITULAR;
import static cl.kimelti.werken.data.database.DbConstants.RECIBIDO_POR;
import static cl.kimelti.werken.data.database.DbConstants.TIPO;

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
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.data.model.EstadoVo;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.data.model.OrdenVo;

public class EnvioDbHelper extends DbHelper<EnvioVo>{

    private final String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ( {1} INTEGER PRIMARY KEY, {2} INTEGER, {3} INTEGER, {4} TEXT, {5} TEXT), {6} TEXT, {7} TEXT, {8} TEXT, {9} DATE, {10} DATE, {11} TEXT, {12} INTEGER, {13} INTEGER, {14} TEXT, {15} TEXT, {16} TEXT, {17} INTEGER, {18} TEXT, {19} TEXT, {20} INTEGER, {21} INTEGER)",
            TABLE_NAME, _ID, FOLIO, ORDEN_TRABAJO, NUMERO_TRACKING, NOMBRE_DESTINATARIO, DIRECCION, COMUNA, FONO, FECHA_CREACION, FECHA_ENTREGA, COMENTARIO, ESTADO, EMPRESA, EMAIL_DESTINATARIO, CODIGO, TIPO, CANTIDAD_BULTOS, RECIBIDO_POR, PARENTESCO, RECIBE_TITULAR, MENSAJERO);
    private final String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    private final String SORT_ORDER = MessageFormat.format("{0} ASC",NOMBRE_DESTINATARIO);
    private static final String TABLE_NAME = "envio";

    private final String[] projection = {
            _ID,
            FOLIO,
            ORDEN_TRABAJO,
            NUMERO_TRACKING,
            NOMBRE_DESTINATARIO,
            DIRECCION,
            COMUNA,
            FONO,
            FECHA_CREACION,
            FECHA_ENTREGA,
            COMENTARIO,
            ESTADO,
            EMPRESA,
            EMAIL_DESTINATARIO,
            CODIGO,
            TIPO,
            CANTIDAD_BULTOS,
            RECIBIDO_POR,
            PARENTESCO,
            RECIBE_TITULAR,
            MENSAJERO
    };

    public EnvioDbHelper(@Nullable Context context) {
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
    public List<EnvioVo> getFilterRecords(String query) {

        // Filter results WHERE "id" = id
        String selection = NOMBRE + " LIKE ? ";
        String[] selectionArgs = { "%"+query+"%"};

        Cursor cursor = sqlDb.query(TABLE_NAME, projection, selection, selectionArgs, null, null, SORT_ORDER);
        ArrayList<EnvioVo> outList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                EnvioVo vo = buildVo(cursor);
                outList.add(vo);
            }
        }
        cursor.close();
        return outList;
    }

    @Override
    public EnvioVo buildVo(Cursor cursor) {
        EnvioVo vo = new EnvioVo();
        if(cursor != null) {
            vo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            vo.setFolio(cursor.getInt(cursor.getColumnIndexOrThrow(FOLIO)));

            OrdenVo orden = new OrdenVo(cursor.getInt(cursor.getColumnIndexOrThrow(ORDEN_TRABAJO)));
            vo.setOrdenTrabajo(orden);

            vo.setNumeroTracking(cursor.getString(cursor.getColumnIndexOrThrow(NUMERO_TRACKING)));
            vo.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(CODIGO)));
            vo.setNombreDestinatario(cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_DESTINATARIO)));
            vo.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(DIRECCION)));
            vo.setComuna(cursor.getString(cursor.getColumnIndexOrThrow(COMUNA)));
            vo.setFono(cursor.getString(cursor.getColumnIndexOrThrow(FONO)));

            try {
                String fechaCreacion = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_CREACION));
                if(fechaCreacion != null){
                    vo.setFechaCreacion(dateFormat.parse(fechaCreacion));
                }

                String fechaEntrega = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_ENTREGA));
                if(fechaCreacion != null){
                    vo.setFechaEntrega(dateFormat.parse(fechaEntrega));
                }
            } catch (ParseException e) {
                Log.d("Error al parsear fecha ",e.getMessage());
            }

            vo.setComentario(cursor.getString(cursor.getColumnIndexOrThrow(COMENTARIO)));

            EstadoVo estado = new EstadoVo();
            estado.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ESTADO)));
            vo.setEstado(estado);

            EmpresaVo empresa = new EmpresaVo();
            empresa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(EMPRESA)));
            vo.setEmpresa(empresa);

            vo.setEmailDestinatario(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_DESTINATARIO)));
            vo.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(TIPO)));
            vo.setCantidadBultos(cursor.getInt(cursor.getColumnIndexOrThrow(CANTIDAD_BULTOS)));
            vo.setRecibidoPor(cursor.getString(cursor.getColumnIndexOrThrow(RECIBIDO_POR)));
            vo.setParentesco(cursor.getString(cursor.getColumnIndexOrThrow(PARENTESCO)));
            vo.setRecibeTitular(cursor.getInt(cursor.getColumnIndexOrThrow(RECIBE_TITULAR)) == 1);

            MensajeroVo mensajero = new MensajeroVo();
            mensajero.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MENSAJERO)));
            vo.setMensajero(mensajero);
        }

        return vo;
    }

    @Override
    public ContentValues getValues(EnvioVo vo) {
        ContentValues values = new ContentValues();
        values.put(_ID, vo.getId());
        values.put(FOLIO, vo.getFolio());
        values.put(ORDEN_TRABAJO, vo.getOrdenTrabajo().getId());
        values.put(CODIGO, vo.getCodigo());
        values.put(NUMERO_TRACKING,vo.getNumeroTracking());
        values.put(NOMBRE_DESTINATARIO,vo.getNombreDestinatario());
        values.put(DIRECCION,vo.getDireccion());
        values.put(COMUNA,vo.getComuna());
        values.put(FONO,vo.getFono());

        if(vo.getFechaCreacion()!= null){
            values.put(FECHA_CREACION,dateFormat.format(vo.getFechaCreacion()));
        }

        if(vo.getFechaEntrega() != null){
            values.put(FECHA_ENTREGA,dateFormat.format(vo.getFechaEntrega()));
        }
        values.put(COMENTARIO,vo.getComentario());
        values.put(ESTADO,vo.getEstado().getId());
        values.put(EMPRESA,vo.getEmpresa().getId());
        values.put(EMAIL_DESTINATARIO,vo.getEmailDestinatario());
        values.put(TIPO,vo.getTipo());
        values.put(CANTIDAD_BULTOS,vo.getCantidadBultos());
        values.put(RECIBIDO_POR,vo.getRecibidoPor());
        values.put(PARENTESCO,vo.getParentesco());
        values.put(RECIBE_TITULAR,vo.getRecibeTitular());
        values.put(MENSAJERO,vo.getMensajero().getId());

        return values;
    }

}
