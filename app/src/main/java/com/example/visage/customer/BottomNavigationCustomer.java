package com.example.visage.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.visage.R;

public class BottomNavigationCustomer extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_customer);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_calendar));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_profile));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new com.example.visage.Customer.HomeFragment();
                        break;
                    case 2:
                        fragment = new com.example.visage.Customer.CalendarFragment();
                        break;
                    case 3:
                        fragment = new com.example.visage.Customer.CartFragment();
                        break;
                    case 4:
                        fragment = new com.example.visage.Customer.AccountFragment();
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


        //bottomNavigation.setCount(1,"");
        bottomNavigation.show(1,true);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}

