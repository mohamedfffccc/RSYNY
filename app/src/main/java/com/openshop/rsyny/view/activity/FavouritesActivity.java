package com.openshop.rsyny.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openshop.rsyny.R;
import com.openshop.rsyny.data.adapter.FavAdapter;
import com.openshop.rsyny.data.local.room.NewItem;
import com.openshop.rsyny.data.local.room.RoomDao;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openshop.rsyny.data.local.room.RoomManger.getInistance;

public class FavouritesActivity extends AppCompatActivity {

    @BindView(R.id.rv_fav)
    RecyclerView rvFav;
    RoomDao roomDao;
    List<NewItem> data;
    FavAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);
        roomDao = getInistance(this).roomDao();

        rvFav.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this));
        getFav();
    }

    private void getFav() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                data=roomDao.getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter=new FavAdapter(data,FavouritesActivity.this,FavouritesActivity.this);
                        rvFav.setAdapter(adapter);
                    }
                });
            }
        });

    }
}
