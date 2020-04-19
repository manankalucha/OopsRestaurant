package in.dete.oops;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private NavController navController;
    private User user;
    private LinearLayout linearLayout;
    private TextView home, cart, profile, search;
    private RecyclerView recyclerView;
    private RestaurantAdapter myAdapter;
    TextView welcome;
    private ArrayList<Restaurant> restaurants;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        pref.edit().putBoolean("isIntroOpened", true).apply();

        home = findViewById(R.id.home);
        search = findViewById(R.id.search1);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);
        welcome = findViewById(R.id.welcome);

        profile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });
        cart.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

//        search.setOnClickListener(v -> {
//            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//        });


        user = (User) getIntent().getSerializableExtra("user");
       // welcome.setText(user.getName());
        if(user==null){
            String userInfo = getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("loggedInUser","");
            if(!userInfo.equals("")) user = new Gson().fromJson(userInfo, User.class);
        }


        restaurants = new ArrayList<>();

        restaurants.add(new Restaurant("Aman Jain", "","veg", "4.5", null ));
        restaurants.add(new Restaurant("Aman Jain", "","veg", "4.5", null ));
        restaurants.add(new Restaurant("Aman Jain", "","veg", "4.5", null ));
        restaurants.add(new Restaurant("Aman Jain", "","veg", "4.5", null ));

        myAdapter = new RestaurantAdapter(HomeActivity.this, restaurants);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        recyclerView.setAdapter(myAdapter);




//        btnLogout = findViewById(R.id.btnLogout);
//
//        btnLogout.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity2.this);
//            builder.setMessage("Are you sure you want to sign out of this app");
//            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
//                Paper.book().destroy();
//                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
//                FirebaseAuth.getInstance().signOut();
//                getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();
//                Intent intent = new Intent(HomeActivity2.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            });
//            builder.setNegativeButton("No", null);
//            builder.create().show();
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
