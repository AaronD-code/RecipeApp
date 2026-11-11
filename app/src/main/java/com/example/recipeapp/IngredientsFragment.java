package com.example.recipeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    public IngredientsFragment() {
        // Required empty constructor
    }

    public static IngredientsFragment newInstance(
            String name,
            ArrayList<String> ingredients,
            String method) {

        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putStringArrayList("ingredients", ingredients);
        args.putString("method", method);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_ingredients, container, false);

        TextView nameView = view.findViewById(R.id.recipe_name);
        TextView ingredientsView =
                view.findViewById(R.id.recipe_ingredients);
        TextView methodView = view.findViewById(R.id.recipe_method);

        Bundle args = getArguments();

        if (args != null) {
            nameView.setText(args.getString("name", ""));
            methodView.setText(args.getString("method", ""));

            ArrayList<String> ingredients =
                    args.getStringArrayList("ingredients");

            StringBuilder ingredientText = new StringBuilder();

            if (ingredients != null) {
                for (String ingredient : ingredients) {
                    ingredientText.append("• ")
                            .append(ingredient)
                            .append("\n");
                }
            }

            ingredientsView.setText(ingredientText.toString().trim());
        }

        return view;
    }
}