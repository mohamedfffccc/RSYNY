package com.openshop.rsyny.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void addItem(NewItem... item);

    @Delete
    void removeItem(NewItem... item);

    @Query("select * from NewItem")
    List<NewItem> getAll();

    @Query("delete from NewItem")
    void delAll();

    @Update
    void update(NewItem... item);


}
