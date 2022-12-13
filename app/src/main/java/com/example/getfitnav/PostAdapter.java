package com.example.getfitnav;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    Context context;
    List<Item> postList;

    public PostAdapter(Context context , List<Item> postList){
        this.context = context;
        this.postList = postList;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.eachpost , parent , false);
        return new PostHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        Item item = postList.get(position);
        holder.setImageView(item.getImageurl());
        holder.setmTags(item.getTags());
        holder.setmcalories(item.getCalories());
        holder.setmcarbs(item.getCarbsQuantity());
        holder.setmfat(item.getFatQuantity());
        holder.setmprotein(item.getProteinQuantity());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mcalories, mTags, mprotein, mfat, mcarbs;
        View view;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

        }

        public void setImageView(String url){
            imageView = view.findViewById(R.id.imageview);
            Glide.with(context).load(url).into(imageView);
        }
        public void setmcalories(int calorie){
            mcalories = view.findViewById(R.id.calories);
            mcalories.setText(calorie + "J calories");
        }
        public void setmTags(String tag){
            mTags = view.findViewById(R.id.tags);
            mTags.setText(tag);
        }
        public void setmprotein(int protien){
            mprotein = view.findViewById(R.id.protein);
            mprotein.setText(protien+ "g proteins");
        }
        public void setmfat(int fat){
            mfat = view.findViewById(R.id.fat);
            mfat.setText(fat+ "g fat");
        }
        public void setmcarbs(int carbs){
            mcarbs = view.findViewById(R.id.carbs);
            mcarbs.setText(carbs+ "g carbhohydrates");
        }

    }
}
