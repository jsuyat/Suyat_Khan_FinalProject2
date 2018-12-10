package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    void deleteDatabase(){
        context.deleteDatabase(NAME);
    }
}
