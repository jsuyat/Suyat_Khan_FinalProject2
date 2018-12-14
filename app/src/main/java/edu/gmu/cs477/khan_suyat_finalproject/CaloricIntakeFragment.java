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

    public static int total;
    public static int current;

    Cursor mCursor;
    Cursor mCursor2;

    private RecipesDatabase recipeDBHelper = null;
    private SQLiteDatabase db = null;

    android.widget.SimpleCursorAdapter myAdapter;
    android.widget.SimpleCursorAdapter myAdapter2;

    ListView mlist;
    ListView mlist2;


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

        total = 2000;
        current = 0;

        db = recipeDBHelper.getWritableDatabase();
        mCursor = db.query(recipeDBHelper.RECIPES_NAME, all_columns,null,null,null,null,null);

        db = recipeDBHelper.getWritableDatabase();
        String selectQuery = "SELECT _id, calories FROM "+recipeDBHelper.RECIPES_NAME+" WHERE checked = 0";
        mCursor2 = db.rawQuery(selectQuery, null);

        myAdapter = new android.widget.SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[]{RECIPE},
                new int[]{android.R.id.text1});

        myAdapter2 = new android.widget.SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                mCursor2,
                new String[]{CALORIES},
                new int[]{android.R.id.text1});





        while(mCursor2.moveToNext()) {

            int index;
            index = mCursor2.getColumnIndexOrThrow(CALORIES);

            current += mCursor2.getInt(index);

            System.out.println("HEY: " + mCursor2.getInt(index));



        }

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
        mlist = (ListView) getView().findViewById(R.id.mealList);
        mlist.setAdapter(myAdapter);

        mlist2 = (ListView) getView().findViewById(R.id.calList);
        mlist2.setAdapter(myAdapter2);


        TextView cur = (TextView) getView().findViewById(R.id.totalValue);
        cur.setText(String.valueOf(current));

        int r = total - current;

        TextView remaining = (TextView) getView().findViewById(R.id.remainingValue);
        remaining.setText(String.valueOf(r));



    }


    public void updateCalories()
    {

        TextView cur = (TextView) getView().findViewById(R.id.totalValue);
        cur.setText(String.valueOf(current));

        int r = total - current;

        TextView remaining = (TextView) getView().findViewById(R.id.remainingValue);
        remaining.setText(String.valueOf(r));

    }


    @Override
    public void onResume() {
        super.onResume();

        TextView cur = (TextView) getView().findViewById(R.id.totalValue);
        cur.setText(String.valueOf(current));

        int r = total - current;

        TextView remaining = (TextView) getView().findViewById(R.id.remainingValue);
        remaining.setText(String.valueOf(r));
    }
}
