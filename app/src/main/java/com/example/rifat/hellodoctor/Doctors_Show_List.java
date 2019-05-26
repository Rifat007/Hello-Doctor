package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Doctors_Show_List extends AppCompatActivity {

    private TabLayout my_tl;
    private ViewPager my_vp;
    public static String hosName1,catagory_name1,datee1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__show__list);


        hosName1=getIntent().getStringExtra("HospitalAddress");
        catagory_name1=getIntent().getStringExtra("CATAGORY");
        datee1=getIntent().getStringExtra("DATE");

        my_vp = (ViewPager) findViewById(R.id.my_view_pagers);
        my_tl = (TabLayout) findViewById(R.id.my_tabbs);

        setUpMyViewPager(my_vp);
        my_tl.setupWithViewPager(my_vp);
    }

    void setUpMyViewPager(ViewPager vp){

        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vpa.addMyFragment(new FragmentOne(), "View Doctors");
        vpa.addMyFragment(new FragmentTwo(), "View by Ratings");
        //vpa.addMyFragment(new FragmentThree(), "ImRubel");

        vp.setAdapter(vpa);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mf=getMenuInflater();
        mf.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.lgoutId)
        {
            startActivity(new Intent(getApplicationContext(),DocPatSelection.class));
        }
        if(item.getItemId()==R.id.homeeId)
        {
            startActivity(new Intent(getApplicationContext(),Navi.class));
        }
        return super.onOptionsItemSelected(item);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> my_list = new ArrayList<Fragment>();

        private final List<String> my_titles = new ArrayList<String>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return my_list.get(position);
        }


        @Override
        public int getCount() {
            return my_list.size();
        }

        void addMyFragment(Fragment f, String title){
            my_list.add(f);
            my_titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return my_titles.get(position);
        }


    }
}
