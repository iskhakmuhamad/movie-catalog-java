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
import com.example.moviecatalog.datamodel.TvsItem;

import java.util.ArrayList;

public class TvsAdapter extends RecyclerView.Adapter<TvsAdapter.CardViewHolder> {
    private ArrayList<TvsItem> tvsData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback {
        void onItemClicked(TvsItem data);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setTvsData(ArrayList<TvsItem> datas) {
        tvsData.clear();
        tvsData.addAll(datas);
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
        holder.bind(tvsData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(tvsData.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return tvsData.size();
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

        void bind(TvsItem data) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + data.getPosterPath())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPhoto);
            tvName.setText(data.getName());
            tvDescription.setText(data.getOverview());
            tvRelease.setText(data.getFirstAirDate());
            tvRate.setText(String.valueOf(data.getVoteAverage()));
            if (data.getVoteAverage() < 7)
                imgRateStar.setImageResource(R.drawable.ic_star_half_black_24dp);
            else imgRateStar.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}
