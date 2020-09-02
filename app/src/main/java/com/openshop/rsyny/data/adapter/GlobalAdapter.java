package com.openshop.rsyny.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openshop.rsyny.view.activity.InternationalNews;
import com.openshop.rsyny.R;
import com.openshop.rsyny.view.activity.WebViewActivity;
import com.openshop.rsyny.data.model.international.Article;
import com.openshop.rsyny.data.local.room.NewItem;
import com.openshop.rsyny.data.local.room.RoomDao;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openshop.rsyny.data.local.HelperMethod.shareDialoge;
import static com.openshop.rsyny.data.local.room.RoomManger.getInistance;


public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.ProductViewHolder> {


    private  RoomDao roomDao;
    private Context context;
    private List<Article> articleslist;
    InternationalNews activity;



    public GlobalAdapter(List<Article> articleslist, Context context , InternationalNews activity) {
        this.articleslist = articleslist;
        this.context = context;
        this.activity=activity;
        roomDao = getInistance(context).roomDao();




    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(context).load(articleslist.get(position).getUrlToImage()).into(holder.ivNewImage);
        holder.tvSource.setText(articleslist.get(position).getAuthor());
        holder.tvTitle.setText(articleslist.get(position).getTitle());
        holder.webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web_i = new Intent(context, WebViewActivity.class);
                web_i.putExtra("url" , articleslist.get(position).getUrl());
                context.startActivity(web_i);

            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialoge(context,articleslist.get(position).getUrl());
            }
        });
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewItem item=new NewItem(articleslist.get(position).getUrlToImage() ,
                        articleslist.get(position).getAuthor() ,
                        articleslist.get(position).getTitle() ,
                        articleslist.get(position).getUrl());
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        roomDao.addItem(item);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "تم الاضافة الي المفضلة", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });




    }


    @Override
    public int getItemCount() {
        return articleslist.size();
    }

    @OnClick({R.id.fav_btn, R.id.web_btn, R.id.share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fav_btn:
                break;
            case R.id.web_btn:
                break;
            case R.id.share_btn:
                break;
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_new_image)
        ImageView ivNewImage;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.fav_btn)
        ImageView favBtn;
        @BindView(R.id.web_btn)
        ImageView webBtn;
        @BindView(R.id.share_btn)
        ImageView shareBtn;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
