package cl.kimelti.werken.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.database.DBOpenHelper;
import cl.kimelti.werken.data.database.EstadoDao;
import cl.kimelti.werken.data.model.EstadoVo;
import cl.kimelti.werken.service.web.IEstadoWebService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstadoService {

    private IEstadoWebService service;
    private EstadoDao dbHelper;
    private static EstadoService instance;

    private EstadoService() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.getAppResources().getString(R.string.url_base)).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(IEstadoWebService.class);
        dbHelper = new EstadoDao(DBOpenHelper.getInstance().getWritableDatabase());
    }

    public static synchronized EstadoService getInstance(){
        if (instance == null) {
            instance = new EstadoService();
        }
        return instance;
    }

    public List<EstadoVo> getOnlineEstados() {
        try {
            String authStringEnc = PreferenceService.getInstance().getAuthString();
            Call<List<EstadoVo>> call = service.getEstados(authStringEnc);

            Response<List<EstadoVo>> response = call.execute();
            List<EstadoVo> estados = response.body();
            if(estados == null){
                estados = new ArrayList<>();
            }
            return estados;

        } catch (Exception e) {
            Log.d("Estado Service", e.getMessage());
            return new ArrayList<>();
        }
    }

    public void loadData(){
        try {
            List<EstadoVo> estados = getOnlineEstados();

            if (estados != null && estados.size() > 0) {
                dbHelper.deleteAll();
                for (EstadoVo estado : estados) {
                    dbHelper.insert(estado);
                }
            }
        } catch (Exception e){
            Log.d("Load Initial Data", e.getMessage());
        }
    }

    public List<EstadoVo> getLocalEstados(){
        return dbHelper.getAllRecords();
    }

    public void closeDbHelper(){
        dbHelper.closeHelper();
    }
}
