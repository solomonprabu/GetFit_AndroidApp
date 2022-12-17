package com.example.getfitnav;

import static com.example.getfitnav.R.id.acc_Id;
import static com.example.getfitnav.R.id.food_name;
import static com.example.getfitnav.R.id.nav_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class MyAccount extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//-----------------------------------Maincode------------------------------


    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
//    String check="";

    EditText foodname,proteinIntake,fatIntake,carbsIntake;
    Button addIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //        -------------------------Hooks----------------------------
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        foodname = findViewById(R.id.food_name);
        proteinIntake = findViewById(R.id.protein_intake);
        fatIntake = findViewById(R.id.fat_intake);
        carbsIntake = findViewById(R.id.carbs_intake);

        addIntake = findViewById(R.id.add_button);
        //        -------------------------Toolbar code ----------------------
        setSupportActionBar(toolbar);

        //        -------------------------Navigation code ----------------------
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this); //to activate menu icons in Navigation menu
        navigationView.setCheckedItem(nav_view);
//----------------------------------------Google sigin -------------------------------
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



            String Id = account.getId();

//-----------------------------------------Firebase-------------------------------------
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

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
                transaction.put("Food Name",foodnameEnt);
                transaction.put("Carbhohydrates",carbsIntakeEnt);
                transaction.put("Fat",fatIntakeEnt);
                transaction.put("Protein",proteinIntakeEnt);
                transaction.put("time",CurrentDate);

                firestore.collection("Intakes").add(transaction).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MyAccount.this, "Added", Toast.LENGTH_SHORT).show();
                        foodname.setText("");
                        proteinIntake.setText("");
                        fatIntake.setText("");
                        carbsIntake.setText("");

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:
                Intent a = new Intent(MyAccount.this, MainActivity.class);
                startActivity(a);
                break;

            case R.id.B_calc:
                Intent intent = new Intent(MyAccount.this, BMI.class);
                startActivity(intent);
                break;

            case R.id.fitNess:
                Intent intent2 = new Intent(MyAccount.this, Fitness.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                SignOut();
                break;

            case R.id.account:

                break;

            case R.id.api:
                Intent n = new Intent(MyAccount.this, FoodApi.class);
                startActivity(n);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SignOut() {
        Task<Void> voidTask = gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }

        });
    }
}
  //  --------------------------------------MainCode--------------------//
