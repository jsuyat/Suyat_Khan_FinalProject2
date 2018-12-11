package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddGrocery extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private GroceryListDatabase groceryDBHelper = null;
    EditText ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        ingredient = (EditText) findViewById(R.id.ingredientInput);
        groceryDBHelper = new GroceryListDatabase(this);
    }

    public void onAddIngredientClicked(View view){
        

    }
}
