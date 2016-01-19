package clwang.chunyu.me.wcl_rx_cache_demo.uis;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import clwang.chunyu.me.wcl_rx_cache_demo.R;
import clwang.chunyu.me.wcl_rx_cache_demo.RcApplication;
import clwang.chunyu.me.wcl_rx_cache_demo.data.ObservableRepoDb;
import clwang.chunyu.me.wcl_rx_cache_demo.data.Repo;
import clwang.chunyu.me.wcl_rx_cache_demo.networks.GitHubClient;
import clwang.chunyu.me.wcl_rx_cache_demo.networks.GitHubService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 缓存Activity
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class CacheActivity extends Activity {

    @Bind(R.id.cache_rv_list) RecyclerView mRvList; // 列表
    @Bind(R.id.cache_srl_swipe) SwipeRefreshLayout mSrlSwipe; // 刷新

    @Inject Application mApplication;
    @Inject ObservableRepoDb mRepoDb;
    @Inject GitHubClient mGitHubClient;

    private ListAdapter mListAdapter; // RecyclerView适配器

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);

        // 注入类
        ((RcApplication) getApplication()).getApiComponent().inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mApplication);
        mRvList.setLayoutManager(layoutManager);

        mListAdapter = new ListAdapter();
        mRvList.setAdapter(mListAdapter);

        mSrlSwipe.setOnRefreshListener(this::fetchUpdates);
    }

    @Override protected void onResume() {
        super.onResume();
        mRepoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData);

        fetchUpdates();
        Toast.makeText(mApplication, "正在更新", Toast.LENGTH_SHORT).show();
    }

    // 设置数据, 更新完成会调用
    private void setData(ArrayList<Repo> repos) {
        mListAdapter.setRepos(repos);
        Toast.makeText(mApplication, "更新完成", Toast.LENGTH_SHORT).show();
    }

    private void fetchUpdates() {
        // 延迟3秒, 模拟效果
        mGitHubClient.getRepos("SpikeKing")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRepoDb::insertRepoList, this::fetchError, this::fetchComplete);
    }

    private void fetchError(Throwable throwable) {
        mSrlSwipe.setRefreshing(false);
    }

    private void fetchComplete() {
        mSrlSwipe.setRefreshing(false);
    }
}
