package com.dongyuwuye.compontent_base;


import android.view.View;


import com.dongyuwuye.compontent_widget.recyclerview.MRecyclerView;
import com.dongyuwuye.compontent_widget.recyclerview.StaggeredGridLayoutFixManager;
import com.dongyuwuye.compontent_widget.state.StateLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.OrientationHelper;
import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * create by：mc on 2018/5/17 10:20
 * <p>
 * email：
 */

public abstract class ListFragment extends BaseFragment implements IListView, MRecyclerView.LoadingListener {

    protected StateLayout mStateLayout;

    protected MRecyclerView mRecyclerView;

    protected IBasePresenter presenter;

    protected MultiTypeAdapter mAdapter;
    protected List<Object> dataItems;
    protected StaggeredGridLayoutFixManager manager;

    @Override
    public void initView() {
        mStateLayout = view.findViewById(R.id.state_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
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
        init();
        if (presenter != null&&needRefresh()) {
            mStateLayout.showProgressView();
            presenter.refresh();
        }
    }

    private void initStateLayoutEvent() {
        mStateLayout.setErrorAndEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    mStateLayout.showProgressView();
                    presenter.refresh();
                }
            }
        });
    }

    protected boolean needRefresh(){
        return true;
    }

    @Override
    public void load() {

    }

    public abstract void init();

    public abstract boolean hasLoadMore();

    public abstract boolean hasPullRefresh();

    protected abstract MultiTypeAdapter getRegisteredAdapter();

    protected abstract int getSpanCount();

    @Override
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
