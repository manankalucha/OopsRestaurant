package in.dete.oops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.graph.MutableNetwork;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import in.dete.oops.Fragments.HomeFragment;

public class MenuActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    String restaurantName;
    private RecyclerView recyclerView;
    private MenuAdapter myAdapter;
    private ArrayList<Menu> menus;
    private TextView itemCount, totalPrice, viewCart;
    private ArrayList<Cart> carts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        carts = new ArrayList<>();

        restaurantName = getIntent().getStringExtra("restaurants");

        menus = new ArrayList<>();
        itemCount = findViewById(R.id.itemCount);
        totalPrice = findViewById(R.id.totalPrice);
        viewCart = findViewById(R.id.viewCart);

        viewCart.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(intent);
        });

        menus.add(new Menu("Aman Jain", "450","non-veg", null ));
        menus.add(new Menu("Aman Jain", "450","veg", null ));
        menus.add(new Menu("Aman Jain", "","veg", null ));
        menus.add(new Menu("Aman Jain", "","veg", null ));


        myAdapter = new MenuAdapter(MenuActivity.this, menus, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
        recyclerView.setAdapter(myAdapter);
//
//        myAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                carts.add(new Cart(menus.get(position).getN(), menus.get(position).getP(),menus.get(position).getV()));
//            }
//        });
        HashMap<String, Object> odr = new HashMap<>();

        FirebaseFirestore.getInstance().collection("Orders")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                        DocumentSnapshot ds = task.getResult();
                        ((TextView) findViewById(R.id.itemCount)).setText(ds.getString("name"));
//                        ((TextView) findViewById(R.id.riderNo)).setText(ds.getString("m"));
//                        rider_ph = ds.getString("m");
                    }
                });

    }

    @Override
    public void onItemClick(int position) {
        carts.add(new Cart(menus.get(position).getN(), menus.get(position).getP(),menus.get(position).getV()));
        Toast.makeText(MenuActivity.this, "Hi",Toast.LENGTH_LONG).show();
        if(carts.size()!=0){
            totalPrice.setText(Integer.toString(carts.size()));

        }

    }
}
//    HashMap<String , Object> order = new HashMap<>();
//            order.put("name", menu.get(position).getN());
//                    order.put("price", menu.get(position).getP());
//                    order.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                    FirebaseFirestore.getInstance().collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//@Override
//public void onComplete(@NonNull Task<DocumentReference> task) {
//        if (task.isSuccessful()){
//        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });