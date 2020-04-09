package in.example.oopsrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, aptAddress;
    public static int AUTOCOMPLETE_ACTIVITY = 180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
