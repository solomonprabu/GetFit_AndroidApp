package com.example.getfitnav;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class ViewDialogUpdate {
    public void showDialog(Context context, String id, String height, String weight) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_details);

        EditText textHeight = dialog.findViewById(R.id.textHeight);
        EditText textWeight = dialog.findViewById(R.id.textWeight);
//        EditText textCountry = dialog.findViewById(R.id.textCountry);

        textHeight.setText(height);
        textWeight.setText(weight);
//        textCountry.setText(country);


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
            DatabaseReference databaseReference;

            @Override
            public void onClick(View view) {

                String newHeight = textHeight.getText().toString();
                String newWeight = textWeight.getText().toString();
//                String newCountry = textCountry.getText().toString();

                if (height.isEmpty() || weight.isEmpty()) {
                    Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                } else {

                    if (newHeight.equals(height) && newWeight.equals(weight)) {
                        Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("USERS").child(id).setValue(new UserItem(id, newHeight, newWeight));
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
