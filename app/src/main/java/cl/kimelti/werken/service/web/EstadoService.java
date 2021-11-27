package cl.kimelti.werken.service.web;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.database.EstadoDbHelper;
import cl.kimelti.werken.data.model.EstadoVo;
import cl.kimelti.werken.service.PreferenceService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstadoService {

    private IEstadoWebService service;
    private static EstadoService instance;

    public EstadoService() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.getAppResources().getString(R.string.url_base)).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(IEstadoWebService.class);
    }

    public static synchronized EstadoService getInstance(){
        if (instance == null) {
            instance = new EstadoService();
        }
        return instance;
    }

    public List<EstadoVo> getEstados() {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Integer mensajeroId = Integer.parseInt(PreferenceService.getInstance().getMessenger());
            Call<List<EstadoVo>> call = service.getEstados(authStringEnc);

            Response<List<EstadoVo>> response = call.execute();
            List<EstadoVo> estados = response.body();
            if(estados == null){
                estados = new ArrayList<EstadoVo>();
            }
            return estados;

        } catch (Exception e) {
            Log.d("Estado Service", e.getMessage());
            return new ArrayList<EstadoVo>();
        }
    }

    public void loadData(){
        try {
            List<EstadoVo> estados = getEstados();

            if (estados != null && estados.size() > 0) {
                EstadoDbHelper dao = new EstadoDbHelper(App.getContext());
                dao.deleteAll();
                for (EstadoVo estado : estados) {
                    dao.insert(estado);
                }
                dao.closeHelper();
            }
        } catch (Exception e){
            Log.d("Load Initial Data", e.getMessage());
        }
    }
}
