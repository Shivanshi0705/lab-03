package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface Listener {
        void addCity(City city);
        void updateCity(int position, City city);
    }

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS = "arg_pos";

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    public static AddCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POS, position);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Listener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityFragment.Listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCity = view.findViewById(R.id.edit_text_city_text);
        EditText editProv = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        boolean isEdit = args != null && args.containsKey(ARG_CITY);
        int position = -1;

        if (isEdit) {
            City c = (City) args.getSerializable(ARG_CITY);
            position = args.getInt(ARG_POS, -1);
            if (c != null) {
                editCity.setText(c.getName());
                editProv.setText(c.getProvince());
            }
        } else {
            editCity.setText("");
            editProv.setText("");
        }

        int posFinal = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (d, w) -> {
                    String name = editCity.getText().toString();
                    String prov = editProv.getText().toString();
                    City result = new City(name, prov);
                    if (isEdit) {
                        listener.updateCity(posFinal, result);
                    } else {
                        listener.addCity(result);
                    }
                })
                .create();
    }
}
