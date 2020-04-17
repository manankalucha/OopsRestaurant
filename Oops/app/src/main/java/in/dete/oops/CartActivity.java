package in.dete.oops;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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
}
