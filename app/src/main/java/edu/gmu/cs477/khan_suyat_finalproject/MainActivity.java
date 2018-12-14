package edu.gmu.cs477.khan_suyat_finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

//Mohammad Khan
    final Notification notification = new Notification();
    public final static String RET_INGREDIENT = "edu.gmu.cs477.khan_suyat_finalproject.RET_INGREDIENT";
    public final static String RET_FOODGROUP = "edu.gmu.cs477.khan_suyat_finalproject.RET_FOODGROUP";
    static final String MYDYNAMIC = "edu.gmu.cs477.khan_suyat_finalproject.MYDYNAMIC";
    public final int ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new GroceryListFragment());

        IntentFilter intf = new IntentFilter(MYDYNAMIC);
        registerReceiver(notification, intf);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 27);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        intent.setAction(MYDYNAMIC);
        sendBroadcast(intent);
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


    public void onEditButtonClicked(View view){

        Intent intent = new Intent(this, EditCalories.class);
        startActivityForResult(intent, ACTIVITY_RESULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            String ingredient = data.getExtras().getString(RET_INGREDIENT);
            String food_group = data.getExtras().getString(RET_FOODGROUP);
            GroceryListDatabase dbHelper = new GroceryListDatabase(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ingredient", ingredient);
            values.put("food_group", food_group);
            db.insert(dbHelper.NAME, null, values);
        }
        else if(resultCode == RESULT_CANCELED)
        {
            System.out.println("HAHA");
        }
    }
}
