package com.service.ekrishibazaar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.service.ekrishibazaar.fragments.HomeFragment;
import com.service.ekrishibazaar.fragments.MyAccountFragment;
import com.service.ekrishibazaar.fragments.MyAdsFragment;
import com.service.ekrishibazaar.fragments.HelpFregment;
import com.service.ekrishibazaar.util.LocaleHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navListener);
    }

    //bottom menu click events
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
//                        case R.id.navigation_chat:
//                            selectedFragment = new ChatFragment();
//                            break;
                        case R.id.navigation_sell:
                            selectedFragment = new HelpFregment();
                            break;
                        case R.id.navigation_my_adds:
                            selectedFragment = new MyAdsFragment();
                            break;
                        case R.id.navigation_myaccount:
                            selectedFragment = new MyAccountFragment();
                            break;
                        case R.id.navigation_chat:
                            Toast.makeText(DashboardActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                            selectedFragment = new MyAccountFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.searchview_menu, menu);
//        return true;
//    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
