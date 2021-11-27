package cl.kimelti.werken.service.web;

import java.util.List;

import cl.kimelti.werken.data.model.RetiroVo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRetiroWebService {

    @GET("/WerkenWebService/web/retiroservice/retiro/mensajero/{mensajeroId}")
    Call<List<RetiroVo>> getRetirosByMensajero(@Path("mensajeroId") Integer mensajeroId, @Header("authorization") String auth);

    @GET("/WerkenWebService/web/retiroservice/retiro/id/{id}")
    Call<RetiroVo> getRetiroById(@Path("id") Integer id, @Header("authorization") String auth);

    @PUT("/WerkenWebService/web/retiroservice/retiro/")
    Call<RetiroVo> updateRetiro(RetiroVo retiro, @Header("authorization") String authString);

    @PUT("/WerkenWebService/web/retiroservice/retiro/")
    Call<List<RetiroVo>> updateRetiros(List<RetiroVo> retiros, @Header("authorization") String authString);

}
