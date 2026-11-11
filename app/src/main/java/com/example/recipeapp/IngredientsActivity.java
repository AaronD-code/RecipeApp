package com.example.recipeapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle recipe = getIntent().getBundleExtra("selected_recipe");

        // Return to the two-panel screen when rotated to landscape.
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selected_recipe", recipe);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingredients);

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

        if (savedInstanceState == null) {
            IngredientsFragment fragment = new IngredientsFragment();
            fragment.setArguments(recipe);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ingredients_container, fragment)
                    .commit();
        }
    }
}