package cl.kimelti.werken.service;

import android.util.Log;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.service.web.IMensajeroWebService;
import cl.kimelti.werken.util.StringUtil;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MensajeroService {

    IMensajeroWebService service;
    private static MensajeroService instance = null;

    private MensajeroService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.getAppResources().getString(R.string.url_base)).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(IMensajeroWebService.class);
    }

    public static synchronized MensajeroService getInstance(){
        if (instance == null) {
            instance = new MensajeroService();
        }
        return instance;
    }

    public boolean isAuthorized(String login, String pass) {
        try {
            String authString = login + ":" + pass;
            String authStringEnc = StringUtil.encript(authString);

            Call<Boolean> call = service.isAuthorized(authStringEnc);

            Response<Boolean> response = call.execute();

            Boolean isValidCrp = response.body();

            return isValidCrp;

        } catch (Exception e) {
            Log.d("CRP Service", e.getMessage());
            return false;
        }
    }

    public MensajeroVo getMensajero(String login, String pass){
        try {
            String authString = StringUtil.concatenate(login, ":",pass);
            String authStringEnc = StringUtil.encript(authString);
            Log.d("MensajeroService",authStringEnc);
            Call<MensajeroVo> call = service.getMensajero(authStringEnc);
            Response<MensajeroVo> response = call.execute();
            return response.body();
        } catch (Exception e) {
            Log.d("CRP Service", e.getMessage());
            return null;
        }
    }
}
