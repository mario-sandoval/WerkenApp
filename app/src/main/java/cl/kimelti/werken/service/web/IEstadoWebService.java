package cl.kimelti.werken.service.web;

import java.util.List;

import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.data.model.EstadoVo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IEstadoWebService {

    @GET("/WerkenWebService/web/estadoservice/estados")
    Call<List<EstadoVo>> getEstados(@Header("authorization") String authString);
}
