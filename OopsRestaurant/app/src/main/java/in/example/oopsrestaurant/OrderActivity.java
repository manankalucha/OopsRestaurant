package in.example.oopsrestaurant;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter myAdapter;
    private ArrayList<Cart> carts;
    ArrayList<JsonObject>  history;
    ArrayList<Cart> cartArrayList = new ArrayList<>();
    TextView textView;
    String rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        textView = findViewById(R.id.text);

//        FirebaseFirestore.getInstance().collection("Restaurants").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot ds = task.getResult();
//                    rest = ds.getString("name");
//                }
//            }
//        });
        FirebaseFirestore.getInstance().collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        cartArrayList.clear();
                        if (task.getResult().getDocuments().size() != 0) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments())
                                if(doc.get("restaurantName").equals("Aman Jain")){
                                    Cart p = doc.toObject(Cart.class);
                                    cartArrayList.add(p);
                            }
                        }
                    }
                });

        textView.setText(Integer.toString(cartArrayList.size()));
        myAdapter = new OrderAdapter(OrderActivity.this, cartArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        recyclerView.setAdapter(myAdapter);

    }

}

