package jc.geecity.taihua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.baseadapter.AbsBaseArtRecycleViewAdapter;
import com.bumptech.glide.Glide;

import jc.geecity.taihua.R;
import jc.geecity.taihua.base.AbsBaseActivity;
import jc.geecity.taihua.me.UserinfoActivity;
import jc.geecity.taihua.test.RecyclerBean;

public class ArtRecycleViewAdapter extends AbsBaseArtRecycleViewAdapter<RecyclerBean> {

    public ArtRecycleViewAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int bindAbsViewLayoutResID() {
        return R.layout.griditem_image;
    }

    @Override
    protected RecyclerView.ViewHolder getAbsBindViewHolder(Context context, View view) {
        return new PhotoViewHolder(mContext, view);
    }

    @Override
    protected void bindAbsVHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoViewHolder) {
            ((PhotoViewHolder) holder).setData(get(position), position);
        }
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        private Context mCtx;
        private ImageView image;

        PhotoViewHolder(Context context, View itemView) {
            super(itemView);
            this.mCtx = context;
            image = (ImageView) itemView;
        }

        void setData(RecyclerBean bean, int position) {
            image.setTag(R.id.tag_item, position);
            Glide.with(mCtx)
                    .load(bean.getIcon())
                    .centerCrop()
                    .crossFade()
                    .dontAnimate()
                    .into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AbsBaseActivity) mCtx).startNextActivity(null, UserinfoActivity.class);
                }
            });
        }
    }
}
