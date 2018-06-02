package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;
    TextView textView_orgin;
    TextView textView_also_known_as;
    TextView textView_ingredients;
    TextView textView_description;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);


        // Toolbar
        toolbar.setTitle(sandwich.getMainName());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        ingredientsIv = findViewById(R.id.image_iv);
        textView_orgin = findViewById(R.id.origin_tv);
        textView_also_known_as = findViewById(R.id.also_known_tv);
        textView_ingredients = findViewById(R.id.ingredients_tv);
        textView_description = findViewById(R.id.description_tv);


        textView_orgin.setText(sandwich.getPlaceOfOrigin()); // setting the text at TextView Orgin
        textView_description.setText(sandwich.getDescription());  // setting the text at TextView Description


        if(sandwich.getAlsoKnownAs().size() > 0) {

            for (int i = 0; i < sandwich.getAlsoKnownAs().size();i++){
                textView_also_known_as.append(sandwich.getAlsoKnownAs().get(i) + "\n"); // setting the text at TextView Also known as
            }

        }
        else {
            textView_also_known_as.setText("..."); // setting ... at TextView Also known as if no content is available
        }

        if (sandwich.getIngredients().size() > 0) {

            for (int i = 0; i < sandwich.getIngredients().size();i++){
                textView_ingredients.append(sandwich.getIngredients().get(i)  + "\n"); // setting the text at TextView Ingredients
            }

        }
        else {
            textView_ingredients.setText("..."); // setting ... at TextView Ingredients if no content is available
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
