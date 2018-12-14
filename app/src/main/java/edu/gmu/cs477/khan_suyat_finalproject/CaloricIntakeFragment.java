package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class CaloricIntakeFragment extends Fragment {

    public static int total = 2000;
    public static int current = 0;

    Cursor mCursor;
    Cursor mCursor2;

    private RecipesDatabase recipeDBHelper = null;
    private SQLiteDatabase db = null;

    android.widget.SimpleCursorAdapter myAdapter;

    ListView mlist;


    final static String _ID = "_id";
    final private static String RECIPE = "recipe";
    final private static String MEAT = "meat";
    final private static String VEGETABLE = "vegetable";
    final private static String GRAIN = "grain";
    final private static String DAIRY = "dairy";
    final private static String FRUIT = "fruit";
    final private static String CALORIES = "calories";
    final private static String LINK = "link";
    final private static String CHECKED = "checked";

    final static String[] all_columns = {_ID, RECIPE, MEAT, VEGETABLE, GRAIN, DAIRY, FRUIT, CALORIES, LINK, CHECKED};

    public final int ACTIVITY_RESULT = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDBHelper = new RecipesDatabase(getContext());
        System.out.println("In On create");
        System.out.println("Total: " + total);



        db = recipeDBHelper.getWritableDatabase();
        mCursor = db.query(recipeDBHelper.RECIPES_NAME, all_columns,null,null,null,null,null);


        //get meals that have been selected
        String selectQuery = "SELECT _id, recipe, calories FROM "+recipeDBHelper.RECIPES_NAME+" WHERE checked = 1";
        mCursor2 = db.rawQuery(selectQuery, null);


        //display meal calorie breakdown
        myAdapter = new android.widget.SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_2,
                mCursor,
                new String[]{RECIPE,CALORIES},
                new int[]{android.R.id.text1, android.R.id.text2});


        current = 0;    //reset calculation

        //total up calories
        while(mCursor2.moveToNext() ) {

            int index;
            index = mCursor2.getColumnIndexOrThrow(CALORIES);   //go to calorie column

            current += mCursor2.getInt(index);  //get calorie value and add it to current

            System.out.println("HEY: " + mCursor2.getInt(index));

        }
        mCursor2.close();   //close cursor
        System.out.println("CURRENT: " + current);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calorie, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set adapter for list
        mlist = (ListView) getView().findViewById(R.id.mealList);
        mlist.setAdapter(myAdapter);

        //update total calories
        TextView cur = (TextView) getView().findViewById(R.id.totalValue);
        cur.setText(String.valueOf(current));

        //update remaining for the day
        TextView remaining = (TextView) getView().findViewById(R.id.remainingValue);
        remaining.setText(String.valueOf(total-current));



    }


    @Override
    public void onResume() {
        super.onResume();

        //update on resume
        TextView cur = (TextView) getView().findViewById(R.id.totalValue);
        cur.setText(String.valueOf(current));
        TextView remaining = (TextView) getView().findViewById(R.id.remainingValue);
        remaining.setText(String.valueOf(total-current));
    }
}
