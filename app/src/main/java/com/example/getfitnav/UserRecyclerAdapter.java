package com.example.getfitnav;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<UserItem> UserItemArrayList;
    DatabaseReference databaseReference;

    public UserRecyclerAdapter(Context context, ArrayList<UserItem> userItemArrayList) {
        this.context = context;
        this.UserItemArrayList = userItemArrayList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserItem users = UserItemArrayList.get(position);

        holder.textHeight_disp.setText("Height : " + users.getUserHeight());
        holder.textWeight_disp.setText("Weight : " + users.getUserWeight());
//        holder.textCountry.setText("Country : " + users.getUserCountry());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, users.getUserId(), users.getUserHeight(), users.getUserWeight());
            }
        });


    }

    @Override
    public int getItemCount() {
        return UserItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//----------------------------Declaring user_item variables--------------------------
        TextView textHeight_disp;
        TextView textWeight_disp;

        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           textHeight_disp = itemView.findViewById(R.id.Height_display);
            textWeight_disp = itemView.findViewById(R.id.Weight_display);

            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);

        }

    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String height, String weight) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm);

            EditText textHeight = dialog.findViewById(R.id.heighttxt);
            EditText textWeight = dialog.findViewById(R.id.weighttxt);

            textHeight.setText(height);
            textWeight.setText(weight);


            Button buttonUpdate = dialog.findViewById(R.id.buttonUpdate);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newHeight = textHeight.getText().toString();
                    String newWeight = textWeight.getText().toString();
                    //  String newCountry = textCountry.getText().toString();

                    if (height.isEmpty() || weight.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newHeight.equals(height) && newWeight.equals(weight) ) {
                            Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("MAIL").child(id).setValue(new UserItem(id, newHeight, newWeight));
                            Toast.makeText(context, "User Updated successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}


//-------------------------main----------------------------------------------------


//public class ViewDialogConfirmDelete {
//    public void showDialog(Context context, String id) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.view_dialog_confirm);
//
//        Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
//        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
//
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                databaseReference.child("USERS").child(id).removeValue();
//                Toast.makeText(context, "User Deleted successfully!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//
//            }
//        });

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//    }
//}
//}