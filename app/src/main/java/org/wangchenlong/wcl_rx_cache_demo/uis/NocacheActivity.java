package org.wangchenlong.wcl_rx_cache_demo.uis;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import org.wangchenlong.wcl_rx_cache_demo.R;
import org.wangchenlong.wcl_rx_cache_demo.RcApplication;
import org.wangchenlong.wcl_rx_cache_demo.networks.GitHubClient;
import org.wangchenlong.wcl_rx_cache_demo.data.Repo;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 无缓存Activity
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class NocacheActivity extends Activity {

    @Bind(R.id.nocache_rv_list) RecyclerView mRvList;
    @Bind(R.id.nocache_pb_progress) ProgressBar mPbProgress;

    @Inject Application mApplication;
    @Inject GitHubClient mGitHubClient;

    private ListAdapter mListAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nocache);
        ButterKnife.bind(this);

        ((RcApplication) getApplication()).getApiComponent().inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mApplication);
        mRvList.setLayoutManager(layoutManager);

        mListAdapter = new ListAdapter();
        mRvList.setAdapter(mListAdapter);
    }

    @Override protected void onResume() {
        super.onResume();

        // 延迟3秒, 模拟网络较差的效果
        mGitHubClient.getRepos("SpikeKing")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        mPbProgress.setVisibility(View.VISIBLE);
    }

    private void onSuccess(ArrayList<Repo> repos) {
        mListAdapter.setRepos(repos);
        mPbProgress.setVisibility(View.INVISIBLE);
    }

    private void onError(Throwable throwable) {
        mPbProgress.setVisibility(View.INVISIBLE);
    }
}
