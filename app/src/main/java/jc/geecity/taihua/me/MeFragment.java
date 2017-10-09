package jc.geecity.taihua.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.irecyclerview.widget.CustomPtrHeader;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jc.geecity.taihua.R;
import jc.geecity.taihua.adapter.ArtRecycleViewAdapter;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.test.DataUtil;

/**
 * 我的
 */
@SuppressWarnings("ALL")
public class MeFragment extends AbsBaseFragment {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;

    private ArtRecycleViewAdapter recyclerAdapter;

    private int page = 1;
    private boolean isRefresh;
    private boolean hasMore = true;
    private int lastVisibleItem;

    public static MeFragment newInstance(String title) {
        MeFragment homeFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("我的");
        ntb.setBackVisibility(false);
    }

    @Override
    protected void initDatas() {
        initList();
    }

    /**
     *
     */
    private void initList() {
        CustomPtrHeader header = new CustomPtrHeader(getActivity(), 1);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                isRefresh = true;
                hasMore = true;
                mHandler.sendEmptyMessageDelayed(CODE_UPT, 3000);
            }
        });
        mPtrFrame.setEnabledNextPtrAtOnce(true);
        mPtrFrame.setOffsetToRefresh(200);
        mPtrFrame.autoRefresh(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);

        recyclerAdapter = new ArtRecycleViewAdapter(getActivity());
        recycleView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        GridLayoutManager mLayoutMgr = new GridLayoutManager(getActivity(), 2);
        recycleView.setLayoutManager(mLayoutMgr);
        recycleView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && !isRefresh
                        && hasMore
                        && (lastVisibleItem + 1 == recyclerAdapter.getItemCount())) {
                    if (page <= 3) {
                        mHandler.sendEmptyMessageDelayed(CODE_UPT, 3000);
                    } else if (page == 5) {
                        mHandler.sendEmptyMessageDelayed(CODE_ON_FAILURE, 3000);
                    } else {
                        mHandler.sendEmptyMessageDelayed(CODE_NO_MORE, 3000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    private final int CODE_UPT = 1;
    private final int CODE_NO_MORE = 2;
    private final int CODE_ON_FAILURE = 3;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_UPT:
                    mPtrFrame.refreshComplete();
                    page++;
                    if (isRefresh) {
                        recyclerAdapter.setHasMore(true);
                        recyclerAdapter.setError(false);
                        isRefresh = false;
                        recyclerAdapter.reset(DataUtil.createItemList());
                    } else {
                        recyclerAdapter.addArtList(DataUtil.createItemList());
                    }
                    break;
                case CODE_NO_MORE:
                    hasMore = false;
                    if (!isRefresh) {
                        // 显示没有更多
                        recyclerAdapter.setHasMore(false);
                        recyclerAdapter.notifyItemChanged(recyclerAdapter.getItemCount() - 1);
                    }
                    break;
                case CODE_ON_FAILURE:
                    if (!isRefresh) {
                        // 显示失败
                        recyclerAdapter.setError(true);
                        recyclerAdapter.notifyItemChanged(recyclerAdapter.getItemCount() - 1);
                    } else {
                        ToastUitl.showShort("----- (>_<) ----- 刷新失败");
                    }
                    break;
            }
        }
    };
}
