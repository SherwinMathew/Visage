package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.visage.R;

public class  Introductory_Activity extends AppCompatActivity {

    ImageView logo,appName,splashImg;
    LottieAnimationView lottieAnimationView;

    public static final int NUM_PAGES= 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo1);
        appName = findViewById(R.id.app_name);
        splashImg = findViewById(R.id.bg1);
        lottieAnimationView = findViewById(R.id.lottie);
        viewPager = findViewById(R.id.pager);
        pageAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());


        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                viewPager.setAdapter(pageAdapter);
            }
        }, 3000);


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{


        public ScreenSlidePagerAdapter(@NonNull FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    com.example.visage.Customer.OnBoardingFragment1 tab1 = new com.example.visage.Customer.OnBoardingFragment1();
                    return tab1;
                case 1:
                    com.example.visage.Customer.OnBoardingFragment2 tab2 = new com.example.visage.Customer.OnBoardingFragment2();
                    return tab2;
                case 2:
                    com.example.visage.Customer.OnBoardingFragment3 tab3 = new com.example.visage.Customer.OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}