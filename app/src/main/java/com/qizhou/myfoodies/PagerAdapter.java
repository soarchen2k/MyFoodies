package com.qizhou.myfoodies;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qizhou.myfoodies.entities.DataSource;
import com.qizhou.myfoodies.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PagerAdapter extends FragmentPagerAdapter {
    private List<Restaurant> restaurantList = getFeaturedRestaurants();

    private List<Restaurant> getFeaturedRestaurants() {
        if (this.restaurantList == null) {
            restaurantList = new ArrayList<>();
            for (Restaurant r : DataSource.getRestaurantList()) {
                if (r.isFeatured()) {
                    restaurantList.add(r);
                }
            }
        }

        return restaurantList;
    }

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Restaurant restaurant = restaurantList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("restaurantname", restaurant.getName());
        bundle.putString("restaurantaddress", restaurant.getAddress1());
        bundle.putInt("image", restaurant.getBannerImage());

        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (position == 0) {
//            return "Bird";
//        } else if (position == 1) {
//            return "Pizza";
//        } else {
//            return "TEst";
//        }
//    }
}
