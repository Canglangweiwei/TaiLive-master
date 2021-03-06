package jc.geecity.taihua.business;

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
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.home.bean.TopAdBean;
import jc.geecity.taihua.util.BannerGlideImageLoader;

/**
 * 商户
 */
public class BusinessFragment extends AbsBaseFragment implements OnBannerListener {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.businessFrg_banner)
    Banner mBanner;
    @Bind(R.id.textView)
    TextView mTv;

    private List<TopAdBean> imgUrls;

    public static BusinessFragment newInstance(String title) {
        BusinessFragment homeFragment = new BusinessFragment();
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
        return R.layout.fragment_home_business;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("商家");
        ntb.setBackVisibility(false);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mTv.setText(title);
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

        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);// 显示数字指示器
        mBanner.setImages(imgUrls)
                .setImageLoader(new BannerGlideImageLoader())
                .start();
    }

    @Override
    protected void initListener() {
        mBanner.setOnBannerListener(this);
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUitl.showShort("我点击了第" + (position + 1) + "广告：" + imgUrls.get(position).getTitle());
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 结束轮播
        mBanner.stopAutoPlay();
    }
}
