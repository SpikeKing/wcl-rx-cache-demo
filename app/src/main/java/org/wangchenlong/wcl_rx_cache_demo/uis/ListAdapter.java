package org.wangchenlong.wcl_rx_cache_demo.uis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import org.wangchenlong.wcl_rx_cache_demo.R;
import org.wangchenlong.wcl_rx_cache_demo.data.Repo;

/**
 * 适配器
 * <p>
 * Created by wangchenlong on 16/1/18.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Repo> mRepos;

    public ListAdapter() {
        mRepos = new ArrayList<>();
    }

    public void setRepos(ArrayList<Repo> repos) {
        mRepos = repos;
        notifyDataSetChanged();
    }

    @Override public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {
        holder.bindTo(mRepos.get(position));
    }

    @Override public int getItemCount() {
        return mRepos.size();
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_tv_repo_name) TextView mTvRepoName;
        @Bind(R.id.item_tv_repo_author) TextView mTvRepoAuthor;
        @Bind(R.id.item_tv_repo_desc) TextView mTvRepoDesc;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Repo repo) {
            mTvRepoName.setText(repo.getName());
            mTvRepoAuthor.setText(repo.getOwner().getLogin());
            mTvRepoDesc.setText(repo.getDescription());
        }
    }
}
