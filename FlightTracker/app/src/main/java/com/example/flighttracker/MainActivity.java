package com.example.flighttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flighttracker.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlightAdapter flightAdapter;
    private ActivityMainBinding activityMainBinding;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityMainBinding.rvFights.setLayoutManager(linearLayoutManager);
        activityMainBinding.pbLoader.setVisibility(View.GONE);
        flightAdapter = new FlightAdapter(new ArrayList<>());
        activityMainBinding.rvFights.setAdapter(flightAdapter);

        // Initialize the RequestQueue (Consider using a singleton for RequestQueue)
        requestQueue = Volley.newRequestQueue(this);

        activityMainBinding.btnSearch.setOnClickListener(view -> {
            String airportCode = activityMainBinding.editSearch.getText().toString();
            if (TextUtils.isEmpty(airportCode)) {
                showAlertDialog(MainActivity.this, getResources().getString(R.string.hint_airport_code), getResources().getString(R.string.airport_code_warning), new String[]{getResources().getString(R.string.ok),getResources().getString(R.string.cancel)});
            } else {
                if (isNetworkConnected()) {
                    activityMainBinding.pbLoader.setVisibility(View.VISIBLE);
                    fetchDataFromAPI(airportCode);
                } else {
                    createToast(MainActivity.this, getResources().getString(R.string.no_internet));
                }
            }
        });
    }

    // Check network connectivity
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    private void fetchDataFromAPI(String airportCode) {
        String apiKey = "d7e003f1749050593c9f24275b2073a8";
        String apiUrl = "http://api.aviationstack.com/v1/flights?access_key=" + apiKey + "&dep_iata=" + airportCode;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        activityMainBinding.pbLoader.setVisibility(View.GONE);
                        Log.d("API_RESPONSE", response.toString());
                        List<Flight> flights = parseFlightData(response);
                        if (flights.size()>0) {
                            flightAdapter.setData(flights);
                        } else {
                            createToast(MainActivity.this, "No flights found for the given airport code.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activityMainBinding.pbLoader.setVisibility(View.GONE);
                        // Log the error
                        Log.e("API_ERROR", "Error occurred while fetching data: " + error.getMessage());
                        createToast(MainActivity.this, "Error occurred while fetching data: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
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

    void createToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    void snackBar(Context context, String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    void showAlertDialog(Context context, String title, String message, String[] buttonTitles) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(message).setPositiveButton(buttonTitles[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(buttonTitles[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
