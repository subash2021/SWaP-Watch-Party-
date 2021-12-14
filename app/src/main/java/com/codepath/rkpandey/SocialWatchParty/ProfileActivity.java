package com.codepath.rkpandey.SocialWatchParty;

import static com.codepath.rkpandey.SocialWatchParty.R.id.myimageview;
import static com.codepath.rkpandey.SocialWatchParty.R.id.selected;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.data.model.Users;
import com.codepath.rkpandey.SocialWatchParty.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
   // ActivityProfileBinding binding;
    TextView Fullname, Email;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String username;
    String email_add;
   // public static final int GALLERY_REQUEST_CODE = 105;

    ImageView selectedImage;

    Button changeprofile, changeinfo, SignOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        selectedImage = findViewById(myimageview);
        changeprofile = findViewById(R.id.changeprofile);
        changeinfo = findViewById(R.id.changeinfo);
        Fullname = findViewById(R.id.name2);
        Email = findViewById(R.id.email2);
        SignOutButton = findViewById(R.id.SignOutButton);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users users = snapshot.getValue(Users.class);
                            Picasso.get().load(users.getProfilepic())
                                    .placeholder(R.drawable.profile)
                                    .into(selectedImage);
                            Fullname.setText(users.getUserName());
                            Email.setText(users.getEmail());
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,EditProfile.class);
                startActivity(i);
            }
        });
        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//*/*
                startActivityForResult(intent, 33);
            }
        });
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            Uri sFile = data.getData();
            selectedImage.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child("profile_pictures")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(ProfileActivity.this, text  Toast)

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(@NonNull Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString());
                            Toast.makeText(ProfileActivity.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}





