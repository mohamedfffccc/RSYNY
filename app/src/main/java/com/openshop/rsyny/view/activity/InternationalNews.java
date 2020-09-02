package com.openshop.rsyny.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openshop.rsyny.R;
import com.openshop.rsyny.data.adapter.GlobalAdapter;
import com.openshop.rsyny.data.adapter.SourceAdapter;
import com.openshop.rsyny.data.model.SourceItem;
import com.openshop.rsyny.data.model.international.Article;
import com.openshop.rsyny.data.model.international.International;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.openshop.rsyny.data.local.HelperMethod.dismissProgressDialog;
import static com.openshop.rsyny.data.local.HelperMethod.showProgressDialog;
import static com.openshop.rsyny.data.api.RetrofitClient.getClient2;

public class InternationalNews extends AppCompatActivity {

    @BindView(R.id.source_rv)
    RecyclerView sourceRv;
    @BindView(R.id.global_rv)
    RecyclerView globalRv;
    ArrayList<SourceItem> sources = new ArrayList<>();
    SourceAdapter sourceAdapter;
    ArrayList<Article> newslist = new ArrayList<>();
    GlobalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme2);

        setContentView(R.layout.activity_international_news);
        ButterKnife.bind(this);
        addSources();
        getNews("bbc-news");

    }

    private void addSources() {
        sourceRv.setLayoutManager(new LinearLayoutManager(InternationalNews.this,RecyclerView.HORIZONTAL ,
                true));
        sources.add(new SourceItem( R.drawable.bbc,    "bbc-news"));

        sources.add(new SourceItem( R.drawable.newyork,    "new-york-magazine"));
        sources.add(new SourceItem( R.drawable.abc,    "abc-news-au"));
        sources.add(new SourceItem( R.drawable.google,    "google-news"));
        sources.add(new SourceItem( R.drawable.talksport,    "talksport"));
        sources.add(new SourceItem( R.drawable.bild,    "bild"));
        sources.add(new SourceItem( R.drawable.cnn,    "cnn"));
        sources.add(new SourceItem( R.drawable.focus,    "focus"));
        sources.add(new SourceItem( R.drawable.fourtwo,    "four-four-two"));
        sources.add(new SourceItem( R.drawable.foxsport,    "fox-sports"));
        sources.add(new SourceItem( R.drawable.ftimes,    "financial-times"));
        sources.add(new SourceItem( R.drawable.time,    "time"));
        sources.add(new SourceItem( R.drawable.timesindia,    "the-times-of-india"));
        sourceAdapter=new SourceAdapter(sources, InternationalNews.this,InternationalNews.this);
        sourceRv.setAdapter(sourceAdapter);



    }
  public void   getNews(String name)
  {
      newslist.clear();
      globalRv.setLayoutManager(new LinearLayoutManager(InternationalNews.this));
      showProgressDialog(InternationalNews.this, "برجاء الانتظار...");
      getClient2().getGlobal(name,  "top" , "fc183d7a06f24301ae2dea58a313ab2a").enqueue(new Callback<International>() {
          @Override
          public void onResponse(Call<International> call, Response<International> response) {
              dismissProgressDialog();
              try {
                  newslist.addAll(response.body().getArticles());
                  adapter=new GlobalAdapter(newslist,InternationalNews.this,InternationalNews.this);
                  globalRv.setAdapter(adapter);

              }
              catch (Exception e)
              {

              }

          }

          @Override
          public void onFailure(Call<International> call, Throwable t) {

          }
      });

  }
}
