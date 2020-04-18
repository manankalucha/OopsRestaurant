package in.dete.oops;

import android.os.Bundle;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private RecyclerView recyclerView;
    private CartAdapter myAdapter;
    private ArrayList<Cart> carts;
    private Button btnConfirm;
    private ArrayList<Cart> cartArrayList;
    private double total;
    private ArrayList<Menu> menuArrayList;
    private TextView itemCount, totalPrice, viewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnConfirm = findViewById(R.id.btnConfirm);

        cartArrayList = getIntent().getParcelableArrayListExtra("order");
        menuArrayList = getIntent().getParcelableArrayListExtra("menus");
        itemCount = findViewById(R.id.itemCount);
        totalPrice = findViewById(R.id.totalPrice);
        viewCart = findViewById(R.id.viewCart);

        total = cartArrayList.get(cartArrayList.size() - 1).getTp();
        myAdapter = new CartAdapter(CartActivity.this, cartArrayList, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        recyclerView.setAdapter(myAdapter);

        HashMap<String, ArrayList<Cart>> cart = new HashMap<>();
        cart.put("order", cartArrayList);
        btnConfirm.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("Orders").add(cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CartActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
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
        int dish = 0;
//
//        }

    }
}
