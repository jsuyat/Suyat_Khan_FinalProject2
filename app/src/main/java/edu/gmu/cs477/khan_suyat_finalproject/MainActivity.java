package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

//Mohammad Khan

    public final static String RET_INGREDIENT = "edu.gmu.cs477.khan_suyat_finalproject.RET_INGREDIENT";
    public final static String RET_FOODGROUP = "edu.gmu.cs477.khan_suyat_finalproject.RET_FOODGROUP";
    public final int ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new GroceryListFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()){
            case R.id.navigation_recipes:
                fragment = new RecipesFragment();
                break;

            case R.id.navigation_grocerylist:
                fragment = new GroceryListFragment();
                break;

            case R.id.navigation_calories:
                fragment = new CaloricIntakeFragment();
                break;
        }
        return loadFragment(fragment);
    }

    /*this button can be found in Grocery List page
    public void onAddClick(View view){
        Intent intent = new Intent(this, AddGrocery.class);
        startActivityForResult(intent, ACTIVITY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_RESULT){
            String ingredient = data.getExtras().getString(RET_INGREDIENT);
            String food_group = data.getExtras().getString(RET_FOODGROUP);
            GroceryListDatabase dbHelper = new GroceryListDatabase(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ingredient", ingredient);
            values.put("food_group", food_group);
            db.insert(dbHelper.NAME, null, values);
        }
    }*/
}
