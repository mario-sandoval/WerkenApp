package cl.kimelti.werken.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.EmpresaVo;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.data.model.RetiroVo;
import cl.kimelti.werken.gson.DateDeserializer;
import cl.kimelti.werken.gson.EmpresaDeserializer;
import cl.kimelti.werken.gson.MensajeroDeserializer;
import cl.kimelti.werken.service.web.IRetiroWebService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetiroService {

    IRetiroWebService service;
    private static RetiroService instance = null;

    private RetiroService(){
        DateDeserializer dateDeserializer = new DateDeserializer();
        final Gson dateBuilder = new GsonBuilder().registerTypeAdapter(Date.class, dateDeserializer).create();

        MensajeroDeserializer mensajeroDeserializer = new MensajeroDeserializer();
        final Gson mensajeroBuilder = new GsonBuilder().registerTypeAdapter(MensajeroVo.class, mensajeroDeserializer).create();

        EmpresaDeserializer empresaDeserializer = new EmpresaDeserializer();
        final Gson empresaBuilder = new GsonBuilder().registerTypeAdapter(EmpresaVo.class, empresaDeserializer).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.getAppResources().getString(R.string.url_base)).addConverterFactory(GsonConverterFactory.create(dateBuilder)).addConverterFactory(GsonConverterFactory.create(mensajeroBuilder)).addConverterFactory(GsonConverterFactory.create(empresaBuilder)).build();
        service = retrofit.create(IRetiroWebService.class);
    }

    public static synchronized RetiroService getInstance(){
        if (instance == null) {
            instance = new RetiroService();
        }
        return instance;
    }

    public List<RetiroVo> getRetirosByMensajero() {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Integer mensajeroId = Integer.parseInt(PreferenceService.getInstance().getMessenger());
            Call<List<RetiroVo>> call = service.getRetirosByMensajero(mensajeroId,authStringEnc);

            Response<List<RetiroVo>> response = call.execute();
            List<RetiroVo> retiros = response.body();
            if(retiros == null){
                retiros = new ArrayList<RetiroVo>();
            }
            return retiros;

        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new ArrayList<RetiroVo>();
        }
    }

    public RetiroVo getRetiroById(Integer retiroId){
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Log.d("MensajeroService",authStringEnc);
            Call<RetiroVo> call = service.getRetiroById(retiroId,authStringEnc);
            Response<RetiroVo> response = call.execute();
            return response.body();
        } catch (Exception e) {
            Log.d("Retiro Service", e.getMessage());
            return new RetiroVo();
        }
    }

}
