package com.example.getfitnav;

import static com.example.getfitnav.R.id.nav_view;
import static com.example.getfitnav.R.id.tv_weightDisp;

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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class Fitness extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView textView;
    Toolbar toolbar;
    RadioGroup radioGroup;
    RadioButton thin, fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

//        -------------------------Hooks----------------------------
        drawerLayout = findViewById(R.id.gain_drawerLayout);
        navigationView = findViewById(R.id.fit_nav);
        drawerLayout = findViewById(R.id.gain_drawerLayout);
        toolbar = findViewById(R.id.toolbar_fit);

        radioGroup = findViewById(R.id.radiogroup);
        thin = findViewById(R.id.RB_thin);
        fat = findViewById(R.id.RB_fat);
        textView = findViewById(R.id.tv_weightDisp);

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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);


        getIntent();
//        -----------------------------------------------------------------------------------
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
                Intent i = new Intent(Fitness.this,MainActivity.class);
                startActivity(i);
                break;

            case R.id.B_calc:
                Intent intent = new Intent(Fitness.this,BMI.class);
                startActivity(intent);
                break;
            case R.id.logout:
                SignOut();
                break;

            case  R.id.fitNess:
                break;
            case R.id.account:
                Intent a = new Intent(Fitness.this,MyAccount.class);
                startActivity(a);
                break;

            case R.id.api:
                Intent n = new Intent(Fitness.this,FoodApi.class);
                startActivity(n);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }
    public void thinButton (View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        thin = findViewById(radioId);
        textView.setText("Your Weight is :"+GlobalVariable.weightglobal);

//----------------------------------Amount of Calories to intake calculation----------------------------------
        int thin_intake = (int) (GlobalVariable.weightglobal * 2.2 * 10);
        TextView thin_fat = findViewById(R.id.fat_disp);
        thin_fat.setText("Your daily calorie intake should be :"+thin_intake+" grams");
//        -------------------------------------Ratio to intake---------------------------------------------
//        -------------------------------------------Protien intake--------------------------------------
        int protien_percentage = thin_intake*30/100;
        TextView protien = findViewById(R.id.protien_percentage);
        protien.setText("Your protien intake should be :"+ protien_percentage+" grams");

//        -------------------------------------------fat intake--------------------------------------
        int fat_percentage = thin_intake*25/100;
        TextView fat = findViewById(R.id.fat_percentage);
        fat.setText("Your fat intake should be :"+ fat_percentage+" grams");
        //        -------------------------------------------Carbhohydrates intake--------------------------------------
        int carb_percentage = thin_intake*45/100;
        TextView carbs = findViewById(R.id.carb_percentage);
        carbs.setText("Your Carbhohydrates intake should be :"+ carb_percentage+" grams");

        Toast.makeText(this,"Selected button is :"+thin.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void fatButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        fat = findViewById(radioId);
        textView.setText("Your Weight is :"+GlobalVariable.weightglobal);


//----------------------------------Amount of Calories to intake calculation----------------------------------
        int fat_intake = (int) (GlobalVariable.weightglobal * 2.2 * 12);
        TextView thin_fat = findViewById(R.id.fat_disp);
        thin_fat.setText("Your daily calorie intake should be :"+fat_intake+" grams");

        int protien_percentage = fat_intake*30/100;
        TextView protien = findViewById(R.id.protien_percentage);
        protien.setText("Your protien intake should be :"+ protien_percentage+" grams");

        int fat_percentage = fat_intake*25/100;
        TextView fat_view = findViewById(R.id.fat_percentage);
        fat_view.setText("Your fat intake should be :"+ fat_percentage+" grams");

        int carb_percentage = fat_intake*45/100;
        TextView carbs = findViewById(R.id.carb_percentage);
        carbs.setText("Your Carbhohydrates intake should be :"+ carb_percentage+" grams");

        Toast.makeText(this,"Selected button is :"+fat.getText().toString(),Toast.LENGTH_SHORT).show();
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

