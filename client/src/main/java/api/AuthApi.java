package api;

import io.reactivex.Observable;
import model.User;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/signin")
    Observable<Response<String>> singIn(@Body User user);
}
