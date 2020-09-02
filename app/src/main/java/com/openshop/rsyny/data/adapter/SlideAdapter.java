package com.openshop.rsyny.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import com.openshop.rsyny.R;
import com.openshop.rsyny.view.activity.WebViewActivity;
import com.openshop.rsyny.data.model.news.Article;


import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {
    Context context;
    public ArrayList<Article> data;

    LayoutInflater mLayoutInflater;

    public SlideAdapter(Context context , ArrayList<Article> data) {
        this.context = context;
        this.data=data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {

            View itemView = mLayoutInflater.inflate(R.layout.slide_item, container, false);

            ImageView imageView = itemView.findViewById(R.id.iv_slide_item);
            TextView tv_title = itemView.findViewById(R.id.iv_slide_title);

            Glide.with(context).load(data.get(position).getUrlToImage())
                    .into(imageView);
            tv_title.setText(data.get(position).getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent web_i = new Intent(context, WebViewActivity.class);
                    web_i.putExtra("url" , data.get(position).getUrl());
                    context.startActivity(web_i);
                }
            });


            container.addView(itemView);

            return itemView;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
