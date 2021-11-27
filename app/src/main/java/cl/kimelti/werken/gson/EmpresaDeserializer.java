package cl.kimelti.werken.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.EmpresaVo;

public class EmpresaDeserializer implements JsonDeserializer<EmpresaVo> {
    @Override
    public EmpresaVo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return new EmpresaVo(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("rut").getAsString(),
                jsonObject.get("nombre").getAsString(),
                jsonObject.get("direccion").getAsString(),
                jsonObject.get("comuna").getAsString(),
                jsonObject.get("fono").getAsString(),
                jsonObject.get("email").getAsString()
        );
    }
}
