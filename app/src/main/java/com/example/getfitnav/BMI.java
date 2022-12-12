package com.example.getfitnav;

import static com.example.getfitnav.R.id.bmi_disp;
import static com.example.getfitnav.R.id.nav_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class BMI extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView textView;

    Button calculation;
    EditText heightText, weightText;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

         textView=findViewById(R.id.bmi_disp);
         calculation = findViewById(R.id.calc_button);
         heightText = findViewById(R.id.heighttxt);
         weightText = findViewById(R.id.weighttxt);
         drawerLayout = findViewById(R.id.bmi_drawerLayout);
         navigationView = findViewById(R.id.nav_view);
         toolbar = findViewById(R.id.toolbar_bmi);
        //        -------------------------Toolbar code ----------------------
        setSupportActionBar(toolbar);

//        -------------------------Navigation code ----------------------
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this); //to activate menu icons in Navigation menu
        navigationView.setCheckedItem(nav_view);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);


        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float cm = Float.parseFloat(heightText.getText().toString());
                float height = cm/100;
                float weight = Float.parseFloat(weightText.getText().toString());
                float calc = weight / (height*height);

                GlobalVariable.weightglobal = weight;


                if (Float.isNaN(cm)){
                    Toast.makeText(BMI.this,"Enter Your heigth..!",Toast.LENGTH_SHORT).show();
                    heightText.requestFocus();
                    heightText.setError("Field is empty");
                    return;

                }else if (Float.isNaN(weight)){
                    Toast.makeText(BMI.this,"Enter Your weight..!",Toast.LENGTH_SHORT).show();
                    weightText.requestFocus();
                    weightText.setError("Field is empty");
                    return;

                } else {


                    String valu_calc = String.valueOf(calc);
                    textView.setText("Your BMI value is: "+valu_calc);

                }

                //Log.d("MyINt","value"+ valu_calc);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.home:
                Intent i = new Intent(BMI.this,MainActivity.class);
                startActivity(i);
                break;

            case R.id.B_calc:
                break;

            case R.id.logout:
                SignOut();
                break;

            case  R.id.fitNess:
                Intent intent2 = new Intent(BMI.this,Fitness.class);
                startActivity(intent2);
                break;

            case R.id.account:
                Intent a = new Intent(BMI.this,MyAccount.class);
                startActivity(a);

            case R.id.api:
                Intent n = new Intent(BMI.this,FoodApi.class);
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