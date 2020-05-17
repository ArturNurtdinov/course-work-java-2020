package di;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class ApiModule {
    private static final String BASE_URL = "http://localhost:8080/api/";

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public GroupApi provideGroupApi(Retrofit retrofit) {
        return retrofit.create(GroupApi.class);
    }

    @Provides
    @Singleton
    public HumanApi provideHumanApi(Retrofit retrofit) {
        return retrofit.create(HumanApi.class);
    }

    @Provides
    @Singleton
    public SubjectApi provideSubjectApi(Retrofit retrofit) {
        return retrofit.create(SubjectApi.class);
    }

    @Provides
    @Singleton
    public MarkApi provideMarkApi(Retrofit retrofit) {
        return retrofit.create(MarkApi.class);
    }

    @Provides
    @Singleton
    public AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }
}
