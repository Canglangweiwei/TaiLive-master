package jc.geecity.taihua.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import jc.geecity.taihua.app.AbsAppComponent;

@SuppressWarnings("ALL")
public abstract class AbsBaseFragment extends RxFragment {

    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(AbsBaseApplication.get(getActivity()).component());
    }

    /**
     * Dagger2绑定
     *
     * @param component AppComponent
     */
    protected abstract void setupComponent(AbsAppComponent component);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(initContentView(), container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        initDatas();
        initListener();
    }

    /**
     * 页面绑定
     */
    public abstract int initContentView();

    /**
     * 初始化UI
     */
    protected abstract void initUi();

    /**
     * 初始化数据
     */
    protected abstract void initDatas();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
