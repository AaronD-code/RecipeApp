package com.example.recipeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RecipesFragment extends Fragment {

    public RecipesFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_recipes, container, false);

        ListView recipeList = view.findViewById(R.id.recipe_list);
        ArrayList<String> recipeNames = new ArrayList<>();

        try {
            StringBuilder json = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            requireContext().getAssets().open("recipes.json"),
                            StandardCharsets.UTF_8))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
            }

            JSONArray recipes = new JSONArray(json.toString());

            for (int i = 0; i < recipes.length(); i++) {
                recipeNames.add(
                        recipes.getJSONObject(i).getString("name"));
            }

            recipeList.setOnItemClickListener(
                    (parent, itemView, position, id) -> {

                        try {
                            org.json.JSONObject recipe =
                                    recipes.getJSONObject(position);

                            JSONArray ingredientArray =
                                    recipe.getJSONArray("ingredients");

                            ArrayList<String> ingredients =
                                    new ArrayList<>();

                            for (int i = 0;
                                 i < ingredientArray.length(); i++) {

                                ingredients.add(
                                        ingredientArray.getString(i));
                            }

                            IngredientsFragment detailFragment =
                                    IngredientsFragment.newInstance(
                                            recipe.getString("name"),
                                            ingredients,
                                            recipe.getString("method")
                                    );

                            boolean isLandscape =
                                    requireActivity().findViewById(
                                            R.id.detail_container) != null;

                            if (isLandscape) {
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(
                                                R.id.detail_container,
                                                detailFragment
                                        )
                                        .commit();
                            } else {
                                android.content.Intent intent =
                                        new android.content.Intent(
                                                requireContext(),
                                                IngredientsActivity.class
                                        );

                                intent.putExtra(
                                        "selected_recipe",
                                        detailFragment.getArguments()
                                );

                                startActivity(intent);
                            }

                        } catch (Exception e) {
                            android.util.Log.e(
                                    "RecipesFragment",
                                    "Cannot open recipe",
                                    e
                            );

                            Toast.makeText(
                                    requireContext(),
                                    "Could not open recipe",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });

        } catch (Exception e) {
            android.util.Log.e("RecipesFragment", "Cannot load recipes", e);

            Toast.makeText(
                    requireContext(),
                    "Could not load recipes",
                    Toast.LENGTH_LONG
            ).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                recipeNames
        );

        recipeList.setAdapter(adapter);

        return view;
    }
}