package edu.gmu.cs477.khan_suyat_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import org.w3c.dom.Text;

public class EditCalories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calories);


        Intent intent = getIntent();
        int cMax = CaloricIntakeFragment.total;



        TextView current = (TextView) findViewById(R.id.currentMax);
        current.setText(String.valueOf(cMax));



    }

    public void onUpdateClicked(View view){
//        Intent intent = new Intent();
//        String inputIngredient = ingredient.getText().toString();
//        intent.putExtra(MainActivity.RET_INGREDIENT, inputIngredient);
//        intent.putExtra(MainActivity.RET_FOODGROUP, food_group);
//        setResult(RESULT_OK, intent);
//        finish();
        EditText in = (EditText) findViewById(R.id.input);
        String input = in.getText().toString();

        int newTotal = Integer.valueOf(input);
        CaloricIntakeFragment.total = newTotal;

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);

        finish();

    }

}
