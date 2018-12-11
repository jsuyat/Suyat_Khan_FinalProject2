package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GroceryListDatabase extends SQLiteOpenHelper{
    final private static Integer VERSION = 1;
    final private Context context;

    final static String NAME = "Grocery_List";
    final private static String CREATE_CMD =
            "CREATE TABLE "+NAME+" ("  +
                "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ingredient VARCHAR(15)," +
                "food_group VARCHAR(9))" ;

    final private String _ID = "_id";
    final private String INGREDIENT = "ingredient";
    final private String FOODGROUP = "food_group";

    public GroceryListDatabase(Context context){
        super(context, "groceries_db", null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        ContentValues values = new ContentValues();

        values.put(INGREDIENT, "Banana");
        values.put(FOODGROUP, "Fruit");
        db.insert(NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public ArrayList<String> getMeatLabels(){
        ArrayList<String> meatLabels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT meat FROM " + NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    meatLabels.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return meatLabels;
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public ArrayList<String> getFruitLabels(){
        ArrayList<String> fruitLabels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT ingredient FROM "+NAME+" WHERE food_group = \"Fruit\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fruitLabels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return fruitLabels;
    }
}
