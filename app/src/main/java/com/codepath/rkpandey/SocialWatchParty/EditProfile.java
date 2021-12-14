package com.codepath.rkpandey.SocialWatchParty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText profileFullName, profileEmail,profilePhone;
    Button saveBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String Fullname = data.getStringExtra("Fullname");
        String Email = data.getStringExtra("Email");
       String Phone = data.getStringExtra("Phone");



        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);
        saveBtn = findViewById(R.id.saveProfileInfo);
        database = FirebaseDatabase.getInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty()) {
                    // || profilePhone.getText().toString().isEmpty())
                    Toast.makeText(EditProfile.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                ///need to remove email update...authentication email not updating

                String email = profileEmail.getText().toString();
                String username = profileFullName.getText().toString();
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("userName", username);
                obj.put("email", email);
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);

                Intent intent = new Intent(EditProfile.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}