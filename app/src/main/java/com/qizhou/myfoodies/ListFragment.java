package com.qizhou.myfoodies;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qizhou.myfoodies.entities.DataSource;
import com.qizhou.myfoodies.entities.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListFragment extends Fragment {

    private static ListFragment listFragment;
    public RestaurantAdapter adapter;
    ListView listView;
    Context context;
    List<Restaurant> restaurantList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        restaurantList = DataSource.getRestaurantList();

        listView = v.findViewById(R.id.list_restaurant);
        context = inflater.getContext();
        adapter = new RestaurantAdapter(context, restaurantList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(inflater.getContext(), restaurantList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(String setting) {
        restaurantList = getRestaurantList(setting);
        adapter = new RestaurantAdapter(context, restaurantList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Restaurant> getRestaurantList(String setting) {
        List<Restaurant> restaurantList = new ArrayList<>();
        if (setting.equals("vegan")) {
            for (Restaurant r : DataSource.getRestaurantList()) {
                if (r.isVegetarian()) {
                    restaurantList.add(r);
                }
            }
        } else {
            restaurantList = DataSource.getRestaurantList();
            if (setting.equals("lowtohigh")) {
                restaurantList.sort(Comparator.comparingInt(Restaurant::getMinPrice));
            } else if (setting.equals("hightolow")) {
                restaurantList.sort((o1, o2) -> o2.getMinPrice() - o1.getMinPrice());
            } else {
                restaurantList.sort((o1, o2) -> o2.getRating().compareTo(o1.getRating()));
            }
        }
        return restaurantList;
    }

    public static ListFragment getInstance() {
        if (listFragment == null) {
            listFragment = new ListFragment();
        }
        return listFragment;
    }

    private static class RestaurantAdapter extends BaseAdapter {
        Context context;
        List<Restaurant> restaurantList;

        public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
            this.context = context;
            this.restaurantList = restaurantList;
        }

        @Override
        public int getCount() {
            return restaurantList.size();
        }

        @Override
        public Object getItem(int position) {
            return restaurantList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.restaurant_list, parent, false);
            }

            Restaurant restaurant = restaurantList.get(position);

            ImageView imageViewRestaurantImage = convertView.findViewById(R.id.list_restaurant_image);
            imageViewRestaurantImage.setImageResource(restaurant.getBannerImage());

            TextView retaurantName = convertView.findViewById(R.id.restaurant_list_name);
            retaurantName.setText(String.format("%s, %s, %s", restaurant.getName(), restaurant.getAddress1(), restaurant.getCity()));

            TextView restaurantRate = convertView.findViewById(R.id.restaurant_list_rate);
            restaurantRate.setText(String.valueOf(restaurant.getRating()));

            TextView veg = convertView.findViewById(R.id.list_restaurant_vegetarian);
            if (restaurant.isVegetarian()) {
                veg.setText("     ");
                veg.setBackgroundColor(Color.GREEN);
            } else {
                veg.setText("");
                veg.setBackgroundColor(Color.WHITE);
            }

            TextView delivery = convertView.findViewById(R.id.restaurant_list_delivery);
            delivery.setText(String.format("$%s Delivery Fee - %s min", restaurant.getDeliveryFee(), restaurant.getDeliveryTime()));

            return convertView;
        }
    }
}