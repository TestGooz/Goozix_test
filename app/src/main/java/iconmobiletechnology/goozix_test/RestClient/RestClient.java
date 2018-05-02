package iconmobiletechnology.goozix_test.RestClient;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient extends Application {

    Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /*public void setMyVariable(String MyVariable) {
        OkHttpClient OkClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("Smantser", "specter17"))
                .build();
    }*/

    public void CreateAuthRetrofit(String user, String password) {
        OkHttpClient OkClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(user, password))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(OkClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService getService() {
        if (retrofit != null) return retrofit.create(ApiService.class);
        else return null;
    }
}
