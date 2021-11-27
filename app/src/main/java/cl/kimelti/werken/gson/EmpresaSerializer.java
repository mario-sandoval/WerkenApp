package cl.kimelti.werken.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import cl.kimelti.werken.data.model.EmpresaVo;

public class EmpresaSerializer implements JsonSerializer<EmpresaVo> {
    @Override
    public JsonElement serialize(EmpresaVo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",src.getId());
        jsonObject.addProperty("rut",src.getRut());
        jsonObject.addProperty("nombre",src.getNombre());
        jsonObject.addProperty("direccion",src.getDireccion());
        jsonObject.addProperty("comuna",src.getComuna());
        jsonObject.addProperty("fono",src.getFono());
        jsonObject.addProperty("email",src.getEmail());
        return jsonObject;
    }
}
