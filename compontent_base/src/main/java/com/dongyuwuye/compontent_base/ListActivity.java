package com.dongyuwuye.compontent_base;

import android.os.Bundle;

import android.view.View;


import com.dongyuwuye.compontent_widget.R2;
import com.dongyuwuye.compontent_widget.recyclerview.MRecyclerView;
import com.dongyuwuye.compontent_widget.recyclerview.StaggeredGridLayoutFixManager;
import com.dongyuwuye.compontent_widget.state.StateLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.OrientationHelper;
import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

public abstract class ListActivity extends BaseActivity implements IListView, MRecyclerView.LoadingListener{

    protected MRecyclerView mRecyclerView;
    protected IBasePresenter presenter;
    protected MultiTypeAdapter mAdapter;
    protected List<Object> dataItems;
    protected StaggeredGridLayoutFixManager manager;

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycler_view);
        initStateLayoutEvent();
        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置瀑布模式
        manager = new StaggeredGridLayoutFixManager(getSpanCount(), OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setLoadingMoreEnabled(hasLoadMore());
        mRecyclerView.setPullRefreshEnabled(hasPullRefresh());

        //初始化数据
        if (dataItems == null) {
            dataItems = new ArrayList<>();
        }
        if (mAdapter == null) {
            mAdapter = getRegisteredAdapter();
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(this);
        if (needRefresh()){
            mStateLayout.showProgressView();
            presenter.refresh();
        }
    }

    private void initStateLayoutEvent() {
        mStateLayout.setErrorAndEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStateLayout.showProgressView();
                presenter.refresh();
            }
        });
    }

    protected boolean needRefresh(){
        return true;
    }

    @Override
    public void initData() {

    }

    public abstract boolean hasLoadMore();

    public abstract boolean hasPullRefresh();

    protected abstract MultiTypeAdapter getRegisteredAdapter();

    protected abstract int getSpanCount();

    public void complete(List<Object> data, boolean moreData) {
        dataItems.clear();
        dataItems.addAll(data);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();
        if (!moreData) {
            mRecyclerView.setNoMore(true);
        }
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void onLoadMore() {
        presenter.loadMore();
    }
}
