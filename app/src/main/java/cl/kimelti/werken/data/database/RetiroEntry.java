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

import java.text.MessageFormat;

public interface RetiroEntry {

    String TABLE_NAME = "retiro";
    String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ( {1} INTEGER PRIMARY KEY, {2} INTEGER, {3} DATE, {4} TEXT, {5} TEXT, {6} TEXT, {7} TEXT, {8} TEXT, {9} INTEGER)",
            TABLE_NAME, _ID, EMPRESA, FECHA, DIRECCION, COMUNA, CONTACTO, FONO, OBSERVACION, MENSAJERO);
    String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    String SORT_ORDER = MessageFormat.format("{0} ASC",CODIGO);
}
