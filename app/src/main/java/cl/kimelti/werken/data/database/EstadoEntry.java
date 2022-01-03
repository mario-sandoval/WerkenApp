package cl.kimelti.werken.data.database;

import static android.provider.BaseColumns._ID;
import static cl.kimelti.werken.data.database.DbConstants.CODIGO;
import static cl.kimelti.werken.data.database.DbConstants.DESCRIPCION;
import static cl.kimelti.werken.data.database.DbConstants.NOMBRE;

import java.text.MessageFormat;

public interface EstadoEntry {

    String TABLE_NAME = "estado";
    String SQL_CREATE = MessageFormat.format("CREATE TABLE {0} ({1} INTEGER PRIMARY KEY, {2} TEXT, {3} TEXT, {4} INTEGER)",TABLE_NAME, _ID, NOMBRE, DESCRIPCION, CODIGO);
    String SQL_DELETE = MessageFormat.format("DROP TABLE IF EXISTS {0}",TABLE_NAME);
    String SORT_ORDER = MessageFormat.format("{0} ASC",CODIGO);
}
