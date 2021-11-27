package cl.kimelti.werken.data.database;

public interface DbConstants {

    String DATABASE_NAME = "werken.db";
    int DATABASE_VERSION = 2;

    String NOMBRE = "nombre";
    String DESCRIPCION = "descripcion";
    String CODIGO = "codigo";

    String FOLIO = "folio";
    String ORDEN_TRABAJO = "orden_trabajo";
    String NUMERO_TRACKING = "numero_tracking";
    String NOMBRE_DESTINATARIO = "nombre_destinatario";
    String DIRECCION = "direccion";
    String COMUNA = "comuna";
    String FONO = "fono";
    String FECHA_CREACION = "fecha_creacion";
    String FECHA_ENTREGA = "fecha_entrega";
    String COMENTARIO = "comentario";
    String ESTADO = "estado";
    String EMPRESA = "empresa";
    String EMAIL_DESTINATARIO = "email_destinatario";
    String TIPO = "tipo";
    String CANTIDAD_BULTOS = "cantidad_bultos";
    String RECIBIDO_POR = "recibido_por";
    String PARENTESCO = "parentesco";
    String RECIBE_TITULAR = "recibe_titular";
    String MENSAJERO = "mensajero";

    String FECHA = "fecha";
    String CONTACTO ="contacto";
    String OBSERVACION = "observcion";

    String DATE_FORMAT_PATTERN = "dd/MM/yyyy HH:mm";
    String DATE_FORMAT_PATTERN_SHORT = "dd/MM/yyyy";
}
