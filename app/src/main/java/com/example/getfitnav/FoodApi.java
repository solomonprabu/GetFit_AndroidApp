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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodApi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private final String url = "https://api.edamam.com/api/recipes/v2?type=public";
    private final String appid = "c427fdd7";
    private final String appkey = "15f8d86990cbfbe962aef02199af45f5";

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Item> mList;

    EditText foodInput;
    Button foodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_api);


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

//        -----------------------------------Singin------------------------------------------------
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);
//-------------------------------------------API------------------------------------------
        foodInput = findViewById(R.id.food);
        foodButton = findViewById(R.id.foodBtn);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        mList = new ArrayList<>();

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempUrl = "";
                String foodName = foodInput.getText().toString().trim();

                tempUrl = url + "&q=" + foodName + "&app_id=" + appid + "&app_key=" + appkey;

                //String url = " https://api.edamam.com/api/recipes/v2?type=public&q=biriyani&app_id=c427fdd7&app_key=15f8d86990cbfbe962aef02199af45f5";
//        String url = "https://pixabay.com/api/?key=20125103-67afb21c80781c3419030cb40&q=animal&image_type=photo&pretty=true";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("hits");

                            for(int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject jsonObjectRecipe = jsonArray.getJSONObject(i);
                                JSONObject recipe = jsonObjectRecipe.getJSONObject("recipe");
                                String recipeLabel = recipe.getString("label");

                                JSONObject images = recipe.getJSONObject("images");
                                JSONObject small = images.getJSONObject("SMALL");
                                String imageUri = small.getString("url");
                                int calories = recipe.getInt ("calories");

                                Log.d("label",recipeLabel);

//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String imageUrl = jsonObject.getString("webformatURL");
//                        int likes = jsonObject.getInt("likes");
//                        String tags = jsonObject.getString("tags");
//
//                        Item post = new Item(imageUrl , tags , likes);
//                        mList.add(post);


                                JSONObject totalNutrients = recipe.getJSONObject("totalNutrients");
                                JSONObject fat = totalNutrients.getJSONObject("FAT");
                                int fatQuantity = fat.getInt("quantity");

                                JSONObject chocdf = totalNutrients.getJSONObject("CHOCDF");
                                int carbsQuantity = chocdf.getInt("quantity");

                                JSONObject procnt = totalNutrients.getJSONObject("PROCNT");
                                int ProteinQuantity = procnt.getInt("quantity");

                                Item post = new Item(imageUri , recipeLabel , calories,fatQuantity,carbsQuantity,ProteinQuantity); //
                                mList.add(post);

                            }

                            PostAdapter adapter = new PostAdapter(FoodApi.this , mList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            foodInput.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FoodApi.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
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
                Intent h = new Intent(FoodApi.this,MainActivity.class);
                startActivity(h);
                break;

            case R.id.B_calc:
                Intent intent = new Intent(FoodApi.this, BMI.class);
                startActivity(intent);
                break;

            case R.id.fitNess:
                Intent intent2 = new Intent(FoodApi.this, Fitness.class);
                startActivity(intent2);
                break;

            case R.id.logout:
                SignOut();
                break;

            case R.id.account:
                Intent a = new Intent(FoodApi.this, MyAccount.class);
                startActivity(a);
                break;

            case R.id.api:
                break;

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