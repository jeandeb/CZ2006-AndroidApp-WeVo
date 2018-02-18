package com.example.deversity.wevo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.deversity.wevo.R;
import com.example.deversity.wevo.mgr.SectionsPagerAdapter;
import com.example.deversity.wevo.mgr.VolunteerClientMgr;

/**
 * VolunteerView is a boundary class that contains four tab fragments
 * @author John; Fu, Yunhao
 */
public final class VolunteerView extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private mapTab mapTab;
    private listTab listTab;
    private eventTab eventTab;
    private userTab userTab;
    private final static VolunteerClientMgr volunteerMgr = new VolunteerClientMgr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerview);
        mapTab = new mapTab();
        listTab = new listTab();
        eventTab = new eventTab();
        userTab = new userTab();
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter msectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        msectionsPagerAdapter.addFragment(mapTab, "MAP");
        msectionsPagerAdapter.addFragment(listTab,"VWO");
        msectionsPagerAdapter.addFragment(eventTab,"EVENTS");
        msectionsPagerAdapter.addFragment(userTab,"USER");
        viewPager.setAdapter(msectionsPagerAdapter);
    }

    public static VolunteerClientMgr getVolunteerMgr() {
        return volunteerMgr;
    }

    @Override
    public void onBackPressed() {

    }
}