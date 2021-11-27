package cl.kimelti.werken.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.OrdenVo;

public class OrdenDeserializer implements JsonDeserializer<OrdenVo> {
    @Override
    public OrdenVo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return new OrdenVo(jsonObject.get("id").getAsInt());
    }
}
