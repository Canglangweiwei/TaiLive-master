package com.library.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * 用法：
 * private final static int PAGE_REQUEST_CODE = 1001;
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
            // 扫描条形码和二维码
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
 *
 *
 * final ImageView image = (ImageView) findViewById(R.id.imageView);
 * try {
        Bitmap bitmap = ZxingUtil.getInstance().encodeAsBitmap(getActivity(), "http://0.89892528.com");
        imageView.setImageBitmap(bitmap);
   } catch (WriterException e) {
        e.printStackTrace();
   }
 *
 * xml:
 * <ImageView
     android:id="@+id/imageView"
     android:layout_width="220dp"
     android:layout_height="220dp"
     android:layout_gravity="center_horizontal"
     android:contentDescription="@string/app_name" />
 */

/**
 * 扫描条形码、二维码，将字符串编码成二维码图片的工具类
 * Created by Zero on 2016/2/25.
 */
@SuppressWarnings("ALL")
public class ZxingUtil {

    private static ZxingUtil zxingUtil;

    private ZxingUtil() {
        super();
    }

    public static ZxingUtil getInstance() {
        if (zxingUtil == null) {
            zxingUtil = new ZxingUtil();
        }
        return zxingUtil;
    }

    /**
     * 将文字编码成二维码图片
     */
    public Bitmap encodeAsBitmap(Context context, String contents) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = null;
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                encoding = "UTF-8";
                break;
            }
        }
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int displayWidth = displaySize.x;
        int displayHeight = displaySize.y;
        int smallerDimension = displayWidth < displayHeight ? displayWidth : displayHeight;
        smallerDimension = smallerDimension * 7 / 8;

        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 扫描条形码和二维码
     */
    public void decode(Activity mActivity, int requestCode) {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        mActivity.startActivityForResult(intent, requestCode);
    }
}
