package cl.kimelti.werken.gson;

import static cl.kimelti.werken.data.database.DbConstants.DATE_FORMAT_PATTERN;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateSerializer implements JsonSerializer<Date> {

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        String dateAsString = dateFormat.format(src);
        //return src == null ? null : new JsonPrimitive(src.getTime());
        JsonElement jsonElement = src == null ? null : new JsonPrimitive(dateAsString);
        Log.d("DateSerializer ", String.valueOf(jsonElement));
        return jsonElement;
    }
}
