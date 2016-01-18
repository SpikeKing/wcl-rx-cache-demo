package clwang.chunyu.me.wcl_rx_cache_demo.networks;

import java.util.ArrayList;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * 客户端
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class GitHubClient {

    private GitHubService mGitHubService;

    public GitHubClient() {
        mGitHubService = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GitHubService.class);
    }

    public Observable<ArrayList<Repo>> getRepos(String username) {
        return mGitHubService.listRepos(username);
    }
}
