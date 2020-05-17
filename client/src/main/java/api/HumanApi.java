package api;

import io.reactivex.Observable;
import model.Human;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface HumanApi {
    @GET("people")
    Observable<Response<List<Human>>> getPeople(@Header("Authorization") String token);

    @GET("people/{id}")
    Observable<Response<Human>> getHumanById(@Header("Authorization") String token, @Path("id") long id);

    @PUT("people/{id}")
    Observable<Response<Void>> updateHuman(@Header("Authorization") String token, @Path("id") long id, @Body Human human);

    @POST("people")
    Observable<Response<Human>> addHuman(@Header("Authorization") String token, @Body Human human);

    @DELETE("people/{id}")
    Observable<Response<Void>> deleteHuman(@Header("Authorization") String token, @Path("id") long id);
}
