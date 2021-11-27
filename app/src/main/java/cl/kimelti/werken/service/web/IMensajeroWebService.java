package cl.kimelti.werken.service.web;

import cl.kimelti.werken.data.model.MensajeroVo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IMensajeroWebService {

    @GET("/WerkenWebService/web/mensajeroservice/isAuthorized")
    Call<Boolean> isAuthorized(@Header("authorization") String auth);

    @GET("/WerkenWebService/web/mensajeroservice/mensajero")
    Call<MensajeroVo> getMensajero(@Header("authorization") String auth);
}
