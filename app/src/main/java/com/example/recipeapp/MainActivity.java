package com.example.recipeapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (view, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        boolean isLandscape =
                findViewById(R.id.detail_container) != null;

        FragmentManager manager = getSupportFragmentManager();

        // Remember the recipe displayed before rotation.
        Bundle selectedRecipe = null;

        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof IngredientsFragment
                    && fragment.getArguments() != null) {

                selectedRecipe = new Bundle(fragment.getArguments());
            }
        }

        // Clear the old navigation before rebuilding the layout.
        manager.popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
        );

        FragmentTransaction reset = manager.beginTransaction();

        for (Fragment fragment : manager.getFragments()) {
            reset.remove(fragment);
        }

        reset.commitNow();

        // Always show the recipe list first.
        manager.beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new RecipesFragment()
                )
                .commitNow();

        if (isLandscape) {
            IngredientsFragment details = new IngredientsFragment();
            details.setArguments(selectedRecipe);

            manager.beginTransaction()
                    .replace(R.id.detail_container, details)
                    .commitNow();

        } else if (selectedRecipe != null) {
            IngredientsFragment details = new IngredientsFragment();
            details.setArguments(selectedRecipe);

            manager.beginTransaction()
                    .replace(R.id.fragment_container, details)
                    .addToBackStack(null)
                    .commit();
        }
    }
}