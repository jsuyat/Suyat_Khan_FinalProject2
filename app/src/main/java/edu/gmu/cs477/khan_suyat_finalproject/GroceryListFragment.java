package edu.gmu.cs477.khan_suyat_finalproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
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

    public final static String RET_INGREDIENT = "edu.gmu.cs477.khan_suyat_finalproject.RET_INGREDIENT";
    public final static String RET_FOODGROUP = "edu.gmu.cs477.khan_suyat_finalproject.RET_FOODGROUP";
    public final int ACTIVITY_RESULT = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groceryDBHelper = new GroceryListDatabase(getContext());
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
                        /*String whereClause = "ingredient != ?";
                        String [] whereArgs = new String[]{"None"};
                        mCursor = db.query(groceryDBHelper.NAME, all_columns, whereClause, whereArgs, null, null,
                                null);
                        myAdapter.swapCursor(mCursor);*/
                        LoadDatabase loadDB = new LoadDatabase(getContext(), mlist, myAdapter);
                        loadDB.execute();
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

        Button addButton = (Button) getView().findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddGrocery.class);
                startActivityForResult(intent, ACTIVITY_RESULT);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_RESULT){
            String ingredient = data.getExtras().getString(RET_INGREDIENT);
            String food_group = data.getExtras().getString(RET_FOODGROUP);
            GroceryListDatabase dbHelper = new GroceryListDatabase(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ingredient", ingredient);
            values.put("food_group", food_group);
            db.insert(dbHelper.NAME, null, values);
            LoadDatabase loadDB = new LoadDatabase(getContext(), mlist, myAdapter);
            loadDB.execute();
        }
    }

    private final static class LoadDatabase extends AsyncTask<GroceryListDatabase, Void, Cursor>{
        private ListView mList;
        Context appContext;
        SimpleCursorAdapter adapter;

        public LoadDatabase(Context context, ListView list, SimpleCursorAdapter myAdapter){
            this.mList = list;
            this.appContext = context;
            this.adapter = myAdapter;
        }

        @Override
        protected Cursor doInBackground(GroceryListDatabase... groceryListDatabases) {
            GroceryListDatabase dbHelper = new GroceryListDatabase(appContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ingredient != ?";
            String [] whereArgs = new String[]{"None"};
            String[] all_columns = {"_id", "ingredient", "food_group"};
            Cursor mCursor = db.query(dbHelper.NAME, all_columns, whereClause,
                    whereArgs, null, null, null);

            return mCursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            //super.onPostExecute(cursor);
            adapter.swapCursor(cursor);
        }
    }
}

