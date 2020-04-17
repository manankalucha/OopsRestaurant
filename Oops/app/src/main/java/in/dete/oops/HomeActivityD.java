package in.dete.oops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivityD extends AppCompatActivity {

    private View progress;
private CircleImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_d);

        profile = findViewById(R.id.profile);

        profile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivityD.this, ProfileActivityD.class));
        });
    }
}
