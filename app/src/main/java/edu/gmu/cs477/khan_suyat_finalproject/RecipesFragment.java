package edu.gmu.cs477.khan_suyat_finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RecipesFragment extends Fragment {

    private SQLiteDatabase db = null;
    private RecipesDatabase recipeDBHelper = null;
    private GroceryListDatabase groceryDBHelper = null;
    android.widget.SimpleCursorAdapter myAdapter;
    ListView mlist;
    Cursor mCursor;
    Spinner mSpinner;
    Spinner fSpinner;
    android.app.AlertDialog actions;
    View view;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDBHelper = new RecipesDatabase(getContext());
        groceryDBHelper = new GroceryListDatabase(getContext());
        System.out.println("In On create");
        db = recipeDBHelper.getWritableDatabase();
        mCursor = db.query(recipeDBHelper.RECIPES_NAME, all_columns,null,null,null,null,null);
        myAdapter = new android.widget.SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[]{RECIPE},
                new int[]{android.R.id.text1});

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlist = (ListView) getView().findViewById(R.id.recipe_list);
        mlist.setAdapter(myAdapter);

        mSpinner = (Spinner) getView().findViewById(R.id.meatSpinner);
        fSpinner = (Spinner) getView().findViewById(R.id.fruitSpinner);
        loadSpinnerData();
    }

    private void loadSpinnerData(){
        //ArrayList<String> meatLabels = groceryDBHelper.getMeatLabels();
        //ArrayAdapter<String> meatAdapter = new ArrayAdapter<String>(getContext(),
        //        android.R.layout.simple_spinner_item, meatLabels);
        //mSpinner.setAdapter(meatAdapter);
        ArrayList<String> fruitLabels = groceryDBHelper.getFruitLabels();
        ArrayAdapter<String> fruitAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fruitLabels);
        fSpinner.setAdapter(fruitAdapter);
    }

}
