package com.openshop.rsyny.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.openshop.rsyny.BuildConfig;
import com.openshop.rsyny.R;
import com.openshop.rsyny.data.adapter.NewsAdapter;
import com.openshop.rsyny.data.adapter.SlideAdapter;
import com.openshop.rsyny.data.model.news.Article;
import com.openshop.rsyny.data.model.news.News;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.openshop.rsyny.data.local.HelperMethod.disappearKeypad;
import static com.openshop.rsyny.data.local.HelperMethod.dismissProgressDialog;
import static com.openshop.rsyny.data.local.HelperMethod.shareDialoge;
import static com.openshop.rsyny.data.local.HelperMethod.showProgressDialog;
import static com.openshop.rsyny.data.api.RetrofitClient.getClient;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.activity_home_iv_search)
    ImageView activityHomeIvSearch;
    @BindView(R.id.activity_home_ed_query)
    EditText activityHomeEdQuery;
    @BindView(R.id.activity_home_iv_delete)
    ImageView activityHomeIvDelete;
    @BindView(R.id.fragment_home_lin_search)
    LinearLayout fragmentHomeLinSearch;
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    ArrayList<Article> data = new ArrayList<>();
    NewsAdapter adapter;
    public static final String API_KEY = "30563e9214a34b158a1a5e0a141b4d6b";
    @BindView(R.id.home_logo)
    CircleImageView homeLogo;
    @BindView(R.id.home_fav_btn)
    TextView homeFavBtn;
    @BindView(R.id.home_share_btn)
    TextView homeShareBtn;
    @BindView(R.id.home_rate_btn)
    TextView homeRateBtn;

    @BindView(R.id.home_exit_btn)
    TextView homeExitBtn;
    @BindView(R.id.nav_view2)
    NavigationView navView2;
    @BindView(R.id.home_home_btn)
    TextView homeHomeBtn;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.home_vp)
    ViewPager homeVp;
    @BindView(R.id.home_int_btn)
    TextView homeIntBtn;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.iv_close_d)
    ImageView ivCloseD;

    private AdView mAdView;
    ArrayList<Article> slide_list = new ArrayList<>();
    SlideAdapter slideAdapter;
    private int current_position;
    private Timer timer;
    private SimpleDateFormat sdf;
    private String date2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        // disaplyAds();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date2 = sdf.format(new Date());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        rvNews.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        getNews();
        createSlideShow();
        activityHomeEdQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    filterData(date2, v.getText().toString());
                    disappearKeypad(HomeActivity.this, v);

                    return true;

                }
                return true;
            }
        });
    }

    private void disaplyAds() {
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-1725564943998315/3322290074")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
        ///
    }

    private void getNews() {
        showProgressDialog(HomeActivity.this, "برجاء الانتظار...");
        getClient().getNews("eg", API_KEY).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                dismissProgressDialog();
                data.addAll(response.body().getArticles());
                adapter = new NewsAdapter(data, HomeActivity.this, HomeActivity.this);
                rvNews.setAdapter(adapter);
                slide_list.clear();
                slide_list.add(response.body().getArticles().get(1));
                slide_list.add(response.body().getArticles().get(3));
                slide_list.add(response.body().getArticles().get(5));
                slide_list.add(response.body().getArticles().get(6));
                slide_list.add(response.body().getArticles().get(7));
                slide_list.add(response.body().getArticles().get(8));

                slide_list.add(response.body().getArticles().get(9));
                slide_list.add(response.body().getArticles().get(10));
                slideAdapter = new SlideAdapter(HomeActivity.this, slide_list);
                homeVp.setAdapter(slideAdapter);

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    @OnClick({R.id.iv_menu,R.id.iv_close_d ,R.id.home_home_btn, R.id.home_int_btn, R.id.activity_home_iv_search, R.id.activity_home_iv_delete, R.id.home_fav_btn, R.id.home_share_btn, R.id.home_rate_btn, R.id.home_exit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_home_iv_search:
                data.clear();

                filterData(date2, activityHomeEdQuery.getText().toString());
                break;
            case R.id.activity_home_iv_delete:
                activityHomeEdQuery.setText("");
                break;
            case R.id.home_fav_btn:
                startActivity(new Intent(HomeActivity.this, FavouritesActivity.class));
                drawer.closeDrawer(Gravity.RIGHT);

                break;
            case R.id.home_share_btn:
                shareDialoge(HomeActivity.this, "http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                break;
            case R.id.home_rate_btn:
                Intent rate_i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
                startActivity(rate_i);
                break;

            case R.id.home_exit_btn:
                showAlert();
                break;
            case R.id.home_home_btn:
                data.clear();

                getNews();
                activityHomeEdQuery.setText(" ");
                drawer.closeDrawer(Gravity.RIGHT);

                break;
            case R.id.home_int_btn:
                startActivity(new Intent(HomeActivity.this, InternationalNews.class));
                break;
            case R.id.iv_menu :
                drawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_close_d:
                drawer.closeDrawer(Gravity.RIGHT);
                break;
        }
    }

    private void filterData(String date2, String text) {
        data.clear();
        showProgressDialog(HomeActivity.this, "برجاء الانتظار...");
        getClient().filterNews(text, date2
                , "popularity", API_KEY)
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        dismissProgressDialog();
                        data.addAll(response.body().getArticles());
                        adapter = new NewsAdapter(data, HomeActivity.this, HomeActivity.this);
                        rvNews.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        dismissProgressDialog();

                    }
                });

    }

    public void showAlert() {

        final Dialog dialog = new Dialog(this, R.style.customDialogTheme);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_sign_out, null);
        dialog.setContentView(v);

        Button button = dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        Button button2 = dialog.findViewById(R.id.item_sign_out_dialog_btn_no);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Create Slide Show
    public void createSlideShow() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == slide_list.size())
                    current_position = 0;
                homeVp.setCurrentItem(current_position++, true);


            }

        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        }, 250, 2500);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
