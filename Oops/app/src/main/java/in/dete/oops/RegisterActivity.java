package in.dete.oops;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private Button btnUser, btnDeliveryBoy, btnSignUp;
    private Button btnRegister;
    private ImageView btnBack;
    private EditText etFullName, etEmailLogin, etPhoneLogin, etPasswordLogin, etConfirmPassword;
    private String user_email, fullName, phone, user_password, user_confirm_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private int selection;//1 for User, 2 for Delivery Boy
    private GestureDetector gestureDetector;
    private TextView tvLogIn;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etName);
        etEmailLogin = findViewById(R.id.etEmailID);
        etPhoneLogin = findViewById(R.id.etPhone);
        etPasswordLogin = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnBack = findViewById(R.id.btnBack);
        btnUser = findViewById(R.id.btnUser);
        btnDeliveryBoy = findViewById(R.id.btnDeliveryBoy);
        selection = 1;
        update = getIntent().getBooleanExtra("update", false);

        tvLogIn = findViewById(R.id.tvLogIn);
        gestureDetector = new GestureDetector(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnRegister = findViewById(R.id.btnSignUp);

        btnRegister.setOnClickListener(v -> {
            CreateAccount();
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        tvLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        if (update) {
            findViewById(R.id.holderEmail).setVisibility(View.GONE);
            findViewById(R.id.holderPwd).setVisibility(View.GONE);
            findViewById(R.id.holderPwd2).setVisibility(View.GONE);
        }

    }

    private void CreateAccount() {

        user_email = etEmailLogin.getText().toString().trim();
        user_password = etPasswordLogin.getText().toString().trim();
        user_confirm_password = etConfirmPassword.getText().toString().trim();
        phone = etPhoneLogin.getText().toString().trim();
        fullName = etFullName.getText().toString().trim();

        if (Validate()) {
            progressDialog.setTitle("Creating Account...");
            progressDialog.setMessage("Please wait while we are checking the credentials...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            UpdateDetails(fullName, user_email, phone, user_password, selection);

                                            Toast.makeText(RegisterActivity.this, "Registered successfully, Please verify your email.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
    }

    //    private boolean ValidatePhone(String phone) {
//        final boolean[] flag = {false};
//        final DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
//        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!(dataSnapshot.child("Users").child(phone).exists())) {
//                    Toast.makeText(RegisterActivity.this, "This " + phone + "already exists", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Please try using another Phone Number", Toast.LENGTH_SHORT).show();
//                    flag[0] = false;
//                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//
//                } else {
//                    flag[0] = true;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        return flag[0];
//    }
//
    private void UpdateDetails(String fullName, String user_email, String phone, String user_password, int type) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", user_password);
                    userDataMap.put("name", fullName);
                    userDataMap.put("email", user_email);
                    userDataMap.put("type", selection);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Congratulations, your account is created", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Network error, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HashMap<String, Object> map = new HashMap<>();
        map.put("n", fullName);
        map.put("e", user_email);
        map.put("p", user_password);
        map.put("m", phone);
        map.put("s", type);
        User userData = new User(fullName, phone, user_email, FirebaseAuth.getInstance().getCurrentUser().getUid(), null, user_password, type, null);
        String json = new Gson().toJson(userData);

        getSharedPreferences("MyPrefs", MODE_PRIVATE).

                edit().

                putString("loggedInUser", json).

                apply();
        userData.setUid(FirebaseAuth.getInstance().

                getCurrentUser().

                getUid());
        FirebaseFirestore.getInstance().

                collection("users").

                document(FirebaseAuth.getInstance().

                        getCurrentUser().

                        getUid())
                .

                        set(map, SetOptions.merge())
                .

                        addOnCompleteListener(task ->

                        {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Utils.storeUserInfo(userData, RegisterActivity.this);
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.putExtra("user", userData);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else
                                Toast.makeText(RegisterActivity.this, "Couldn't setup profile, try again later", Toast.LENGTH_SHORT).show();
                        });
    }

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("n", fullName);
//        map.put("e", user_email);
//        map.put("t", type);
//        map.put("m", phone);
//        map.put("p", user_password);
//        rootref.child("Users").child(firebaseAuth.getCurrentUser().getUid()).updateChildren(map)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(RegisterActivity.this, "Details updated", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }

    private boolean Validate() {
        boolean result = false;

        String name = etFullName.getText().toString().trim();
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhoneLogin.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter full name", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty() || phone.length() != 10) {
            Toast.makeText(RegisterActivity.this, "Enter correct phone number", Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please re enter the password", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(confirmPassword))) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }
        return result;
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent moveEvent, float velocityX,
                           float velocityY) {
        boolean result = false;
        float diffX = moveEvent.getX() - motionEvent.getX();
        if (diffX > 100) {
            onSwipeLeft();
            result = true;
        }
        return result;
    }

    private void onSwipeLeft() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void onSwipeRight() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void toggle(View v) {
        if (v.getId() == R.id.btnUser) {
            selection = 1;
            btnUser.setBackground(getResources().getDrawable(R.drawable.shaperect, null));
            btnUser.setTextColor(getResources().getColor(android.R.color.white, null));
            btnDeliveryBoy.setBackground(getResources().getDrawable(R.drawable.shape, null));
            btnDeliveryBoy.setTextColor(getResources().getColor(android.R.color.black, null));
        } else if (v.getId() == R.id.btnDeliveryBoy) {
            selection = 2;
            btnDeliveryBoy.setBackground(getResources().getDrawable(R.drawable.shaperect, null));
            btnDeliveryBoy.setTextColor(getResources().getColor(android.R.color.white, null));
            btnUser.setBackground(getResources().getDrawable(R.drawable.shape, null));
            btnUser.setTextColor(getResources().getColor(android.R.color.black, null));
        }
    }

}
