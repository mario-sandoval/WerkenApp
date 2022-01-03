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
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE_DESTINATARIO;
import static cl.kimelti.werken.data.database.DbConstants.NUMERO_TRACKING;
import static cl.kimelti.werken.data.database.DbConstants.ORDEN_TRABAJO;
import static cl.kimelti.werken.data.database.DbConstants.PARENTESCO;
import static cl.kimelti.werken.data.database.DbConstants.RECIBE_TITULAR;
import static cl.kimelti.werken.data.database.DbConstants.RECIBIDO_POR;
import static cl.kimelti.werken.data.database.DbConstants.TIPO;

import java.text.MessageFormat;

public interface EnvioEntry {

    final String TABLE_NAME = "envio";
    String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ( {1} INTEGER PRIMARY KEY, {2} INTEGER, {3} INTEGER, {4} TEXT, {5} TEXT, {6} TEXT, {7} TEXT, {8} TEXT, {9} DATE, {10} DATE, {11} TEXT, {12} INTEGER, {13} INTEGER, {14} TEXT, {15} TEXT, {16} TEXT, {17} INTEGER, {18} TEXT, {19} TEXT, {20} INTEGER, {21} INTEGER)",
            TABLE_NAME, _ID, FOLIO, ORDEN_TRABAJO, NUMERO_TRACKING, NOMBRE_DESTINATARIO, DIRECCION, COMUNA, FONO, FECHA_CREACION, FECHA_ENTREGA, COMENTARIO, ESTADO, EMPRESA, EMAIL_DESTINATARIO, CODIGO, TIPO, CANTIDAD_BULTOS, RECIBIDO_POR, PARENTESCO, RECIBE_TITULAR, MENSAJERO);
    String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    String SORT_ORDER = MessageFormat.format("{0} ASC",NOMBRE_DESTINATARIO);

}
