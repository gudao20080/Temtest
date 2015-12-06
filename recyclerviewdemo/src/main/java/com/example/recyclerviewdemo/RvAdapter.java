package com.example.recyclerviewdemo;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-06 10:07
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private List<String> mImageUrls;

    public RvAdapter(List<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.image_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Uri uri = Uri.parse(mImageUrls.get(position));

        holder.mImageView.post(new Runnable() {
            @Override
            public void run() {
                int width = holder.itemView.getWidth();
                int perWidth = width / 3;
                Picasso.with(holder.mImageView.getContext()).load(uri).centerCrop().resize(perWidth, perWidth).into(holder.mImageView);
                holder.mSDV.setImageURI(uri);

            }
        });
//        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        Log.d("way", position + "");
//        holder.mSDV.setImageURI(uri);
    }


    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        SimpleDraweeView mSDV;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv);
            mSDV = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
        }
    }

}
