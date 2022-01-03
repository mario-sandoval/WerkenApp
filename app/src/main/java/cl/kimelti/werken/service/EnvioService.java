package cl.kimelti.werken.service;

import static cl.kimelti.werken.data.database.DbConstants.DATE_FORMAT_PATTERN;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.EmpresaVo;
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.data.model.OrdenVo;
import cl.kimelti.werken.gson.DateDeserializer;
import cl.kimelti.werken.gson.DateSerializer;
import cl.kimelti.werken.gson.EmpresaDeserializer;
import cl.kimelti.werken.gson.EmpresaSerializer;
import cl.kimelti.werken.gson.MensajeroDeserializer;
import cl.kimelti.werken.gson.MensajeroSerializer;
import cl.kimelti.werken.gson.OrdenDeserializer;
import cl.kimelti.werken.gson.OrdenSerializer;
import cl.kimelti.werken.service.web.IEnviosWebService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnvioService {

    private IEnviosWebService service;
    private DateFormat dateFormat;
    private static EnvioService instance = null;

    private EnvioService(){
        DateDeserializer dateDeserializer = new DateDeserializer();
        final Gson dateDeserializerBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").registerTypeAdapter(Date.class, dateDeserializer).create();

        DateSerializer dateSerializer = new DateSerializer();
        final Gson dateSerializerBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").registerTypeAdapter(Date.class, dateSerializer).create();

        MensajeroDeserializer mensajeroDeserializer = new MensajeroDeserializer();
        final Gson mensajeroDeserializerBuilder = new GsonBuilder().registerTypeAdapter(MensajeroVo.class, mensajeroDeserializer).create();

        MensajeroSerializer mensajeroSerializer = new MensajeroSerializer();
        final Gson mensajeroSerializerBuilder = new GsonBuilder().registerTypeAdapter(MensajeroVo.class, mensajeroSerializer).create();

        EmpresaDeserializer empresaDeserializer = new EmpresaDeserializer();
        final Gson empresaDeserializerBuilder = new GsonBuilder().registerTypeAdapter(EmpresaVo.class, empresaDeserializer).create();

        EmpresaSerializer empresaSerializer = new EmpresaSerializer();
        final Gson empresaSerializerBuilder = new GsonBuilder().registerTypeAdapter(EmpresaVo.class, empresaSerializer).create();

        OrdenDeserializer ordenDeserializer = new OrdenDeserializer();
        final Gson ordenDeserializerBuilder = new GsonBuilder().registerTypeAdapter(OrdenVo.class, ordenDeserializer).create();

        OrdenSerializer ordenSerializer = new OrdenSerializer();
        final Gson ordenSerializerBuilder = new GsonBuilder().registerTypeAdapter(OrdenVo.class, ordenSerializer).create();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(App.getAppResources().getString(R.string.url_base))
                .addConverterFactory(GsonConverterFactory.create(dateDeserializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(dateSerializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(mensajeroDeserializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(mensajeroSerializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(empresaDeserializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(empresaSerializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(ordenDeserializerBuilder))
                .addConverterFactory(GsonConverterFactory.create(ordenSerializerBuilder))
                .build();
        service = retrofit.create(IEnviosWebService.class);

        this.dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
    }

    public static synchronized EnvioService getInstance(){
        if (instance == null) {
            instance = new EnvioService();
        }
        return instance;
    }

    public List<EnvioVo> getEnviosByOt(Integer ot) {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<List<EnvioVo>> call = service.getEnviosByOt(ot,authStringEnc);

            Response<List<EnvioVo>> response = call.execute();
            List<EnvioVo> envios = response.body();
            if(envios == null){
                envios = new ArrayList<EnvioVo>();
            }
            return envios;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<EnvioVo>();
        }
    }

    public EnvioVo getEnvioById(Integer id){
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<EnvioVo> call = service.getEnvioById(id,authStringEnc);
            Response<EnvioVo> response = call.execute();
            return response.body();
        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new EnvioVo();
        }
    }

    public EnvioVo getEnvioByFolio(Integer folio){
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<EnvioVo> call = service.getEnvioById(folio,authStringEnc);
            Response<EnvioVo> response = call.execute();
            return response.body();
        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new EnvioVo();
        }
    }

    public List<EnvioVo> getEnviosByNombre(String nombreDestinatario) {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<List<EnvioVo>> call = service.getEnviosByNombre(nombreDestinatario,authStringEnc);

            Response<List<EnvioVo>> response = call.execute();
            List<EnvioVo> envios = response.body();
            if(envios == null){
                envios = new ArrayList<EnvioVo>();
            }
            return envios;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<EnvioVo>();
        }
    }

    public List<EnvioVo> getEnviosByDireccion(String direccion) {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<List<EnvioVo>> call = service.getEnviosByNombre(direccion,authStringEnc);

            Response<List<EnvioVo>> response = call.execute();
            List<EnvioVo> envios = response.body();
            if(envios == null){
                envios = new ArrayList<EnvioVo>();
            }
            return envios;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<EnvioVo>();
        }
    }

    public List<EnvioVo> getEnviosByText(String searchedText) {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<List<EnvioVo>> call = service.getEnviosByText(searchedText,authStringEnc);

            Response<List<EnvioVo>> response = call.execute();
            List<EnvioVo> envios = response.body();
            if(envios == null){
                envios = new ArrayList<EnvioVo>();
            }
            return envios;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<EnvioVo>();
        }
    }public List<EnvioVo> getEnviosByMensajero() {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Integer mensajeroId = Integer.parseInt(PreferenceService.getInstance().getMessenger());
            Call<List<EnvioVo>> call = service.getEnviosByMensajero(mensajeroId,authStringEnc);

            Response<List<EnvioVo>> response = call.execute();
            List<EnvioVo> envios = response.body();
            if(envios == null){
                envios = new ArrayList<EnvioVo>();
            }
            return envios;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<EnvioVo>();
        }
    }

    public void updateEnvio(EnvioVo envio) throws IOException {
        String authStringEnc = PreferenceService.getInstance().getAuthString();
        Call<EnvioVo> call = service.updateEnvio(envio, authStringEnc);
        Response<EnvioVo> response = call.execute();
        Log.d("REsponse Update ",response.toString());
    }

}
