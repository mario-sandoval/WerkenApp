package cl.kimelti.werken.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.OrdenVo;

public class OrdenSerializer implements JsonSerializer<OrdenVo> {
    @Override
    public JsonElement serialize(OrdenVo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",src.getId());
        return jsonObject;
    }
}
