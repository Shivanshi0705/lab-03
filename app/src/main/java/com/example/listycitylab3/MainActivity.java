package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.Listener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ListyCity");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) dataList.add(new City(cities[i], provinces[i]));

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) ->
                AddCityFragment.newInstance(dataList.get(position), position)
                        .show(getSupportFragmentManager(), "Edit City"));

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                AddCityFragment.newInstance().show(getSupportFragmentManager(), "Add City"));
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int position, City city) {
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, city);
            cityAdapter.notifyDataSetChanged();
        }
    }
}
