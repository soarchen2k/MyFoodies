package com.qizhou.myfoodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhou.myfoodies.entities.Restaurant;

public class ViewPagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ImageView imageViewRestaurantBanner = v.findViewById(R.id.fragment_image);
        TextView textViewRestaurantName = v.findViewById(R.id.restaurant_name);
        TextView textViewRestaurantAddress = v.findViewById(R.id.restaurant_address);

        if (getArguments() != null) {
            int key = getArguments().getInt("image");
            imageViewRestaurantBanner.setImageResource(key);

            String restaurantName = getArguments().getString("restaurantname");
            textViewRestaurantName.setText(restaurantName);

            String restaurantAddress = getArguments().getString("restaurantaddress");
            textViewRestaurantAddress.setText(restaurantAddress);
        }
        return v;
    }
}