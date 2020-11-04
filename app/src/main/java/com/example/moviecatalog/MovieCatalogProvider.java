package com.example.moviecatalog;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviecatalog.db.AppDatabase;
import com.example.moviecatalog.db.DbDataDAO;
import com.example.moviecatalog.db.DbDataModel;


@SuppressLint("Registered")
public class MovieCatalogProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.moviecatalog";
    public static final Uri URI_FAVORIT = Uri.parse("content://" + AUTHORITY + "/table_favorite");

    public static final int CODE_MENU_TABLE = 1;
    public static final int CODE_MENU_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "table_favorite", CODE_MENU_TABLE);
        URI_MATCHER.addURI(AUTHORITY, "table_favorite/*", CODE_MENU_ID);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        final int CODE = URI_MATCHER.match(uri);
        if (CODE == CODE_MENU_TABLE || CODE == CODE_MENU_ID) {
            Context context = getContext();
            if (context == null) {
                return null;
            }
            DbDataDAO dataDAO = AppDatabase.getAppDatabase(context).favDataDao();
            Cursor cursor;
            if (CODE == CODE_MENU_TABLE) {
                cursor = dataDAO.getAllDataCursor();
            } else {
                cursor = dataDAO.getDataById(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MENU_TABLE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + "table_favorite";
            case CODE_MENU_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "table_favorite";
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MENU_TABLE:
                Context context = getContext();
                if (context == null) {
                    return null;
                }
                assert contentValues != null;
                final int id = contentValues.getAsInteger("id");
                AppDatabase.getAppDatabase(context).favDataDao()
                        .insert(DbDataModel.contentValuesData(contentValues));

                return ContentUris.withAppendedId(uri, id);

            case CODE_MENU_ID:
                throw new IllegalThreadStateException("CANNOT INSERT WITH ID " + uri);

            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
