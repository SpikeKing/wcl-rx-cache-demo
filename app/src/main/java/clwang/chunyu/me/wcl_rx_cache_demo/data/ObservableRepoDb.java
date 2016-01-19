package clwang.chunyu.me.wcl_rx_cache_demo.data;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Redo的观察者
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class ObservableRepoDb {
    private PublishSubject<ArrayList<Repo>> mPublishSubject; // 发表主题
    private RepoDbHelper mDbHelper; // 数据库

    public ObservableRepoDb(Context context) {
        mDbHelper = new RepoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    // 返回观察者
    public Observable<ArrayList<Repo>> getObservable() {
        Observable<ArrayList<Repo>> firstObservable = Observable.fromCallable(this::getRepoList);
        return firstObservable.concatWith(mPublishSubject); // 连接发表主题
    }

    // 从数据库获得数据
    private ArrayList<Repo> getRepoList() {
        mDbHelper.openForRead();
        ArrayList<Repo> repos = new ArrayList<>();
        Cursor c = mDbHelper.getAllRepo();
        if (!c.moveToFirst()) {
            return repos; // 返回空
        }

        do {
            // 添加数据
            repos.add(new Repo(
                    c.getString(RepoDbHelper.REPO_ID_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_NAME_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_DESCRIPTION_COLUMN_POSITION),
                    new Repo.Owner(c.getString(RepoDbHelper.REPO_OWNER_COLUMN_POSITION), "", "", "")));
        } while (c.moveToNext());
        c.close();
        mDbHelper.close();
        return repos;
    }

    // 插入Repo列表
    public void insertRepoList(ArrayList<Repo> repos) {
        mDbHelper.open();
        mDbHelper.removeAllRepo();
        for (Repo repo : repos) {
            mDbHelper.addRepo(
                    repo.getId(),
                    repo.getName(),
                    repo.getDescription(),
                    repo.getOwner().getLogin()
            );
        }
        mDbHelper.close();
        mPublishSubject.onNext(repos); // 会调用更新数据
    }
}
