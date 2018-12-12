package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddGrocery extends AppCompatActivity {

    EditText ingredient;
    String food_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        ingredient = (EditText) findViewById(R.id.ingredientInput);
        food_group = "";

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.meatButton:
                if(checked){
                    food_group = "Meat";
                }
                break;
            case R.id.vegetableButton:
                if(checked){
                    food_group = "Vegetable";
                }
                break;
            case R.id.dairyButton:
                if(checked){
                    food_group = "Dairy";
                }
                break;
            case R.id.grainButton:
                if(checked){
                    food_group = "Grain";
                }
                break;
            case R.id.fruitButton:
                if(checked){
                    food_group = "Fruit";
                }
                break;
        }
    }

    public void onAddIngredientClicked(View view){
        Intent intent = new Intent();
        String inputIngredient = ingredient.getText().toString();
        intent.putExtra(MainActivity.RET_INGREDIENT, inputIngredient);
        intent.putExtra(MainActivity.RET_FOODGROUP, food_group);
        setResult(RESULT_OK, intent);
        finish();

    }
}
