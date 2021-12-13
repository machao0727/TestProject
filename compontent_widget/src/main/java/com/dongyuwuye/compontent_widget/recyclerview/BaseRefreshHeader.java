package com.dongyuwuye.compontent_widget.recyclerview;

/**
 * BaseRefreshHeader
 *
 * @author mc
 * create at 2017/9/19 16:11
 */
interface BaseRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();

}