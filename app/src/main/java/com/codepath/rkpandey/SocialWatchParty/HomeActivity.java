package com.codepath.rkpandey.SocialWatchParty;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.databinding.ActivityHomeBinding;
import com.codepath.rkpandey.SocialWatchParty.fragments.chat_fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    ActivityHomeBinding binding;
    String Fullname, Email, Phone;
    String searchInput, mRecyclerView, btnSearch;
    Button YoutubeButton;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START );
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment );
        Navigation.setViewNavController(navigationView, navController );

        ImageView youtubeImage = findViewById(R.id.youtubeImage);

        youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, YouTubeMainActivity.class));
                finish();
            }
        });
        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_home,new chat_fragment()).commit();
            }

        });
        binding.addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast TOAST = Toast.makeText(getApplicationContext(),"Feature Will Be Added In Future", Toast.LENGTH_LONG);
                TOAST.setMargin(30,30);
                TOAST.show();
            }
        });
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuProfile:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("Fullname", Fullname);
                intent.putExtra("Email",email);
                intent.putExtra("Phone",Phone);
                startActivity(intent);
                break;
        }

        switch (item.getItemId()){
            case R.id.menuYoutubee:
                Intent intent = new Intent(HomeActivity.this, YouTubeMainActivity.class);
                intent.putExtra("searchInput", searchInput);
                intent.putExtra("mRecyclerView", mRecyclerView);
                intent.putExtra("btnSearch",btnSearch);
                startActivity(intent);
                break;
        }

        switch (item.getItemId()){
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;

    }
}
