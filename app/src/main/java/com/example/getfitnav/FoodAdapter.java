package com.example.getfitnav;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<Food> foodArrayList;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.db_item, parent, false);

        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, int position) {

        Food food = foodArrayList.get(position);

        holder.carbs.setText(food.Carbhohydrates);
        holder.fatVal.setText(food.Fat);
        holder.proteinVal.setText(food.Protein);
        holder.nameVal.setText(food.fName);
        holder.timestampVal.setText(food.time);
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView carbs, fatVal, proteinVal, nameVal, timestampVal;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carbs = itemView.findViewById(R.id.carbhohydrates);
            fatVal = itemView.findViewById(R.id.fatField);
            proteinVal = itemView.findViewById(R.id.proteinField);
            nameVal = itemView.findViewById(R.id.nameField);
            timestampVal = itemView.findViewById(R.id.timestampField);

        }
    }
}
