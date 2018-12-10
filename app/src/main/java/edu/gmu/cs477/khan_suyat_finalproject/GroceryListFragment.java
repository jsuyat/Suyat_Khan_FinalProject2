package edu.gmu.cs477.khan_suyat_finalproject;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GroceryListFragment extends Fragment {

    private SQLiteDatabase db = null;
    private GroceryListDatabase groceryDBHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    Cursor mCursor;
    AlertDialog actions;
    View view;
    final static String _ID = "_id";
    final static private String INGREDIENT = "ingredient";
    final static private String FOODGROUP = "food_group";
    final static String[] all_columns = {_ID, INGREDIENT, FOODGROUP};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groceryDBHelper = new GroceryListDatabase(getContext());
        System.out.println("MKv2");
        db = groceryDBHelper.getWritableDatabase();
        mCursor = db.query(groceryDBHelper.NAME, all_columns,null,null,null,null,null);
        myAdapter = new SimpleCursorAdapter(getContext(),
                                            android.R.layout.simple_list_item_1,
                                            mCursor,
                                            new String[]{INGREDIENT},
                                            new int[]{android.R.id.text1});
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocerylist, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlist = (ListView) getView().findViewById(R.id.grocery_list);
        mlist.setAdapter(myAdapter);
    }
}

