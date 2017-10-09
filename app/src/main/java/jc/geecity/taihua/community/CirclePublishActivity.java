package jc.geecity.taihua.community;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NoScrollGridView;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import jc.geecity.taihua.R;
import jc.geecity.taihua.adapter.NinePicturesAdapter;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseActivity;


/**
 * <p>
 * 发表说说
 * </p>
 * Created by weiwei on 2017/07/16
 */
public class CirclePublishActivity extends AbsBaseActivity {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;

    private NinePicturesAdapter ninePicturesAdapter;
    private final int REQUEST_CODE = 120;

    /**
     * 启动入口
     *
     * @param context 上下文环境
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, CirclePublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_publish_zone;
    }

    @Override
    protected void parseIntent() {

    }

    @Override
    protected void setupComponent(AbsAppComponent component) {

    }

    @Override
    protected void initUi() {
        ntb.setTitleText(R.string.zone_publish_title);
        ninePicturesAdapter = new NinePicturesAdapter(this, 9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int position) {
                choosePhoto();
            }
        });
        gridview.setAdapter(ninePicturesAdapter);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_back, R.id.tv_save})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_save:// 发表
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    ToastUitl.showShort(getString(R.string.circle_publish_empty));
                    return;
                }
                if (ninePicturesAdapter.getPhotoCount() == 0) {
                    ToastUitl.showShort("请至少选择一张图片");
                    return;
                }
                ToastUitl.showShort("发表成功");
                finish();
                break;
        }
    }

    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // 确定按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                // 标题背景颜色
                .titleBgColor(ContextCompat.getColor(this, R.color.main_color))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this, R.color.main_color))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back)
                // 设置选择页面标题
                .title("图片")
                // 第一个是否显示相机
                .needCamera(true)
                // 是否需要裁剪
                .needCrop(true)
                // 最大选择图片数量
                .maxNum(9 - ninePicturesAdapter.getPhotoCount())
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    private ImageLoader loader = new ImageLoader() {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE:
                    List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                    if (ninePicturesAdapter != null) {
                        ninePicturesAdapter.addAll(pathList);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
