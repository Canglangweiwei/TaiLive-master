package jc.geecity.taihua.home;

import android.os.Bundle;
import android.widget.TextView;

import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jc.geecity.taihua.R;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.home.bean.TopAdBean;
import jc.geecity.taihua.util.GlideImageLoader;

public class HomeFragment extends AbsBaseFragment implements OnBannerListener {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.main_banner)
    Banner mBanner;
    @Bind(R.id.textView)
    TextView mTv;

    private List<TopAdBean> imgUrls;

    public static HomeFragment newInstance(int number) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_home_home;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("首页");
        ntb.setBackVisibility(false);

        Bundle bundle = getArguments();
        int number = bundle.getInt("number");
        mTv.setText(String.valueOf(number));

        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(this);
    }

    @Override
    protected void initDatas() {
        imgUrls = new ArrayList<>();

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

        imgUrls.add(bean1);
        imgUrls.add(bean2);
        imgUrls.add(bean3);
        imgUrls.add(bean4);

        mBanner.setImages(imgUrls)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void OnBannerClick(int position) {
        ToastUitl.showShort("我点击了第" + (position + 1) + "广告：" + imgUrls.get(position).getTitle());
    }
}
