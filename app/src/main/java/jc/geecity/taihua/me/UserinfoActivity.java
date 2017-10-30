package jc.geecity.taihua.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonutils.XgoLog;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuyh.library.imgsel.avatar.CameraSelectDialog;
import com.yuyh.library.imgsel.avatar.CircleImageView;
import com.yuyh.library.imgsel.avatar.PreviewActivity;
import com.yuyh.library.imgsel.avatar.SDPathUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import jc.geecity.taihua.R;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseActivity;

public class UserinfoActivity extends AbsBaseActivity
        implements CameraSelectDialog.OnCameraSelectListener {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 222;

    private static final int REQUEST_GALLERY_CODE = 1;      // 相册
    private static final int REQUEST_CAMERA_CODE = 2;       // 相机
    private static final int REQUEST_ZOOM_CODE = 3;         // 裁剪

    @Bind(R.id.iv_head)
    CircleImageView ivHeadLogo;

    private CameraSelectDialog dialog;

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void parseIntent() {

    }

    @Override
    protected void setupComponent(AbsAppComponent component) {

    }

    @Override
    protected void initUi() {
        ntb.setTitleText("用户信息");
        dialog = new CameraSelectDialog(this);
        dialog.setListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 打开对话框
     */
    @OnClick({R.id.iv_head})
    void onClick() {
        if (!isFinishing()) {
            dialog.show();
        }
    }

    @Override
    public void onCamera() {
        // 打开相机
        if (ContextCompat.checkSelfPermission(UserinfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserinfoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else if (ContextCompat.checkSelfPermission(UserinfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserinfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else if (ContextCompat.checkSelfPermission(UserinfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserinfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onPhoto() {
        // 打开相册
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_GALLERY_CODE);
    }

    /**
     * 打开相机
     */
    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDPathUtils.getCachePath(), "temp.jpg")));
            startActivityForResult(openCameraIntent, REQUEST_CAMERA_CODE);
        } else {
            Uri imageUri = FileProvider.getUriForFile(UserinfoActivity.this, "com.camera_photos.fileprovider",
                    new File(SDPathUtils.getCachePath(), "temp.jpg"));
            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(openCameraIntent, REQUEST_CAMERA_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 申请成功，可以拍照
                takePhoto();
            } else {
                ToastUitl.showShort("CAMERA PERMISSION DENIED");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && data != null) {
            startPhotoZoom(data.getData());
        } else if (requestCode == REQUEST_CAMERA_CODE) {
            File temp = new File(SDPathUtils.getCachePath(), "temp.jpg");
            // 裁剪图片
            startPhotoZoom(Uri.fromFile(temp));
        } else if (requestCode == REQUEST_ZOOM_CODE) {
            if (data != null) {
                setPicToView(data);
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent(UserinfoActivity.this, PreviewActivity.class);
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, REQUEST_ZOOM_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void setPicToView(Intent picdata) {
        Bitmap bitmap;
        byte[] bis = picdata.getByteArrayExtra("bitmap");
        bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        String localImg = System.currentTimeMillis() + ".JPEG";
        if (bitmap != null) {
            SDPathUtils.saveBitmap(bitmap, localImg);
            XgoLog.loge("本地图片绑定", SDPathUtils.getCachePath() + localImg);
            setImageUrl(ivHeadLogo, "file:/" + SDPathUtils.getCachePath() + localImg, R.drawable.no_content_tip);
        }
    }

    private DisplayImageOptions options;

    /**
     * 加载图片
     */
    private void setImageUrl(ImageView imageView, String imageUrl, int emptyImgId) {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(emptyImgId)
                    .showImageForEmptyUri(emptyImgId)
                    .showImageOnFail(emptyImgId)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        XgoLog.logd("图片地址：" + imageUrl);
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }
}
