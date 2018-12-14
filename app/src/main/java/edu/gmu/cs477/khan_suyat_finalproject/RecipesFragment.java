package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipesFragment extends Fragment {

    private SQLiteDatabase db = null;
    private RecipesDatabase recipeDBHelper = null;
    private GroceryListDatabase groceryDBHelper = null;
    android.widget.SimpleCursorAdapter myAdapter;
    ListView mlist;
    Cursor mCursor;
    Spinner mSpinner;
    Spinner vSpinner;
    Spinner gSpinner;
    Spinner dSpinner;
    Spinner fSpinner;
    android.app.AlertDialog actions;
    Button findButton;
    View view;
    private String link;
    private String _id;

    //variables used to query the database
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
        db = recipeDBHelper.getWritableDatabase();
        mCursor = db.query(recipeDBHelper.RECIPES_NAME, all_columns,null,null,null,null,null);
        myAdapter = new android.widget.SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[]{RECIPE},
                new int[]{android.R.id.text1});

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        /*if user wants to cook this recipe
                         *it will updated "checked" in the database
                         *and go to its respective link*/
                        SQLiteDatabase db = recipeDBHelper.getWritableDatabase();
                        String filter = "_id=" + _id;
                        ContentValues values = new ContentValues();
                        values.put(recipeDBHelper.CHECKED, 1);
                        db.update(recipeDBHelper.RECIPES_NAME, values, filter, null);
                        db.close();

                        Uri uri = Uri.parse(link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to have this meal?");
        String[] options = {"Go to Recipe"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
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
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipe = ((TextView) view).getText().toString();
                String selectQuery = "SELECT _id, link FROM "+recipeDBHelper.RECIPES_NAME+" WHERE recipe = \"" +recipe+"\"";
                SQLiteDatabase db = recipeDBHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                _id = "";
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    _id = cursor.getString(0);
                    link = cursor.getString(1);
                }

                // closing connection
                cursor.close();
                db.close();

                actions.show();
            }
        });


        mSpinner = (Spinner) getView().findViewById(R.id.meatSpinner);
        vSpinner = (Spinner) getView().findViewById(R.id.vegetableSpinner);
        gSpinner = (Spinner) getView().findViewById(R.id.grainSpinner);
        dSpinner = (Spinner) getView().findViewById(R.id.dairySpinner);
        fSpinner = (Spinner) getView().findViewById(R.id.fruitSpinner);
        loadSpinnerData();

        findButton = (Button) getView().findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = recipeDBHelper.getWritableDatabase();
                String selection = "meat = ? AND vegetable = ? AND dairy = ? AND grain = ? AND fruit = ?";
                String meatInput = mSpinner.getSelectedItem().toString();
                String vegInput = vSpinner.getSelectedItem().toString();
                String dairyInput = dSpinner.getSelectedItem().toString();
                String grainInput = gSpinner.getSelectedItem().toString();
                String fruitInput = fSpinner.getSelectedItem().toString();
                mCursor = db.query(recipeDBHelper.RECIPES_NAME, all_columns,selection,
                        new String[]{meatInput, vegInput, dairyInput, grainInput, fruitInput},null,null,null);
                myAdapter = new android.widget.SimpleCursorAdapter(getContext(),
                        android.R.layout.simple_list_item_1,
                        mCursor,
                        new String[]{RECIPE},
                        new int[]{android.R.id.text1});
                mlist.setAdapter(myAdapter);
            }
        });

    }

    //loads the ingredients to its respective spinners
    private void loadSpinnerData(){
        ArrayList<String> meatLabels = groceryDBHelper.getMeatLabels();
        ArrayAdapter<String> meatAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, meatLabels);
        mSpinner.setAdapter(meatAdapter);

        ArrayList<String> vegetableLabels = groceryDBHelper.getVegetableLabels();
        ArrayAdapter<String> vegetableAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vegetableLabels);
        vSpinner.setAdapter(vegetableAdapter);

        ArrayList<String> grainLabels = groceryDBHelper.getGrainLabels();
        ArrayAdapter<String> grainAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, grainLabels);
        gSpinner.setAdapter(grainAdapter);

        ArrayList<String> dairyLabels = groceryDBHelper.getDairyLabels();
        ArrayAdapter<String> dairyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dairyLabels);
        dSpinner.setAdapter(dairyAdapter);

        ArrayList<String> fruitLabels = groceryDBHelper.getFruitLabels();
        ArrayAdapter<String> fruitAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fruitLabels);
        fSpinner.setAdapter(fruitAdapter);
    }

    public void onRecipeClicked(String recipe){

        String selectQuery = "SELECT _id, link FROM "+recipeDBHelper.RECIPES_NAME+" WHERE recipe = \"" +recipe+"\"";
        SQLiteDatabase db = recipeDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String id = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            id = cursor.getString(0);
            link = cursor.getString(1);
        }


        String filter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put(recipeDBHelper.CHECKED, 1);
        db.update(recipeDBHelper.RECIPES_NAME, values, filter, null);

        // closing connection
        cursor.close();
        db.close();

        actions.show();
    }
}
