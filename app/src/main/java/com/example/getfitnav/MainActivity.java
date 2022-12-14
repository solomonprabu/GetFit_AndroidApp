package com.example.getfitnav;

import static com.example.getfitnav.R.id.nav_view;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        -------------------------Hooks----------------------------
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        CardView imagesCard,BmiCard,FoodCard,addFoodcard;

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

//        -----------------------------------Singin------------------------------------------------
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        imagesCard = findViewById(R.id.imageCard);
        imagesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                startActivity(intent);
            }
        });

        BmiCard = findViewById(R.id.bmicard);
        BmiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MainActivity.this, BMI.class);
                startActivity(n);
            }
        });

        FoodCard = findViewById(R.id.foodCard);
        FoodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FoodApi.class);
                startActivity(i);
            }
        });
        addFoodcard= findViewById(R.id.addFoodCard);
        addFoodcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(MainActivity.this, AddIntake.class);
                startActivity(f);
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

        switch (item.getItemId()){

            case R.id.home:
                break;

            case R.id.B_calc:
                Intent intent = new Intent(MainActivity.this,BMI.class);
                startActivity(intent);
                break;

            case  R.id.fitNess:
                Intent intent2 = new Intent(MainActivity.this,Fitness.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                SignOut();
                break;

            case R.id.account:
                Intent a = new Intent(MainActivity.this,MyAccount.class);
                startActivity(a);
                break;
            case R.id.api:
                Intent n = new Intent(MainActivity.this,FoodApi.class);
                startActivity(n);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
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

