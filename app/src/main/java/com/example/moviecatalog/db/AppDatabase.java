package com.example.moviecatalog.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {DbDataModel.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract DbDataDAO favDataDao();

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database_favorite")
                    .allowMainThreadQueries().build();
            appDatabase.initCountData();
        }
        return appDatabase;
    }

    private void initCountData() {
        if (favDataDao().count() == 0) {
            runInTransaction(new Runnable() {
                List<DbDataModel> dataModels = new ArrayList<>();
                DbDataModel dataModel;

                @Override
                public void run() {
                    dataModels = favDataDao().getFavoriteData();
                    for (int i = 0; i < favDataDao().getAllDataCursor().getCount(); i++) {
                        dataModel = new DbDataModel();
                        dataModel = dataModels.get(i);
                        favDataDao().insert(dataModel);
                    }
                }
            });

        }
    }
}
