package jc.geecity.taihua.service;

import android.os.Bundle;
import android.widget.TextView;

import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import jc.geecity.taihua.R;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseFragment;

/**
 * 服务
 */
@SuppressWarnings("ALL")
public class ServiceFragment extends AbsBaseFragment {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;

    @Bind(R.id.textView)
    TextView mTv;

    public static ServiceFragment newInstance(String title) {
        ServiceFragment homeFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected void setupComponent(AbsAppComponent component) {

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_home_service;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("服务");
        ntb.setBackVisibility(false);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mTv.setText(title);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

    }
}
