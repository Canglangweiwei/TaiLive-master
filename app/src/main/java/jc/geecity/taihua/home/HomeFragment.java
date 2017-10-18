package jc.geecity.taihua.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.library.zxing.ZxingUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import jc.geecity.taihua.R;
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
    @Bind(R.id.imageView)
    ImageView imageView;

    private List<TopAdBean> imgUrls;

    public static HomeFragment newInstance(String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
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

    private final static int PAGE_REQUEST_CODE = 1001;
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 222;

    @OnClick({R.id.qdcode_scan})
    void click_qdcode_scan(View view) {
        // 权限申请
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            Toast.makeText(getActivity(), "跳转二维码扫描页面", Toast.LENGTH_SHORT).show();
            /**
             * 扫描条形码和二维码
             */
            ZxingUtil.getInstance().decode(getActivity(), PAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 申请成功，扫描条形码和二维码
                ZxingUtil.getInstance().decode(getActivity(), PAGE_REQUEST_CODE);
            } else {
                Toast.makeText(getActivity(), "相机权限被禁止", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            switch (requestCode) {
                case PAGE_REQUEST_CODE:
                    mTv03.setText(bundle.getString("scan_result"));
                    break;
            }
        }
    }

    @OnClick({R.id.create_qdcode_scan})
    void click_create_qdcode_scan(View view) {
        try {
            Bitmap bitmap = ZxingUtil.getInstance().encodeAsBitmap(getActivity(), "http://0.89892528.com");
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
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
