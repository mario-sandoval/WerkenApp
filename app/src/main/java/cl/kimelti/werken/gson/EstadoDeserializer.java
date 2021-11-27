package cl.kimelti.werken.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.EstadoVo;

public class EstadoDeserializer implements JsonDeserializer<EstadoVo> {
    @Override
    public EstadoVo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return new EstadoVo(jsonObject.get("id").getAsInt(),
                jsonObject.get("nombre").getAsString(),
                jsonObject.get("descripcion").getAsString(),
                jsonObject.get("codigo").getAsInt());
    }
}
