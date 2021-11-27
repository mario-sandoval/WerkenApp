package cl.kimelti.werken.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.MensajeroVo;

public class MensajeroDeserializer implements JsonDeserializer<MensajeroVo> {

    @Override
    public MensajeroVo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return new MensajeroVo(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("nombre").getAsString(),
                jsonObject.get("password").getAsString(),
                jsonObject.get("login").getAsString(),
                jsonObject.get("fono").getAsString(),
                jsonObject.get("admin").getAsBoolean()
        );
    }
}
