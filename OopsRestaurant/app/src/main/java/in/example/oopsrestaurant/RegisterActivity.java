package in.example.oopsrestaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, aptAddress;
    public static int AUTOCOMPLETE_ACTIVITY = 180;
    private GeoPoint aptLoc;
    private ImageView imageuploader;
    private String name;
    private SwipeButton interest;
    private ArrayList<String> url;
    private String adr;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName= findViewById(R.id.etName);
        aptAddress = findViewById(R.id.aptAddress);
        interest = findViewById(R.id.showInterest);
        imageuploader = findViewById(R.id.imageuploader);
        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        url = new ArrayList<>();

        name = etName.getText().toString().trim();
//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
//        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());
//        try {
//            Places.initialize(getContext(), "AIzaSyCsTJ7aqC8aL0vdjFal4dob2NRp3BAxzj0");
//        }
//        catch (Exception e){}
        aptAddress.setOnClickListener(v ->{
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        });
//                startActivityForResult(new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields)
//                        .setCountry("IN")
//                        .build(getContext()), AUTOCOMPLETE_ACTIVITY));

        interest.setOnActiveListener(() -> {
            HashMap<String, Object> address = new HashMap<>();
            address.put("add", adr);
            address.put("name", name);
            FirebaseFirestore.getInstance().collection("Restaurants").document("profile").set(address);
            Intent intent = new Intent(RegisterActivity.this, DishesActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        imageuploader.setOnClickListener(v -> {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(RegisterActivity.this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                progressDialog.setMessage("Uploading image ...");
                progressDialog.setTitle("");
                progressDialog.show();
                StorageReference filepath = storageReference.child("image").child(System.currentTimeMillis() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        filepath.getDownloadUrl().addOnCompleteListener(taskUrl -> {
                            progressDialog.dismiss();
                            if (taskUrl.isSuccessful()) {
                                url.add(taskUrl.getResult().toString());
                                ImageView img = new ImageView(RegisterActivity.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dpToPx(80), Utils.dpToPx(100));
                                params.setMarginEnd(24);
                                img.setLayoutParams(params);
                                img.setImageURI(result.getUri());
                                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                img.getLayoutParams().height = Utils.dpToPx(100);
                                img.getLayoutParams().width = Utils.dpToPx(80);
                                img.requestLayout();
                                LinearLayout ll = findViewById(R.id.imagelayout);
                                ll.addView(img, ll.getChildCount() - 1);
                            } else
                                Toast.makeText(RegisterActivity.this, "Failed to add image", Toast.LENGTH_SHORT).show();
                        });
                    } else
                        Toast.makeText(RegisterActivity.this, "Failed to add image", Toast.LENGTH_SHORT).show();
                });
            }
        } else if (requestCode == AUTOCOMPLETE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                aptAddress.setText(place.getName());
                aptLoc = new GeoPoint(place.getLatLng().latitude, place.getLatLng().longitude);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("Address", status.getStatusMessage());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
