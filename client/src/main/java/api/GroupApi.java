package api;

import io.reactivex.Observable;
import model.Group;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface GroupApi {
    @GET("groups")
    Observable<Response<List<Group>>> getGroups(@Header("Authorization") String token);

    @GET("groups/{id}")
    Observable<Response<Group>> getGroupById(@Header("Authorization") String token, @Path("id") long id);

    @PUT("groups/{id}")
    Observable<Response<Void>> updateGroup(@Header("Authorization") String token, @Path("id") long id, @Body Group group);

    @POST("groups")
    Observable<Response<Group>> addGroup(@Header("Authorization") String token, @Body Group group);

    @DELETE("groups/{id}")
    Observable<Response<Void>> deleteGroup(@Header("Authorization") String token, @Path("id") long id);
}
