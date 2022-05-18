package com.example.visage.Merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.visage.Customer.AccountFragment;
import com.example.visage.R;

public class BottomNavigationMerchant extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_merchant);

        bottomNavigation = findViewById(R.id.bottom_navigation2);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        //bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_calendar));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_analytics_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_profile));

        Fragment home = new com.example.visage.Merchant.MerchantDashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout2, home, "");
        fragmentTransaction.commit();


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new com.example.visage.Merchant.MerchantDashboardFragment();
                        break;
                    case 2:
                        fragment = new com.example.visage.Merchant.MerchantAnalyticsFragment();
                        break;
                    case 3:
                        fragment = new MerchantProfileFragment();
                        break;
                }

                loadFragment(fragment);
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.show(1,true);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout2,fragment)
                .commit();
    }

}