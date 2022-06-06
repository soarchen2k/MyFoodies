package com.qizhou.myfoodies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.top_container);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        ListFragment listFragment = ListFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.left_container, listFragment).commit();

        // TabLayout 与 ViewPager 配合，在 ViewPager 底部显示 Title，需要在 Adapter 中重写 getPageTitle()
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String setting;
        ListFragment listFragment = ListFragment.getInstance();

        if (id == R.id.action_settings1) {
            setting = "lowtohigh";
        } else if (id == R.id.action_settings2) {
            setting = "hightolow";
        } else if (id == R.id.action_settings3) {
            setting = "rating";
        } else {
            setting = "vegan";
        }

        listFragment.setData(setting);
        return super.onOptionsItemSelected(item);
    }
}