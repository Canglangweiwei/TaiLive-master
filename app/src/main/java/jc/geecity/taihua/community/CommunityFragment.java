package jc.geecity.taihua.community;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aspsine.irecyclerview.gallery.BaseLoopGallery;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.jaydenxiao.common.gridimageview.AspectImageView;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jc.geecity.taihua.R;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseFragment;
import jc.geecity.taihua.home.bean.TopAdBean;

/**
 * 社区
 */
public class CommunityFragment extends AbsBaseFragment {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.alyLoopGallery)
    BaseLoopGallery alyLoopGallery;
    @Bind(R.id.textView)
    TextView mTv;

    private List<TopAdBean> imgUrls;

    public static CommunityFragment newInstance(String title) {
        CommunityFragment homeFragment = new CommunityFragment();
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
        return R.layout.fragment_home_community;
    }

    @Override
    protected void initUi() {
        ntb.setTitleText("社区");
        ntb.setBackVisibility(false);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mTv.setText(title);
    }

    @Override
    protected void initDatas() {
        List<String> datas = new ArrayList<>();
        datas.add("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg");
        datas.add("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg");
        datas.add("http://news.k618.cn/tech/201604/W020160407281077548026.jpg");
        datas.add("http://www.kejik.com/image/1460343965520.jpg");
        datas.add("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg");
        datas.add("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg");
        datas.add("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg");
        datas.add("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg");

        alyLoopGallery.setDatasAndLayoutId(datas, R.layout.griditem_image, new BaseLoopGallery.BindDataListener<String>() {

            @Override
            public void onBindData(ViewHolder holder, final String data) {
                AspectImageView imageView = holder.getView(R.id.asp_grid_item_image);
//                Picasso.with(holder.itemView.getContext())
//                        .load(data)
//                        .into(imageView);
                ImageLoaderUtils.display(holder.itemView.getContext(), imageView, data);
                imageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ToastUitl.showShort(data);
                    }
                });
            }
        });
    }

    @Override
    protected void initListener() {

    }
}
