package in.dete.oops;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PaytmActivity extends AppCompatActivity {

    Button btncontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

        btncontinue = findViewById(R.id.continuebtn);

        btncontinue.setOnClickListener(v -> {

        });
    }
}
