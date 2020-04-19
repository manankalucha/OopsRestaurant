package in.dete.oops;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    Double total = 0.0;
    HashMap<String, Cart> odr;
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
        odr = new HashMap<>();

//        FirebaseFirestore.getInstance().collection("Restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    QuerySnapshot ds = task.getResult();
//                }
//            }
//        });
        menus.add(new Menu("Aman Jain", "450","non-veg", null ));
        menus.add(new Menu("hu Jain",   "50","veg", null ));
        menus.add(new Menu("Aman Jain", "","veg", null ));
        menus.add(new Menu("Aman Jain", "","veg", null ));

        viewCart.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            intent.putExtra("order", carts);
            intent.putExtra("menus", menus);
            intent.putExtra("restaurantName", restaurantName);
//            FirebaseFirestore.getInstance().collection("Orders").add(odr).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentReference> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(MenuActivity.this, "", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            startActivity(intent);
//        });
            startActivity(intent);
        });


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
//        HashMap<String, Class> odr = new HashMap<>();
//        odr.put("First Item", new Cart(menus.get(position).getN(), menus.get(position).getP(),menus.get(position).getV(), FirebaseAuth.getInstance().getCurrentUser().getUid()))
//
//        FirebaseFirestore.getInstance().collection("Orders")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
//                        DocumentSnapshot ds = task.getResult();
//                        ((TextView) findViewById(R.id.itemCount)).setText(ds.getString("name"));
////                        ((TextView) findViewById(R.id.riderNo)).setText(ds.getString("m"));
////                        rider_ph = ds.getString("m");
//                    }
//                });



    }

    @Override
    public void onItemClick(int position) {
        int dish =0;
        carts.add(new Cart(menus.get(position).getN(), menus.get(position).getP(),total, FirebaseAuth.getInstance().getCurrentUser().getUid(),menus.get(position).getV()));
//        odr.put(menus.get(position).getN()+dish , new Cart(menus.get(position).getN(), menus.get(position).getP(),menus.get(position).getV(), FirebaseAuth.getInstance().getCurrentUser().getUid()));
//        Toast.makeText(MenuActivity.this, "Hi",Toast.LENGTH_LONG).show();
        total = total+ Integer.parseInt(menus.get(position).getP());
        totalPrice.setText(Double.toString(total));
        if(carts.size()!=0){
            itemCount.setText(Integer.toString(carts.size()));
            ++dish;
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