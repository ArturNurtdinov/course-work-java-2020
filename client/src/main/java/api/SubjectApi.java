package api;

import io.reactivex.Observable;
import model.Subject;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface SubjectApi {
    @GET("subjects")
    Observable<Response<List<Subject>>> getSubjects(@Header("Authorization") String token);

    @GET("subjects/{id}")
    Observable<Response<Subject>> getSubjectById(@Header("Authorization") String token, @Path("id") long id);

    @PUT("subjects/{id}")
    Observable<Response<Void>> updateSubject(@Header("Authorization") String token, @Path("id") long id, @Body Subject subject);

    @POST("subjects")
    Observable<Response<Subject>> addSubject(@Header("Authorization") String token, @Body Subject subject);

    @DELETE("subjects/{id}")
    Observable<Response<Void>> deleteSubjectById(@Header("Authorization") String token, @Path("id") long id);
}
