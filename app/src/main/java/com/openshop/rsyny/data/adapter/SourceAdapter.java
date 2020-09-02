package com.openshop.rsyny.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openshop.rsyny.view.activity.InternationalNews;
import com.openshop.rsyny.R;
import com.openshop.rsyny.data.model.SourceItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ProductViewHolder> {


    private Context context;
    private List<SourceItem> articleslist;
    InternationalNews activity;


    public SourceAdapter(List<SourceItem> articleslist, Context context, InternationalNews activity) {
        this.articleslist = articleslist;
        this.context = context;
        this.activity = activity;


    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.source_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.ivSource.setImageResource(articleslist.get(position).getImage());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.getNews(articleslist.get(position).getName());
            }
        });



    }



    @Override
    public int getItemCount() {
        return articleslist.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_source)
        ImageView ivSource;



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
