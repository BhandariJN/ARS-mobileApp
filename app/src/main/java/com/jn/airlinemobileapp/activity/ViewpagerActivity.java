package com.jn.airlinemobileapp.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jn.airlinemobileapp.R;
import com.jn.airlinemobileapp.fragments.BottomFragment;
import com.jn.airlinemobileapp.fragments.TopFragment;

public class ViewpagerActivity extends AppCompatActivity {

    TextView tab1, tab2, tab3;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        HamroPagerAdapter pagerAdapter = new HamroPagerAdapter(getSupportFragmentManager());
         viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab1.setBackgroundColor(Color.TRANSPARENT);
                tab2.setBackgroundColor(Color.TRANSPARENT);
                tab3.setBackgroundColor(Color.TRANSPARENT);

                if (position == 0) {


                    tab1.setBackgroundColor(Color.BLUE);
                } else if (position == 1) {

                    tab2.setBackgroundColor(Color.BLUE);
                } else if (position == 2) {

                    tab3.setBackgroundColor(Color.BLUE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void tabClickListener(View view) {
        tab1.setBackgroundColor(Color.TRANSPARENT);
        tab2.setBackgroundColor(Color.TRANSPARENT);
        tab3.setBackgroundColor(Color.TRANSPARENT);

        if (view.getId() == R.id.tab1) {
            viewPager.setCurrentItem(0);

            tab1.setBackgroundColor(Color.BLUE);
        } else if (view.getId() == R.id.tab2) {
            viewPager.setCurrentItem(1);
            tab2.setBackgroundColor(Color.BLUE);
        } else if (view.getId() == R.id.tab3) {
            viewPager.setCurrentItem(2);
            tab3.setBackgroundColor(Color.BLUE);
        }


    }

    public class HamroPagerAdapter extends FragmentPagerAdapter{

        public HamroPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new BottomFragment();
            }else if(position==1) {
                return new TopFragment();
            }
            return new BottomFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
