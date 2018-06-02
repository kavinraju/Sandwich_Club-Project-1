package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();


        try {
            JSONObject jsonObject_main = new JSONObject(json);

            JSONObject jsonObject_name = jsonObject_main.getJSONObject("name");
            sandwich.setMainName(jsonObject_name.getString("mainName"));  // mainName

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray jsonArray_alsoKnownAs = jsonObject_name.getJSONArray("alsoKnownAs");
            if (jsonArray_alsoKnownAs != null && jsonArray_alsoKnownAs.length() > 0) {
                for (int i = 0; i < jsonArray_alsoKnownAs.length() - 1 ; i++) {

                        alsoKnownAs.add(jsonArray_alsoKnownAs.getString(i));

                }
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);  // alsoKnownAs

            sandwich.setPlaceOfOrigin(jsonObject_main.getString("placeOfOrigin")); // Place of orgin
            sandwich.setDescription(jsonObject_main.getString("description"));    // Description
            sandwich.setImage(jsonObject_main.getString("image"));   // Image URL

            List<String> ingredients  = new ArrayList<>();
            JSONArray jsonArray_ingredients = jsonObject_main.getJSONArray("ingredients");
            if (jsonArray_ingredients != null && jsonArray_ingredients.length() > 0) {

                for (int i = 0; i < jsonArray_ingredients.length() - 1; i++) {

                    ingredients.add(jsonArray_ingredients.getString(i));
                }
            }
            sandwich.setIngredients(ingredients);   // Ingredients

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sandwich;
    }
}
