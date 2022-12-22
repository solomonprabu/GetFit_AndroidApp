package com.example.getfitnav;

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
import android.widget.TextView;
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

    TextView name, mail,Acc_Id;
    Button logout,addButton,viewButton;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //        -------------------------Hooks----------------------------
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        logout = findViewById(R.id.logout);
        addButton = findViewById(R.id.addF_btn);
        viewButton = findViewById(R.id.viewIntake_btn);
        Acc_Id = findViewById(R.id.acc_Id);

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

        if (account != null){
            String Name = account.getDisplayName();
            String Mail = account.getEmail();
            String Id = account.getId();

            name.setText("Name: "+Name);
            mail.setText(Mail);
            Acc_Id.setText("ID: "+Id);

        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MyAccount.this,AddIntake.class);
                startActivity(n);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyAccount.this,ViewIntake.class);
                startActivity(i);
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

