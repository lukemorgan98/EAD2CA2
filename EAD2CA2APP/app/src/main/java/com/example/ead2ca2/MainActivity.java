package com.example.ead2ca2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.AdapterView;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    public static final String[] languages ={"Lang","Eng","Ger"};
    private RequestQueue mQueue;
    private Button button_seller;
    private Button search_button;
    private EditText search_bar;
    private String output;
    private CharSequence input;
    private boolean getButtonClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);


        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonNormalParse();
                getButtonClicked = true;
            }
        });

        button_seller = (Button) findViewById(R.id.button_seller);
        button_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellerActivity();
            }
        });

        search_button = (Button) findViewById(R.id.search_button);
        search_bar = (EditText) findViewById(R.id.search_bar);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = search_bar.getText();
                jsonSearchParse(input);
            }
        });




        Spinner spinner =(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = adapterView.getItemAtPosition(i).toString();
                if(selectedLang.equals("Eng")){
                    setLocal(MainActivity.this,"en");
                    buttonParse.setText("Show Clothes");
                    button_seller.setText("Seller Page");
                    search_button.setText("Search");
                    search_bar.setHint("Search by Brand");
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    startActivity(intent);
                }
                else if(selectedLang.equals("Ger")){
                    setLocal(MainActivity.this, "de");
                    buttonParse.setText("TEST");
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    startActivity(intent);
                    buttonParse.setText("Kleidung Zeigen");
                    button_seller.setText("Verkäuferseite");
                    search_button.setText("Suche");
                    search_bar.setHint("Suche nach Marke ");
                }
                else {
                    Toast.makeText(MainActivity.this, "Please select a Language", Toast.LENGTH_SHORT).show();

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }
    public  void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }

    public void openSellerActivity() {
        Intent intent = new Intent(this, SellerActivity.class);
        startActivity(intent);
    }

    //This is the get method but it is for searches
    private void jsonSearchParse(CharSequence in) {
        mTextViewResult.setText("");
        String url;
        final String[] searchWord = new String[1];
        searchWord[0] = String.valueOf(in);

        if(in != null){
            url = "https://ead2ca2api20220425024150.azurewebsites.net/api/Electronics";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            try {
                                output="";


                                //Loop the Array
                                JSONObject electronic;
                                if(searchWord[0].toLowerCase().contains("nike")) {

                                    for (int i = 0; i < 3; i++) {
                                        electronic = response.getJSONObject(1);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                        electronic = response.getJSONObject(3);
                                        ID = electronic.getInt("id");
                                        Name = electronic.getString("name");
                                        Price = electronic.getInt("price");
                                        brandName = electronic.getString("brandName");
                                        size = electronic.getString("size");
                                        colour = electronic.getString("colour");
                                        SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }
                                else if(searchWord[0].toLowerCase().contains("converse")) {

                                    for (int i = 0; i < 1; i++) {
                                        electronic = response.getJSONObject(2);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }

                                else if(searchWord[0].toLowerCase().contains("tommy hilfiger")
                                        ||searchWord[0].toLowerCase().contains("hilfiger")
                                        ||searchWord[0].toLowerCase().contains("tommy")) {

                                    for (int i = 0; i < 1; i++) {
                                        electronic = response.getJSONObject(0);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }

                                else if(searchWord[0].toLowerCase().contains("the north face")
                                        ||searchWord[0].toLowerCase().contains("north face")
                                        ||searchWord[0].toLowerCase().contains("north")
                                        ||searchWord[0].toLowerCase().contains("the")
                                        ||searchWord[0].toLowerCase().contains("face")) {

                                    for (int i = 0; i < 1; i++) {
                                        electronic = response.getJSONObject(4);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }

                                else if(searchWord[0].toLowerCase().contains("jordan")) {

                                    for (int i = 0; i < 1; i++) {
                                        electronic = response.getJSONObject(5);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }
                                else if(searchWord[0].toLowerCase().contains("superdry")) {

                                    for (int i = 0; i < 1; i++) {
                                        electronic = response.getJSONObject(6);
                                        int ID = electronic.getInt("id");
                                        String Name = electronic.getString("name");
                                        double Price = electronic.getInt("price");
                                        String brandName = electronic.getString("brandName");
                                        String size = electronic.getString("size");
                                        String colour = electronic.getString("colour");
                                        int SellerID = electronic.getInt("sellerID");
                                        output = String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                                ", " + size + ", " + colour + ", " + String.valueOf(SellerID) + "\n\n";
                                    }
                                }
                                else{
                                    mTextViewResult.setText("");
                                    output = "Sorry, search again : " + searchWord[0];
                                }
                                mTextViewResult.append(output);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }
        else{
            url = "https://ead2ca2api20220425024150.azurewebsites.net/api/Electronics/";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                //Loop the Array
                                JSONObject electronic;
                                for (int i = 0; i < response.length(); i++) {
                                    electronic = response.getJSONObject(i);
                                    int ID = electronic.getInt("id");
                                    String Name = electronic.getString("name");
                                    double Price = electronic.getInt("price");
                                    String brandName = electronic.getString("brandName");
                                    String size = electronic.getString("size");
                                    String colour = electronic.getString("colour");
                                    int SellerID = electronic.getInt("sellerID");
                                    output=String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                            ", "  + size + ", "  + colour + ", "  + String.valueOf(SellerID)  + "\n\n";

                                    mTextViewResult.append(output);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }


    }

    private void jsonNormalParse() {
        mTextViewResult.setText("");
        String url = "https://ead2ca2api20220425024150.azurewebsites.net/api/Electronics";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            //Loop the Array
                            JSONObject clothe;
                            for (int i = 0; i < response.length(); i++) {
                                clothe = response.getJSONObject(i);
                                int ID = clothe.getInt("id");
                                String Name = clothe.getString("name");
                                double Price = clothe.getInt("price");
                                String brandName = clothe.getString("brandName");
                                String size = clothe.getString("size");
                                String colour = clothe.getString("colour");
                                int SellerID = clothe.getInt("sellerID");
                                if(getButtonClicked=true){

                                    mTextViewResult.append(String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                            ", "  + size + ", "  + colour + ", "  + String.valueOf(SellerID)  + "\n\n");
                                }

                            }

//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}



