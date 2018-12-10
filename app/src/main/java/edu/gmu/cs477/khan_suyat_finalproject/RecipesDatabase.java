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
    final private static String _ID = "_id";
    final private static String RECIPE = "recipe";
    final private static String MEAT = "meat";
    final private static String VEGETABLE = "vegetable";
    final private static String GRAIN = "grain";
    final private static String DAIRY = "dairy";
    final private static String FRUIT = "fruit";
    final private static String CALORIES = "calories";
    final private static String LINK = "link";
    final private static String CHECKED = "checked";

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
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public ArrayList<String> getMeatLabels(){
        ArrayList<String> meatLabels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT MEAT FROM " + RECIPES_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                meatLabels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return meatLabels;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
