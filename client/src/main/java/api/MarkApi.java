package api;

import io.reactivex.Observable;
import model.Mark;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface MarkApi {
    @GET("marks")
    Observable<Response<List<Mark>>> getMarks(@Header("Authorization") String token);

    @GET("marks/{id}")
    Observable<Response<Mark>> getMarkById(@Header("Authorization") String token, @Path("id") long id);

    @PUT("marks/{id}")
    Observable<Response<Void>> updateMark(@Header("Authorization") String token, @Path("id") long id, @Body Mark mark);

    @POST("marks")
    Observable<Response<Mark>> addMark(@Header("Authorization") String token, @Body Mark mark);

    @DELETE("marks/{id}")
    Observable<Response<Void>> deleteMark(@Header("Authorization") String token, @Path("id") long id);
}
