package com.example.mylibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Home extends AppCompatActivity {
  //  private ActionBar toolbar;
 private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      toolbar=(Toolbar) findViewById(R.id.toolbarstud) ;
      setSupportActionBar(toolbar);

//        toolbar = getSupportActionBar();
        Log.i("task","work jgsaygfc egfufc");
        toolbar.setTitle("shop");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_UserProfile, R.id.navigation_notifications,R.id.navigation_Userlogout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
      NavigationUI. setupWithNavController(navView, navController);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    return true;

                case R.id.navigation_UserProfile:
                   toolbar.setTitle("User Profile");
                    return true;
                case R.id.navigation_notifications:
                   toolbar.setTitle("Notification");
                    return true;
                case R.id.navigation_Userlogout:
                    toolbar.setTitle("Logoout");
                    return true;
            }
            return false;
        }
    };
}
