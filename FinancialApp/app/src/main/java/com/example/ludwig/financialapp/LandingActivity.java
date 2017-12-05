package com.example.ludwig.financialapp;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import layout.ExpenceFragment;
import layout.IncomeFragment;
import layout.TransactionFragment;

public class LandingActivity extends AppCompatActivity {
    //private TextView welcomeTextView;
    private static final String TAG = "LandingActivity";

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //initViews();

    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionFragment(), "Transactions");
        adapter.addFragment(new ExpenceFragment(), "Expence");
        adapter.addFragment(new IncomeFragment(), "Income");
        viewPager.setAdapter(adapter);
    }

    //private void initViews(){
    //    welcomeTextView = (TextView) findViewById(R.id.welcomeText);
    //}

}
