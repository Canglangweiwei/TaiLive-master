package jc.geecity.taihua.me;

import android.os.Bundle;
import android.widget.TextView;

import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.zanlabs.infinitevpager.InfiniteViewPager;
import com.zanlabs.infinitevpager.indicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jc.geecity.taihua.R;
import jc.geecity.taihua.adapter.TopADAdapter;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.home.bean.TopAdBean;

public class MeFragment extends AbsBaseFragment {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.view_pager)
    InfiniteViewPager viewPager;
    @Bind(R.id.indicator)
    LinePageIndicator indicator;
    @Bind(R.id.textView)
    TextView mTv;

    public static MeFragment newInstance(int number) {
        MeFragment homeFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
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

        Bundle bundle = getArguments();
        int number = bundle.getInt("number");
        mTv.setText(String.valueOf(number));
    }

    @Override
    protected void initDatas() {
        final List<TopAdBean> mList = new ArrayList<>();

        TopAdBean bean1 = new TopAdBean();
        bean1.setId(1);
        bean1.setTitle("大桶水");
        bean1.setImage("http://221.215.1.228:8001/HisenseUpload/ad_photo/201781610363506.jpg");

        TopAdBean bean2 = new TopAdBean();
        bean2.setId(2);
        bean2.setTitle("花街小镇");
        bean2.setImage("http://221.215.1.228:8001/HisenseUpload/ad_photo/201798163638278.jpg");

        TopAdBean bean3 = new TopAdBean();
        bean3.setId(3);
        bean3.setTitle("缴费活动");
        bean3.setImage("http://221.215.1.228:8001/HisenseUpload/ad_photo/2017915103118909.jpg");

        TopAdBean bean4 = new TopAdBean();
        bean4.setId(4);
        bean4.setTitle("家政");
        bean4.setImage("http://221.215.1.228:8001/HisenseUpload/ad_photo/2017816155632334.jpg");

        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);

        TopADAdapter topADAdapter = new TopADAdapter(getActivity(), new TopADAdapter.OnClickEveryAdListener() {

            @Override
            public void onClickEveryAD(int idx) {
                ToastUitl.showShort("点击了第" + (idx + 1) + "个广告：" + mList.get(idx).getTitle());
            }
        });
        topADAdapter.setDataList(mList);
        viewPager.setAdapter(topADAdapter);
        viewPager.setAutoScrollTime(3000);
        viewPager.startAutoScroll();
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewPager != null) {
            viewPager.startAutoScroll();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewPager != null) {
            viewPager.stopAutoScroll();
        }
    }
}
