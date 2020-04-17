package in.dete.oops;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
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

public class ProfileActivityD extends AppCompatActivity {

    private EditText fullname;
    private RadioGroup group;
    private String gender;
    private FirebaseUser firebaseUser;
    private User user;
    private EditText   email, phonenumber;
    private TextView save, birthdate, addId;
    private String name, phone, email1, birth, gender1;
    ImageView btnBack;
    private EditText aptAddress;
    public static int AUTOCOMPLETE_ACTIVITY = 180;
    private GeoPoint aptLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_d);

        group = findViewById(R.id.gender);
        int val = group.getCheckedRadioButtonId();
        if (val == -1) gender = "";
        else gender = ((RadioButton) findViewById(val)).getText().toString();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = Utils.fetchUserInfo(ProfileActivityD.this);
        fullname = findViewById(R.id.firstname);
        aptAddress = findViewById(R.id.aptAddress);

        btnBack = findViewById(R.id.btnBack);
        birthdate = findViewById(R.id.birthdate);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        save = findViewById(R.id.save);


        aptAddress.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getApplicationContext());

        });


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
                DatePickerDialog dp = new DatePickerDialog(ProfileActivityD.this, dplistener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.create();
                if (!isFinishing())
                    dp.show();
            }
        });

        User user = Utils.fetchUserInfo(ProfileActivityD.this);
        if (user != null) {
            fullname.setText(user.getName());
            phonenumber.setText(user.getPhone());
            email.setText(user.getEmail());
        }

        save.setOnClickListener(v1 -> {
            if (validate()) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                User user2 = new User(name, user.getPhone(), email1, FirebaseAuth.getInstance().getCurrentUser().getUid(), gender1, birth, 1);
                docRef.set(user2, SetOptions.merge()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Utils.storeUserInfo(user2, ProfileActivityD.this);
                        Toast.makeText(ProfileActivityD.this, "Your data has been updated", Toast.LENGTH_LONG).show();
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
            Toast.makeText(ProfileActivityD.this, "Please enter the name", Toast.LENGTH_LONG).show();
            return false;
        }
        name = Utils.formatName(name);
        fullname.setText(name);

        if (email1 == null) {
            Toast.makeText(ProfileActivityD.this, "Please enter the email", Toast.LENGTH_LONG).show();
            return false;
        } else if (gender1 == null) {
            Toast.makeText(ProfileActivityD.this, "Please select the gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (birth == null) {
            Toast.makeText(ProfileActivityD.this, "Please enter the DOB", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
