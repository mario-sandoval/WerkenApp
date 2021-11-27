package cl.kimelti.werken.service.web;

import java.util.List;

import cl.kimelti.werken.data.model.EnvioVo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IEnviosWebService {

    @GET("/WerkenWebService/web/envioservice/envio/id/{id}")
    Call<EnvioVo> getEnvioById(@Path("id") int id, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/folio/{folio}")
    Call<EnvioVo> getEnvioByFolio(@Path("folio") int id, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/ot/{ot}")
    Call<List<EnvioVo>> getEnviosByOt(@Path("ot") int ot, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/nombre/{nombre}")
    Call<List<EnvioVo>> getEnviosByNombre(@Path("nombre") String nombre, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/direccion/{direccion}")
    Call<List<EnvioVo>> getEnviosByDireccion(@Path("direccion") String nombre, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/search/{searchedText}")
    Call<List<EnvioVo>> getEnviosByText(@Path("searchedText") String searchedText, @Header("authorization") String authString);

    @GET("/WerkenWebService/web/envioservice/envio/mensajero/{mensajeroId}")
    Call<List<EnvioVo>> getEnviosByMensajero(@Path("mensajeroId") int mensajeroId, @Header("authorization") String authString);

    @PUT("/WerkenWebService/web/envioservice/envio/")
    Call<EnvioVo> updateEnvio(@Body EnvioVo envio, @Header("authorization") String authString);

    @PUT("/WerkenWebService/web/envioservice/envio/")
    Call<List<EnvioVo>> updateEnvios(@Body List<EnvioVo> envios, @Header("authorization") String authString);
}
