package clwang.chunyu.me.wcl_rx_cache_demo.networks;

import java.util.ArrayList;

import clwang.chunyu.me.wcl_rx_cache_demo.data.Repo;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Github服务
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public interface GitHubService {
    String BASE_URL = "https://api.github.com";

    @GET("/users/{user}/repos") Observable<ArrayList<Repo>> getRepos(@Path("user") String user);
}
