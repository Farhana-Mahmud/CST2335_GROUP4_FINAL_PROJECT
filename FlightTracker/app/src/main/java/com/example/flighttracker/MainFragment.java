package com.example.flighttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flighttracker.database.FlightDao;
import com.example.flighttracker.database.FlightDatabase;
import com.example.flighttracker.databinding.ActivityFlightDetailBinding;
import com.example.flighttracker.databinding.ActivityMainBinding;
import com.example.flighttracker.databinding.FragmentMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Farhana Mahmud
 * @version 1.0
 */
public class MainFragment extends Fragment implements OnItemClickListener {

    /**
     * Initialize FlightAdapter
     */
    private FlightAdapter flightAdapter;
    /**
     * Initialize FragmentMainBinding
     */
    private FragmentMainBinding fragmentMainBinding;
    /**
     * Initialize RequestQueue object
     */
    private RequestQueue requestQueue;

    FlightDatabase flightDatabase;
    FlightDao flightDao;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentMainBinding = FragmentMainBinding.inflate(inflater,container,false);
        return fragmentMainBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        SharedPreferenceHelper.initialize(getActivity(),API_KEYS.PREFERENCE_NAME);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragmentMainBinding.rvFights.setLayoutManager(linearLayoutManager);
       fragmentMainBinding.pbLoader.setVisibility(View.GONE);
        flightAdapter = new FlightAdapter(new ArrayList<>(),false);
        flightAdapter.setOnItemClickListener(this::onItemClickListener);
        fragmentMainBinding.rvFights.setAdapter(flightAdapter);
        flightDatabase = Room.databaseBuilder(getActivity(),FlightDatabase.class,API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        setHasOptionsMenu(true);
        // Initialize the RequestQueue (Consider using a singleton for RequestQueue)
        requestQueue = Volley.newRequestQueue(getContext());
        if(SharedPreferenceHelper.getStringValue(API_KEYS.AIRPORT_CODE)!=null)
        {
            String airportCode = SharedPreferenceHelper.getStringValue(API_KEYS.AIRPORT_CODE);
            fragmentMainBinding.editSearch.setText(airportCode);
            if (isNetworkConnected()) {
                fragmentMainBinding.pbLoader.setVisibility(View.VISIBLE);
                fetchDataFromAPI(airportCode);
            } else {
                MainActivity.createToast(getActivity(), getResources().getString(R.string.no_internet));
            }
        }
        fragmentMainBinding.btnSearch.setOnClickListener(view -> {
            String airportCode = fragmentMainBinding.editSearch.getText().toString();


            if (TextUtils.isEmpty(airportCode)) {
                MainActivity.showAlertDialog(getActivity(), getResources().getString(R.string.hint_airport_code), getResources().getString(R.string.airport_code_warning), new String[]{getResources().getString(R.string.ok),getResources().getString(R.string.cancel)},null,null);
            } else {
                if (isNetworkConnected()) {
                    fragmentMainBinding.pbLoader.setVisibility(View.VISIBLE);
                    fetchDataFromAPI(airportCode);
                } else {
                    MainActivity.createToast(getActivity(), getResources().getString(R.string.no_internet));
                }
            }
        });
    }


    /**
     * This method checks the network connectivity
     * @return connection status
     */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * This method fetches data from the API
     * @param airportCode is used to check the flight details
     */
    private void fetchDataFromAPI(String airportCode) {
        String apiUrl = API_KEYS.API_URL+ airportCode;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    fragmentMainBinding.pbLoader.setVisibility(View.GONE);
                    Log.d("API_RESPONSE", response.toString());
                    List<Flight> flights = parseFlightData(response);
                    if (flights.size()>0) {
                        flightAdapter.setData(flights);
                        SharedPreferenceHelper.setStringValue(API_KEYS.AIRPORT_CODE,airportCode);
                    } else {
                        MainActivity.createToast(getContext(), getResources().getString(R.string.no_flight_found_airport_code));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fragmentMainBinding.pbLoader.setVisibility(View.GONE);
                        // Log the error
                        Log.e("API_ERROR", "Error occurred while fetching data: " + error.getMessage());
                        MainActivity.createToast(getContext(), getResources().getString(R.string.api_server_error) + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Populates the ArrayList with list of flight details
     * @param response
     * @return
     */
    private List<Flight> parseFlightData(JSONObject response) {
        List<Flight> flights = new ArrayList<>();
        try {
            JSONArray data = response.getJSONArray(API_KEYS.DATA);
            for (int i = 0; i < data.length(); i++) {
                JSONObject flightObject = data.getJSONObject(i);
                Flight flight = new Flight().convertJsonToFlight(flightObject);
                flights.add(flight);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flights;
    }


    @Override
    public void onItemClickListener(Flight flight, int position) {

        Fragment fragment = new FlightDetail();
        Bundle bundle = new Bundle();
        bundle.putParcelable(API_KEYS.FLIGHT_DETAIL,flight);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(API_KEYS.FLIGHT_DETAIL).commit();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.help_menu_main,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            MainActivity.showAlertDialog(getActivity(),getResources().getString(R.string.main_help_title),getResources().getString(R.string.main_help_message),
                    new String[]{getResources().getString(R.string.main_positive_button_label),
                            getResources().getString(R.string.main_negative_button_label)},null,null);
        }
        else if(item.getItemId()==R.id.menu_favourite)
        {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                if(flightDao.getFlights().size()>0)
                {
                    getActivity().runOnUiThread(()-> {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FavouriteFlights()).addToBackStack(API_KEYS.FRAGMENT_FAVOURITE).commit();
                    });
                }
                else
                { getActivity().runOnUiThread(()-> {
                        MainActivity.createToast(getActivity(),getActivity().getResources().getString(R.string.no_record_found));
                     });
                }
            });



        }
        return true;
    }


}
