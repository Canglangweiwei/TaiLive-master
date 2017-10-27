package jc.geecity.taihua.home;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import jc.geecity.taihua.R;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.home.bean.TopAdBean;
import jc.geecity.taihua.test.KeyValueModel;
import jc.geecity.taihua.test.TestJsonProtocol;
import jc.geecity.taihua.test.TestObjProtocol;
import jc.geecity.taihua.util.BannerGlideImageLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 首页
 */
public class HomeFragment extends AbsBaseFragment implements OnBannerListener {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.homeFrg_banner)
    Banner mBanner;
    @Bind(R.id.textView01)
    TextView mTv01;
    @Bind(R.id.textView02)
    TextView mTv02;
    @Bind(R.id.textView03)
    TextView mTv03;

    private List<TopAdBean> imgUrls;

    public static HomeFragment newInstance(String title) {
        HomeFragment homeFragment = new HomeFragment();
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
        return R.layout.fragment_home_home;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("首页");
        ntb.setBackVisibility(false);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mTv01.setText(title);
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

    @OnClick(R.id.rxjava_okhttp)
    void click_rxjava_okhttp() {
        TestJsonProtocol mTestProtocol = new TestJsonProtocol();
        mTestProtocol.test8989Date()
                .compose(this.<String>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String dataJson) {
                        Gson mGson = new Gson();
                        KeyValueModel model = mGson.fromJson(dataJson, KeyValueModel.class);
                        if (model == null)
                            return;
                        List<KeyValueModel.DataBean> modelList = model.getData();
                        if (modelList == null || modelList.size() == 0)
                            return;
                        StringBuilder builder = new StringBuilder();
                        builder.append(dataJson).append("\r\n");
                        for (KeyValueModel.DataBean bean : modelList)
                            builder.append(bean.toString()).append("\r\n");
                        mTv01.setText("rxjava_okhttp Result:\r\n" + builder.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mTv01.setText("rxjava_okhttp Error:\r\n" + throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.rxjava_okhttp_Gson)
    void click_rxjava_okhttp_gson() {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("id", 1);
        params.put("name", "小鱼儿");
        params.put("age", 18);
        params.put("gender", "男");
        params.put("addr", "青岛市市北区xxx路xxx大厦B座2001");
        params.put("telphone", "15376753304");
        TestObjProtocol mTestProtocol = new TestObjProtocol();
        mTestProtocol.test8989Date(params)
                .compose(this.<KeyValueModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<KeyValueModel>() {
                    @Override
                    public void call(KeyValueModel dataModel) {
                        if (dataModel == null)
                            return;
                        if (dataModel.getData() == null
                                || dataModel.getData().size() == 0)
                            return;
                        StringBuilder builder = new StringBuilder();
                        for (KeyValueModel.DataBean bean : dataModel.getData())
                            builder.append(bean.toString()).append("\r\n");
                        mTv02.setText("rxjava_okhttp_gson Result:\r\n" + builder.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mTv02.setText("rxjava_okhttp_gson Error:\r\n" + throwable.getMessage());
                    }
                });
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
