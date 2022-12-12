package com.example.getfitnav;

import static com.example.getfitnav.R.id.nav_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MyAccount extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//-----------------------------------Maincode------------------------------
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<UserItem> UserItemArrayList;
    UserRecyclerAdapter adapter;
    Button buttonAdd;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

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

        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

//-----------------------------------------Firebase-------------------------------------
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // work offline
        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserItemArrayList = new ArrayList<>();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MyAccount.this);
            }
        });

        readData();

    }

    private void readData() {

        databaseReference.child("MAIL").orderByChild("mailID").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserItem usersId = dataSnapshot.getValue(UserItem.class);
                    UserItemArrayList.add(usersId);
                }
                adapter = new UserRecyclerAdapter(MyAccount.this, UserItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

            case    R.id.home:
                Intent a = new Intent(MyAccount.this,MyAccount.class);
                startActivity(a);
                break;

            case R.id.B_calc:
                Intent intent = new Intent(MyAccount.this,BMI.class);
                startActivity(intent);
                break;

            case  R.id.fitNess:
                Intent intent2 = new Intent(MyAccount.this,Fitness.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                SignOut();
                break;

            case R.id.account:

                break;

            case R.id.api:
                Intent n = new Intent(MyAccount.this,FoodApi.class);
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

  //  --------------------------------------MainCode--------------------//
    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_details);

            EditText textHeight = dialog.findViewById(R.id.Height_display);
            EditText textWeight = dialog.findViewById(R.id.Weight_display);

            Button buttonAdd = dialog.findViewById(R.id.buttonAdd_detail);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel_add);

            buttonAdd.setText("ADD");


            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });



            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "user" + new Date().getTime();
                    String userWeight = textHeight.getText().toString();
                    String userHeight = textWeight.getText().toString();


                    if (userHeight.isEmpty() || userWeight.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("USERS").child(id).setValue(new UserItem(id,userHeight, userWeight));
                        Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
  }
}

