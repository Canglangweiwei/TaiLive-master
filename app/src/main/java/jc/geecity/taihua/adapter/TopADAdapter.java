package jc.geecity.taihua.adapter;

/**
 * <p>
 * 头部广告适配器
 * </p>
 * Created by weiwei on 17/10/20.
 */
@SuppressWarnings("ALL")
public class TopADAdapter /*extends InfinitePagerAdapter*/ {

//    private Context mContext;
//    private LayoutInflater mInflater;
//    private List<TopAdBean> topAds;
//    private OnClickEveryAdListener onClickEveryAdListener;
//
//    public TopADAdapter(Context context, OnClickEveryAdListener l) {
//        this.mContext = context;
//        this.onClickEveryAdListener = l;
//        mInflater = LayoutInflater.from(mContext);
//    }
//
//    public void setDataList(List<TopAdBean> entities) {
//        this.topAds = entities;
//    }
//
//    @Override
//    public int getItemCount() {
//        return topAds == null ? 0 : topAds.size();
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup container) {
//        ViewHolder vh;
//        if (convertView != null) {
//            vh = (ViewHolder) convertView.getTag();
//        } else {
//            convertView = mInflater.inflate(R.layout.item_top_story, container, false);
//            vh = new ViewHolder(convertView);
//            convertView.setTag(vh);
//        }
//        if (topAds == null)
//            return convertView;
//
//        final TopAdBean entity = topAds.get(position);
//        vh.title.setText(entity.getTitle());
//        Picasso.with(mContext)
//                .load(entity.getImage())
//                .into(vh.img);
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onClickEveryAdListener != null) {
//                    onClickEveryAdListener.onClickEveryAD(position);
//                }
//            }
//        });
//        return convertView;
//    }
//
//    class ViewHolder {
//
//        @Bind(R.id.img_top_story)
//        ImageView img;
//        @Bind(R.id.tv_top_story_title)
//        TextView title;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
//
//    /**
//     * 单个广告接口
//     */
//    public interface OnClickEveryAdListener {
//        void onClickEveryAD(int idx);
//    }
}
