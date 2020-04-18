package in.dete.oops;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText fullname;
    private RadioGroup group;
    private String gender;
    private FirebaseUser firebaseUser;
    private User user;
    private EditText   email, phonenumber;
    private TextView save, birthdate, addId;
    private String name, phone, email1, birth,address, gender1;
    ImageView btnBack;
    private EditText aptAddress;
    public static int AUTOCOMPLETE_ACTIVITY = 180;
    private GeoPoint aptLoc;
    private TextView home, cart, profile, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        group = findViewById(R.id.gender);
        int val = group.getCheckedRadioButtonId();
        if (val == -1) gender = "";
        else gender = ((RadioButton) findViewById(val)).getText().toString();



        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);

        home.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        });
        cart.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, CartActivity.class));
        });

//        search.setOnClickListener(v -> {
//            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = Utils.fetchUserInfo(ProfileActivity.this);
        fullname = findViewById(R.id.firstname);
        aptAddress = findViewById(R.id.aptAddress);

        btnBack = findViewById(R.id.btnBack);
        birthdate = findViewById(R.id.birthdate);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        save = findViewById(R.id.save);

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        try {
            Places.initialize(ProfileActivity.this, "AIzaSyBXpHTrurGbpDlb6RXy5AisDcQkKxWiXyU");
        } catch (Exception e) {
        }
        aptAddress.setOnClickListener(v ->
                startActivityForResult(new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setCountry("IN")
                        .build(ProfileActivity.this), AUTOCOMPLETE_ACTIVITY));



        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dplistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthdate.setText(" " + i2 + "." + (i1 + 1) + "." + i);
            }
        };
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(ProfileActivity.this, dplistener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.create();
                if (!isFinishing())
                    dp.show();
            }
        });

        User user = Utils.fetchUserInfo(ProfileActivity.this);
        if (user != null) {
            fullname.setText(user.getName());
            phonenumber.setText(user.getPhone());
            email.setText(user.getEmail());
        }

        save.setOnClickListener(v1 -> {
            if (validate()) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                User user2 = new User(name, user.getPhone(), email1, FirebaseAuth.getInstance().getCurrentUser().getUid(), gender1, birth, 1, address);
                docRef.set(user2, SetOptions.merge()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Utils.storeUserInfo(user2, ProfileActivity.this);
                        Toast.makeText(ProfileActivity.this, "Your data has been updated", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        if (requestCode == AUTOCOMPLETE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                aptAddress.setText(place.getName());
                address = place.getAddress();
                aptLoc = new GeoPoint(place.getLatLng().latitude, place.getLatLng().longitude);
                // findViewById(R.id.addressExtraDetails).setVisibility(View.VISIBLE);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("Address", status.getStatusMessage());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Boolean validate() {
        name = fullname.getText().toString();
        email1 = email.getText().toString();
        birth = birthdate.getText().toString();
        if (name == null || name.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter the name", Toast.LENGTH_LONG).show();
            return false;
        }
        name = Utils.formatName(name);
        fullname.setText(name);

        if (email1 == null) {
            Toast.makeText(ProfileActivity.this, "Please enter the email", Toast.LENGTH_LONG).show();
            return false;
        } else if (gender1 == null) {
            Toast.makeText(ProfileActivity.this, "Please select the gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (birth == null) {
            Toast.makeText(ProfileActivity.this, "Please enter the DOB", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
