package com.example.getfitnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddIntake extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    EditText foodname,proteinIntake,fatIntake,carbsIntake;
    Button addIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intake);

        foodname = findViewById(R.id.food_name);
        proteinIntake = findViewById(R.id.protein_intake);
        fatIntake = findViewById(R.id.fat_intake);
        carbsIntake = findViewById(R.id.carbs_intake);

        addIntake = findViewById(R.id.add_button);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String Id = account.getId();


//-----------------------------------------Firebase-------------------------------------
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        addIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodnameEnt = foodname.getText().toString();
                String proteinIntakeEnt  = proteinIntake.getText().toString();
                String fatIntakeEnt  = fatIntake.getText().toString();
                String carbsIntakeEnt  = carbsIntake.getText().toString();

                SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy  HH:mm:SS", Locale.getDefault());
                String CurrentDate = SDF.format(new Date());

//                String id = UUID.randomUUID().toString();
                Map<String,Object> transaction=new HashMap<>();
                transaction.put("id",Id);
                transaction.put("fName",foodnameEnt);
                transaction.put("Carbhohydrates",carbsIntakeEnt);
                transaction.put("Fat",fatIntakeEnt);
                transaction.put("Protein",proteinIntakeEnt);
                transaction.put("time",CurrentDate);

                if (foodnameEnt.isEmpty() ||proteinIntakeEnt.isEmpty() || fatIntakeEnt.isEmpty() || carbsIntakeEnt.isEmpty() ){
                    Toast.makeText(AddIntake.this, "Enter all the details", Toast.LENGTH_SHORT).show();
                }else {
                    firestore.collection("Intakes").add(transaction).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddIntake.this, "Added", Toast.LENGTH_SHORT).show();
                                    foodname.setText("");
                                    proteinIntake.setText("");
                                    fatIntake.setText("");
                                    carbsIntake.setText("");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddIntake.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
            }
        });
    }
}