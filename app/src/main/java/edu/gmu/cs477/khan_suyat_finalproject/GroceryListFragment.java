package edu.gmu.cs477.khan_suyat_finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class GroceryListFragment extends Fragment {

    private SQLiteDatabase db = null;
    private GroceryListDatabase groceryDBHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    Cursor mCursor;
    String ingredient;
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
        String whereClause = "ingredient != ?";
        String [] whereArgs = new String[]{"None"};
        mCursor = db.query(groceryDBHelper.NAME, all_columns, whereClause, whereArgs,null,null,null);
        myAdapter = new SimpleCursorAdapter(getContext(),
                                            android.R.layout.simple_list_item_1,
                                            mCursor,
                                            new String[]{INGREDIENT},
                                            new int[]{android.R.id.text1});

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        db = groceryDBHelper.getWritableDatabase();
                        db.delete(groceryDBHelper.NAME, "ingredient=?", new String[]{ingredient});
                        String whereClause = "ingredient != ?";
                        String [] whereArgs = new String[]{"None"};
                        mCursor = db.query(groceryDBHelper.NAME, all_columns, whereClause, whereArgs, null, null,
                                null);
                        myAdapter.swapCursor(mCursor);
                        break;

                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
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
        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ingredient = ((TextView) view).getText().toString();
                actions.show();
                return true;
            }
        });

    }
}

