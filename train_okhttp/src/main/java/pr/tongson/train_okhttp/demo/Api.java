package pr.tongson.train_okhttp.demo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <b>Create Date:</b> 2020-03-03<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */

public interface Api {

    @GET("/users/{username}/repos")
    Single<List<Repos>> getRepos(@Path("username") String username);

}
