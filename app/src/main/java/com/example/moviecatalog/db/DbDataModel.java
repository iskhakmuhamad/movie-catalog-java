package com.example.moviecatalog.db;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_favorite")
public class DbDataModel implements Parcelable {

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "ori_title")
    private String oriTitle;

    @ColumnInfo(name = "descripsi")
    private String descripsi;

    @ColumnInfo(name = "release")
    private String release;


    @ColumnInfo(name = "rating")
    private Double rating;


    @ColumnInfo(name = "image")
    private String image;

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private int id;


    String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriTitle() {
        return oriTitle;
    }

    public void setOriTitle(String oriTitle) {
        this.oriTitle = oriTitle;
    }

    public String getDescripsi() {
        return descripsi;
    }

    public void setDescripsi(String descripsi) {
        this.descripsi = descripsi;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.title);
        dest.writeString(this.oriTitle);
        dest.writeString(this.descripsi);
        dest.writeString(this.release);
        dest.writeValue(this.rating);
        dest.writeString(this.image);
        dest.writeInt(this.id);
    }

    public DbDataModel() {
    }

    protected DbDataModel(Parcel in) {
        this.category = in.readString();
        this.title = in.readString();
        this.oriTitle = in.readString();
        this.descripsi = in.readString();
        this.release = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.image = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<DbDataModel> CREATOR = new Parcelable.Creator<DbDataModel>() {
        @Override
        public DbDataModel createFromParcel(Parcel source) {
            return new DbDataModel(source);
        }

        @Override
        public DbDataModel[] newArray(int size) {
            return new DbDataModel[size];
        }
    };

    public static DbDataModel contentValuesData(ContentValues values) {

        DbDataModel dataModel = new DbDataModel();

        if (values.containsKey("id")) {
            dataModel.id = values.getAsInteger("id");
        }
        if (values.containsKey("title")) {
            dataModel.title = values.getAsString("title");
        }
        if (values.containsKey("ori_title")) {
            dataModel.oriTitle = values.getAsString("ori_title");
        }
        if (values.containsKey("category")) {
            dataModel.category = values.getAsString("category");
        }
        if (values.containsKey("descripsi")) {
            dataModel.descripsi = values.getAsString("descripsi");
        }
        if (values.containsKey("release")) {
            dataModel.release = values.getAsString("release");
        }
        if (values.containsKey("rating")) {
            dataModel.rating = values.getAsDouble("rating");
        }
        return dataModel;
    }


}
