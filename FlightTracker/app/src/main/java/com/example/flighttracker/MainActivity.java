package com.example.flighttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.flighttracker.database.FlightDao;
import com.example.flighttracker.database.FlightDatabase;
import com.example.flighttracker.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements OnFragmentEvent {


    private ActivityMainBinding activityMainBinding;
    FlightDatabase flightDatabase;
    FlightDao flightDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setOnFragmentEvent(this::onItemClickInFragment);
        flightDatabase = Room.databaseBuilder(this,FlightDatabase.class,API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        getSupportFragmentManager().beginTransaction().add(R.id.container,mainFragment,API_KEYS.FRAGMENT_MAIN).addToBackStack(API_KEYS.FRAGMENT_MAIN).commit();
    }



   public static void createToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            finish();
        }
        else
        {
            getSupportFragmentManager().popBackStack();
        }

    }

    public   static void snackBar(Context context, String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            MainActivity.showAlertDialog(MainActivity.this,getResources().getString(R.string.main_help_title),getResources().getString(R.string.main_help_message),
                    new String[]{getResources().getString(R.string.main_positive_button_label),
                            getResources().getString(R.string.main_negative_button_label)},null,null);
        }
        else if(item.getItemId()==R.id.menu_favourite)
        {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                if(flightDao.getFlights().size()>0)
                {
                    runOnUiThread(()-> {
                        FavouriteFlights favouriteFlights = new FavouriteFlights();
                        favouriteFlights.setOnFragmentEvent(this);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,favouriteFlights).addToBackStack(API_KEYS.FRAGMENT_FAVOURITE).commit();
                    });
                }
                else
                { runOnUiThread(()-> {
                    MainActivity.createToast(MainActivity.this,getResources().getString(R.string.no_record_found));
                });
                }
            });



        }
        return true;
    }
   public static void showAlertDialog(Context context, String title, String message, String[] buttonTitles,PositiveClickListener positiveClickListener,Flight flight) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(message).setPositiveButton(buttonTitles[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(positiveClickListener!=null)
                        {
                            positiveClickListener.onUserConfirmation(flight);
                        }

                    }
                }).setNegativeButton(buttonTitles[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onItemClickInFragment(Flight flight, int position) {
        if(activityMainBinding.detailContainer!=null)
        {
            Fragment fragment = new FlightDetail();
            Bundle bundle = new Bundle();
            bundle.putParcelable(API_KEYS.FLIGHT_DETAIL,flight);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,fragment).addToBackStack(API_KEYS.FLIGHT_DETAIL).commit();
        }
        else
        {
            Fragment fragment = new FlightDetail();
            Bundle bundle = new Bundle();
            bundle.putParcelable(API_KEYS.FLIGHT_DETAIL,flight);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(API_KEYS.FLIGHT_DETAIL).commit();
        }
    }
}
