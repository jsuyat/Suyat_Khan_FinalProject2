package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipesDatabase extends SQLiteOpenHelper {

    final private static Integer VERSION = 1;
    final private Context context;

    final static String RECIPES_NAME = "Recipes_List";
    final static String _ID = "_id";
    final static String RECIPE = "recipe";
    final static String MEAT = "meat";
    final static String VEGETABLE = "vegetable";
    final static String GRAIN = "grain";
    final static String DAIRY = "dairy";
    final static String FRUIT = "fruit";
    final static String CALORIES = "calories";
    final static String LINK = "link";
    final static String CHECKED = "checked";

    final private static String CREATE_CMD =
            "CREATE TABLE "+RECIPES_NAME+" ("  +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RECIPE + " VARCHAR(40)," +
                    MEAT + " VARCHAR(10)," +
                    VEGETABLE + " VARCHAR(10)," +
                    GRAIN + " VARCHAR(10)," +
                    DAIRY + " VARCHAR(10)," +
                    FRUIT + " VARCHAR(10)," +
                    CALORIES + " INTEGER," +
                    LINK + " VARCHAR(150)," +
                    CHECKED + " INTEGER)";




    public RecipesDatabase(Context context){
        super(context, "recipes_db", null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        ContentValues values = new ContentValues();

        values.put(RECIPE, "Chicken Parmesan");
        values.put(MEAT, "Chicken");
        values.put(VEGETABLE, "Basil");
        values.put(GRAIN, "Spaghetti");
        values.put(DAIRY, "Cheese");
        values.put(FRUIT, "Tomatoes");
        values.put(CALORIES, 471);
        values.put(LINK, "https://www.foodnetwork.com/recipes/food-network-kitchen/general-tsos-chicken-recipe-3361885");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Chicken Fried Rice");
        values.put(MEAT, "Chicken");
        values.put(VEGETABLE, "Carrots");
        values.put(GRAIN, "Rice");
        values.put(DAIRY, "Eggs");
        values.put(FRUIT, "None");
        values.put(CALORIES, 484);
        values.put(LINK, "https://www.foodnetwork.com/recipes/robert-irvine/chicken-fried-rice-recipe-1952257");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "General Tso's Chicken");
        values.put(MEAT, "Chicken");
        values.put(VEGETABLE, "Garlic");
        values.put(GRAIN, "Rice");
        values.put(DAIRY, "Egg");
        values.put(FRUIT, "None");
        values.put(CALORIES, 865);
        values.put(LINK, "https://www.allrecipes.com/recipe/197394/joes-general-tsos-chicken/?internalSource=hub%20recipe&referringContentType=Search");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Beef Bulgogi");
        values.put(MEAT, "Beef");
        values.put(VEGETABLE, "Green Onions");
        values.put(GRAIN, "Rice");
        values.put(DAIRY, "None");
        values.put(FRUIT, "None");
        values.put(CALORIES, 232);
        values.put(LINK, "https://www.allrecipes.com/recipe/100606/beef-bulgogi/?internalSource=hub%20recipe&referringContentType=Search");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Pot Roast");
        values.put(MEAT, "Beef");
        values.put(VEGETABLE, "Carrots");
        values.put(GRAIN, "Flour");
        values.put(DAIRY, "Heavy Cream");
        values.put(FRUIT, "None");
        values.put(CALORIES, 243);
        values.put(LINK, "https://www.allrecipes.com/recipe/162091/awesome-red-wine-pot-roast/?internalSource=streams&referringId=1469&referringContentType=Recipe%20Hub&clickId=st_trending_b");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Brisket");
        values.put(MEAT, "Beef");
        values.put(VEGETABLE, "Onion");
        values.put(GRAIN, "Buns");
        values.put(DAIRY, "None");
        values.put(FRUIT, "Tomatoes");
        values.put(CALORIES, 455);
        values.put(LINK, "https://www.allrecipes.com/recipe/231030/braised-corned-beef-brisket/?internalSource=hub%20recipe&referringContentType=Search");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Paella");
        values.put(MEAT, "Shrimp");
        values.put(VEGETABLE, "Onion");
        values.put(GRAIN, "Rice");
        values.put(DAIRY, "None");
        values.put(FRUIT, "None");
        values.put(CALORIES, 524);
        values.put(LINK, "https://www.allrecipes.com/recipe/12728/paella-i/?internalSource=streams&referringId=17849&referringContentType=Recipe%20Hub&clickId=st_trending_b");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Lobster Mac and Cheese");
        values.put(MEAT, "Lobster");
        values.put(VEGETABLE, "Onion");
        values.put(GRAIN, "Macaroni");
        values.put(DAIRY, "Cheese");
        values.put(FRUIT, "None");
        values.put(CALORIES, 913);
        values.put(LINK, "https://www.foodnetwork.com/recipes/food-network-kitchen/general-tsos-chicken-recipe-3361885");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);

        values = new ContentValues();
        values.put(RECIPE, "Fish Tacos");
        values.put(MEAT, "Fish");
        values.put(VEGETABLE, "Cabbage");
        values.put(GRAIN, "Tortillas");
        values.put(DAIRY, "Mayonnaise");
        values.put(FRUIT, "Lime");
        values.put(CALORIES, 409);
        values.put(LINK, "https://www.allrecipes.com/recipe/53729/fish-tacos/?internalSource=hub%20recipe&referringContentType=Search");
        values.put(CHECKED, 0);
        db.insert(RECIPES_NAME, null, values);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
