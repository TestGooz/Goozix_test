package iconmobiletechnology.goozix_test.RestClient;

import java.util.List;

import iconmobiletechnology.goozix_test.Model.AutorizationUserModel;
import iconmobiletechnology.goozix_test.Model.Repo;
import iconmobiletechnology.goozix_test.Model.UpdateUserBody;
import iconmobiletechnology.goozix_test.Model.User;
import iconmobiletechnology.goozix_test.Model.UserSearchItems;
import iconmobiletechnology.goozix_test.Model.UsersSearchModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/{user}/repos")
    Call<List<Repo>> getListRepos(@Path("user") String user);

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    @GET("/user/repos?per_page=100")
    Call<List<Repo>> listAuthRepos();

    @GET("/user")
    Call<AutorizationUserModel> AuthUser();

    @GET("/users?per_page=10")
    Call<List<UsersSearchModel>> getUsers(@Query("since") int since);

    @GET("/search/users")
    Call<UserSearchItems> getSearchUsers(@Query("q") String login);

    @PATCH("/user")
    Call<AutorizationUserModel> update(@Body UpdateUserBody registrationBody);
}
