package com.example.moviecatalog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalog.R;
import com.example.moviecatalog.db.DbDataModel;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.CardViewHolder> {

    private ArrayList<DbDataModel> favData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback {
        void onItemClicked(DbDataModel data);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(List<DbDataModel> favDatas) {
        favData.clear();
        favData.addAll(favDatas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_row, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        holder.bind(favData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(favData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return favData.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvName, tvRelease, tvRate;
        ImageView imgPhoto, imgRateStar;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tv_description_card);
            tvName = itemView.findViewById(R.id.tv_title_card);
            imgPhoto = itemView.findViewById(R.id.img_photo_card);
            tvRelease = itemView.findViewById(R.id.tv_realese_card);
            tvRate = itemView.findViewById(R.id.tv_rate_card);
            imgRateStar = itemView.findViewById(R.id.img_star_rate_card);
        }

        void bind(DbDataModel data) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + data.getImage())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPhoto);
            tvName.setText(data.getTitle());
            tvDescription.setText(data.getDescripsi());
            tvRelease.setText(data.getRelease());
            tvRate.setText(String.valueOf(data.getRating()));
            if (data.getRating() <= 7)
                imgRateStar.setImageResource(R.drawable.ic_star_half_black_24dp);
            else imgRateStar.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}
