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
import android.widget.ListView;
import android.widget.EditText;

public class RecipesFragment extends Fragment {

    private SQLiteDatabase db = null;
    private GroceryListDatabase groceryHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    EditText elem;
    Cursor mCursor;
    AlertDialog actions;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_recipes, null);
        mlist = (ListView) view.findViewById(R.id.grocery_list);
        return view;
    }

    /*public void onResume(){
        super.onResume();
        db = groceryHelper.getWritableDatabase();

        mCursor = db.query(groceryHelper.NAME, all_columns, null, null, null, null,
                null);
        myAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[] { "item" },
                new int[] { android.R.id.text1 });

        mlist.setAdapter(myAdapter);
    }*/

}
