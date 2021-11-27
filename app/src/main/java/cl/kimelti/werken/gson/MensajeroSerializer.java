package cl.kimelti.werken.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.EmpresaVo;
import cl.kimelti.werken.data.model.MensajeroVo;

public class MensajeroSerializer implements JsonSerializer<MensajeroVo> {
    @Override
    public JsonElement serialize(MensajeroVo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",src.getId());
        jsonObject.addProperty("nombre",src.getNombre());
        jsonObject.addProperty("password",src.getPassword());
        jsonObject.addProperty("login",src.getLogin());
        jsonObject.addProperty("fono",src.getFono());
        jsonObject.addProperty("admin",src.getAdmin());
        return jsonObject;
    }
}
