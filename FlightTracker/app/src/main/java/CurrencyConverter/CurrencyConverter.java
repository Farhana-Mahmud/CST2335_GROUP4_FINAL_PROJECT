/**
 * Student name: Avneet kaur
 * Student Number:041059813
 * Student Description: It shows the activity libked to their respective layout files
 */
package CurrencyConverter;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flighttracker.R;
import com.example.flighttracker.databinding.ActivityChatRoom1Binding;

import com.example.flighttracker.databinding.ReceiveMessageBinding;
import com.example.flighttracker.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * This is the main activity class named CurrencyConverter, which extends AppCompatActivity.
 * It represents the Currency Converter activity and is linked to its respective layout files.
 */
public class CurrencyConverter extends AppCompatActivity {
    /**
     * It binds the activity with the mainChatRoomXml file
     */
    ActivityChatRoom1Binding binding;
    /**
     * It gets linked with the ConversionRoomViewModel chatModel
     */
    ConversionRoomViewModel chatModel;
    /**
     * It shows the list of array messages
     */
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ////ArrayList<String> message = new ArrayList<>();
    /**
     * It shows the recycler view
     */
    private RecyclerView.Adapter myAdapter;
    /**
     * It shows the messaged which were saved and then then display the last saved
     */
    SharedPreferences sharedPreferences;
    /**
     * It defines a variable named currencyName
     */
    protected String currencyName;
    /**
     * It assigns the request value equals to null
     */
    RequestQueue queue = null;
    /**
     * It shows the mconversionMessageDAO
     */
    private ConversionMessageDAO mDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ConversionMessageDAO mDAO = db.cmDAO();
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding = ActivityChatRoom1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        chatModel = new ViewModelProvider(this).get(ConversionRoomViewModel.class);
        ////message = chatModel.messages.getValue();
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        ConversionRoomViewModel viewModel = null;
        // Initialize the sharedPreferences object
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
/**
 * This method will show sthe last message typed and whn the app will be debug again it will show t
 * he last message typed
 */

        // Retrieve the stored value from SharedPreferences
        String storedAmountValue = sharedPreferences.getString("amountValue", "");
        // Set the value to the EditText
          binding.textInput.setText(storedAmountValue);
           binding.imageButton2.setOnClickListener(click -> {
               // Assuming you have the following EditText views in your layout:
               EditText editTextFrom = findViewById(R.id.editTextFrom);
               EditText editTextTo = findViewById(R.id.editTextTo);

// Retrieve the input values from the EditText views
               String currencyFrom = editTextFrom.getText().toString();
               String currencyTo = editTextTo.getText().toString();

               /**
                * This will assigns a value to the button which is linked to the online API linked and it will show the conversion
                */
            String stringURL = null;

                //stringURL = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=AUD&to=CAD&amount=1&api_key=b0da6c968adc84d139786ca08ffb3628cf2f8159&format=json";
// Construct the URL for the API request with the input values
               try {
                   stringURL = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=" +
                           URLEncoder.encode(currencyFrom, "UTF-8") +
                           "&to=" + URLEncoder.encode(currencyTo, "UTF-8") +
                           "&amount=1&api_key=b0da6c968adc84d139786ca08ffb3628cf2f8159&format=json";
               } catch (UnsupportedEncodingException e) {
                   throw new RuntimeException(e);
               }

               JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null, (response) -> {
                try {
                    // Extract data from the response JSON object
                    String baseCurrencyCode = response.getString("base_currency_code");
                    String baseCurrencyName = response.getString("base_currency_name");
                    String amount = response.getString("amount");
                    String updatedDate = response.getString("updated_date");
                    JSONObject rates = response.getJSONObject("rates");
                    JSONObject cad = rates.getJSONObject("CAD");

                    String currencyName = cad.getString("currency_name");
                    String rate = cad.getString("rate");
                    String rateForAmount = cad.getString("rate_for_amount");
                    binding.basecurrencycode.setText("The base currency Code is :"+baseCurrencyCode);
                    binding.basecurrencycode.setVisibility(View.VISIBLE);
                    binding.basecurrencyname.setText("The base currency Name is :"+baseCurrencyName);
                    binding.basecurrencyname.setVisibility(View.VISIBLE);
                    binding.amount.setText("The amount is :"+amount);
                    binding.amount.setVisibility(View.VISIBLE);
                    binding.updatedDate.setText("The updatedDate is :"+updatedDate);
                    binding.updatedDate.setVisibility(View.VISIBLE);
                     binding.currencyName.setText("The currencyName  is"+currencyName );
                    binding.currencyName.setVisibility(View.VISIBLE);
                    binding.rt.setText("The rate is"+rate );
                   binding.rt.setVisibility(View.VISIBLE);
                    binding.rfa.setText("The rateForAmount is"+rateForAmount);
                    binding.rfa.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }, (error) -> {
            });
            queue.add(request);
        });
/**
 * This methods links the chat model with the selected message and when clicked will diaplay the
 * id and the values converted
 */

           chatModel.selectedMessage.observe(this, (newMessageValue) -> {

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.replace(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack(null);
            tx.commit();
        });


        if (messages == null) {
            ArrayList<String> message;
            ////////chatModel.messages.setValue(message = new ArrayList<>());
            chatModel.messages.setValue(messages);

        }
        /**
         * This will select the particular messages according to the change in the languages
         * and then when the default language is selected it will display the default vales while
         * if the lanhuage will change it will see the values in the other folders
         */
       // Get the current locale/language
        Locale currentLocale = getResources().getConfiguration().locale;
        String currentLanguage = currentLocale.getLanguage();
        // Set the button text based on the current language
        if (currentLanguage.equals("en")) { // For English
            binding.button.setText(R.string.AUD);
        } else if (currentLanguage.equals("hi")) { // For Hindi
            binding.button.setText(R.string.AUD); // Replace "AUD" with the appropriate key for "INR" in Hindi strings.xml
        }

        if (currentLanguage.equals("en")) { // For English
            binding.button4.setText(R.string.CAD);
        } else if (currentLanguage.equals("hi")) { // For Hindi
            binding.button4.setText(R.string.CAD); // Replace "AUD" with the appropriate key for "INR" in Hindi strings.xml
        }



        // Set the background color based on the current language
        if (currentLanguage.equals("en")) { // For English
            findViewById(R.id.fragmentLocation).setBackgroundResource(R.color.background_color);
        } else if (currentLanguage.equals("hi")) { // For Hindi
            findViewById(R.id.fragmentLocation).setBackgroundResource(R.color.background_color);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        /**
         * this button links the with the conversion method s
         */
        binding.button.setOnClickListener(click -> {
            String amountString = binding.textInput.getText().toString();
            String currencyFrom = "YOUR_SOURCE_CURRENCY";
            String currencyTo = "YOUR_TARGET_CURRENCY";
             // Store the value in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("amountValue", amountString);
            editor.apply();


            // Convert the amount from String to double
            double amount = Double.parseDouble(amountString);

            // Calculate the converted amount manually using the exchange rate
            double convertedAmount = amount * 1.13548;


             // Create the ChatMessage object with the converted amount
            ChatMessage chatMessage = new ChatMessage(String.valueOf(convertedAmount), "", true);

            // Insert the message into the database
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(chatMessage);
            });

            // Add the message to the list and notify the adapter
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);

            // Clear the previous text
            binding.textInput.setText("");
            // Show an AlertDialog when the conversion is made
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Conversion Done");
            builder.setMessage("Conversion is made successfully.");
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Handle the OK button click if needed, or leave it empty
            });
            builder.show();

        });
        /**
         * this button links the with the conversion methods as it is with the second button
         */
        binding.button4.setOnClickListener(click -> {
            String amountString = binding.textInput.getText().toString();
            String currencyFrom = "YOUR_SOURCE_CURRENCY";
            String currencyTo = "YOUR_TARGET_CURRENCY";


            // Convert the amount from String to double
            double amount = Double.parseDouble(amountString);

              // Calculate the converted amount manually using the exchange rate
            double convertedAmount = amount * 1 / 1.13548;


              // Create the ChatMessage object with the converted amount
            ChatMessage chatMessage = new ChatMessage(String.valueOf(convertedAmount), "", false);

            // Insert the message into the database
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(chatMessage);
            });

            // Add the message to the list and notify the adapter
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);

            // Clear the previous text
            binding.textInput.setText("");

             // Show a Toast message when the conversion is made
            Toast.makeText(this, "Conversion is made successfully.", Toast.LENGTH_SHORT).show();


        });
/**
 * This button will show the last query typed in the form of shared preferences
 */
        binding.saveButton.setOnClickListener(click->{
            String storedAmountValue2 = sharedPreferences.getString("amountValue", "");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Stored Amount Value");
            builder.setMessage("The last conversion made and stored was : " + storedAmountValue2);
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Handle the OK button click if needed, or leave it empty
            });
            builder.show();
});


        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             * Sets up the RecyclerView adapter with the data and handles the creation of view holders
             * and binding of data to each item.
             *
             * @param parent The ViewGroup into which the new View will be added after it is bound to
             *               an adapter position.
             * @param viewType The view type of the new View.
             * @return A new instance of MyRowHolder, representing a row in the RecyclerView.
             */

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    View root = binding.getRoot();
                    return new MyRowHolder(binding.getRoot(),messages, mDAO, myAdapter);
                } else {
                    // Inflate the receive_message layout for viewType 1
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot(), messages, mDAO, myAdapter);
                }
            }

            /**
             * Binds the data to the view holder at the specified position in the RecyclerView.
             *
             * @param holder The MyRowHolder to bind the data to.
             * @param position The position of the item in the RecyclerView.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }
            /**
             * Returns the total number of items in the data set.
             *
             * @return The total number of items in the RecyclerView.
             */
            @Override
            public int getItemCount() {
                return messages.size();
            }
            /**
             * Returns the view type of the item at the specified position.
             *
             * @param position The position of the item in the RecyclerView.
             * @return The view type of the item.
             */
            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                if (chatMessage.isSentButton()) {
                    return 0; // Send message type
                } else {
                    return 1; // Receive message type
                }
            }

        });
    }
    /**
     * Performs a currency conversion based on the given amount, source currency, and target currency.
     *
     * @param amount The amount to convert.
     * @param currencyFrom The source currency code.
     * @param currencyTo The target currency code.
     * @return The converted amount after the currency conversion.
     */
    private double performConversion(String amount, String currencyFrom, String currencyTo) {
        double amountValue = Double.parseDouble(amount);
        double conversionRate = 1.13548; // Placeholder conversion rate
        double convertedAmount = amountValue * conversionRate;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount", amount);
        editor.putString("currencyFrom", currencyFrom);
        editor.putString("currencyTo", currencyTo);
        editor.apply();



        return convertedAmount;
    }
    /**
     * Overrides the back button press behavior to handle the Fragments in the back stack.
     */
   // @Override
    public void onBackPressed() {
        // Check if there are any Fragments in the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the Fragment from the back stack
            getSupportFragmentManager().popBackStack();
        } else {
            // No Fragments in the back stack, let the system handle the back button
            super.onBackPressed();
        }
    }
    /**
     * Creates the options menu and handles the selection of menu items.
     *
     * @param menu The menu in which the items are placed.
     * @return {@code true} if the menu is displayed; {@code false} otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my_menu, menu);
        // Get the menu items
        MenuItem item1 = menu.findItem(R.id.menu_symbol);

        return true;
    }
    /**
     * Handles the selection of menu items.
     *
     * @param item The selected menu item.
     * @return {@code true} if the menu item is handled; {@code false} otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        String words="Version 1, created by Avneet kaur";
        if(item.getItemId()==R.id.menu_symbol){
            String helpMessage = "Instructions for using the interface:\n\n" +
                    "- To perform currency conversion, enter the amount in the text input field and click the Convert button.\n" +
                    "- The converted currency details will be displayed below.\n" +
                    "- You can delete a message by clicking on it and confirming the deletion in the dialog box.\n" +
                    "- Click on any message to view its details in a separate fragment.\n" +
                    "- The toolbar provides language selection options for English and another language.\n" +
                    "- Click on the menu items to see different messages related to each person's project.\n" +
                    "- The color of the titles can be changed based on the language selected.\n\n" +
                    "Version 1, created by Avneet kaur";

            // Create and show the AlertDialog with help message
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Help");
            builder.setMessage(helpMessage);
            builder.setPositiveButton("OK", null);
            builder.show();
        }



        return true;
    }
    /**
     * ViewHolder class representing a row in the RecyclerView.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The View object representing the row in the RecyclerView.
         * @param messages The ArrayList of ChatMessage objects containing the chat messages.
         * @param mDAO The DAO (Data Access Object) for performing database operations.
         * @param adt The RecyclerView adapter for binding data to the ViewHolder.
         */
        TextView messageText;
        TextView timeText;
        ArrayList<ChatMessage> messages;
        ConversionMessageDAO mDAO;
        RecyclerView.Adapter adt;
        public MyRowHolder(@NonNull View itemView,ArrayList<ChatMessage> messages, ConversionMessageDAO mDAO, RecyclerView.Adapter adt) {
            super(itemView);
            this.messages = messages;
            this.mDAO = mDAO;
            this.adt = adt;
            itemView.setOnClickListener(clk -> {
               int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

               chatModel.selectedMessage.postValue(selected);
                //int position = getAbsoluteAdapterPosition();
                ChatMessage message = messages.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setMessage("Do you want to delete the message:" + messageText.getText());
                builder.setNegativeButton("No", null);

                builder.setPositiveButton("Yes", (dialog, cl) -> {



                        builder.setNegativeButton("No", null);

                        Executor thread = Executors.newSingleThreadExecutor();

                        // Delete the message from the database
                        thread.execute(() -> {
                            mDAO.deleteMessage(message);
                        });

                        // Remove the message from the list and update the adapter
                        messages.remove(position);
                        adt.notifyItemRemoved(position);

                        // Show a snackbar with the option to undo the delete
                        Snackbar.make(messageText, "You deleted the message #" + position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", ck -> {
                                    // Add the removed message back to the list and update the adapter
                                    messages.add(position, message);
                                    adt.notifyItemInserted(position);
                                })
                                .show();

                        });
                        builder.create().show();

            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        private int getAbsoluteAdapterPosition() {
            return 0;
        }
    }
}