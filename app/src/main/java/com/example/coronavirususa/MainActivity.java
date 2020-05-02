package com.example.coronavirususa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface{


    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    public ArrayList<StateObj> stateList = new ArrayList<>();
    public StateObj AllAmericanObj;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    private static final Map<String, String> stateConversion = initMap();


    // Unmodifiable Map for converting State Abbreviations to State Name.
    private static Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();
        map.put("AL", "Alabama");        map.put("AK", "Alaska");    map.put("AZ", "Arizona");        map.put("AR", "Arkansas");
        map.put("CA", "California");     map.put("CO", "Colorado");  map.put("CT", "Coneticut");      map.put("DE", "Delaware");
        map.put("FL", "Florida");        map.put("GA", "Georgia");   map.put("HI", "Hawaii");         map.put("ID", "Idaho");
        map.put("IL", "Illinois");       map.put("IN", "Indiana");   map.put("IA", "Iowa");           map.put("KS", "Kansas");
        map.put("KY", "Kentucky");       map.put("LA", "Louisiana"); map.put("ME", "Maine");          map.put("MD", "Maryland");
        map.put("MA", "Massachusetts");  map.put("MI", "Michigan");  map.put("MN", "Minnesota");      map.put("MO", "Missouri");
        map.put("MT", "Montana");        map.put("NE", "Nebraska");  map.put("NV", "Nevada");         map.put("NH", "New Hampshire");
        map.put("MS", "Mississippi");    map.put("NM", "New Mexico");     map.put("NY", "New York");  map.put("NC", "North Carolina");
        map.put("ND", "North Dakota");   map.put("OH", "Ohio");           map.put("OK", "Oklahoma");  map.put("OR", "Oregon");
        map.put("PA", "Pennsylvania");   map.put("RI", "Rhode Island");   map.put("SC", "South Carolina");  map.put("SD", "South Dakota");
        map.put("TN", "Tennessee");      map.put("TX", "Texas");          map.put("UT", "Utah");      map.put("VT", "Vermont");
        map.put("VA", "Virginia");       map.put("WA", "Washington");     map.put("WV", "West Virginia");  map.put("WI", "Wisconsin");
        map.put("WY", "Wyoming");        map.put("DC", "District of Columbia");  map.put("MH", "Marshall Islands");  map.put("PR", "Pueto Rico");
        map.put("AS", "American Somoa"); map.put("GU", "Guam");           map.put("MP", "Northern Marina Islanda");  map.put("VI", "US Virgin Islands");
        map.put("Error", "error");       map.put("NJ", "New Jersey");

        return Collections.unmodifiableMap(map);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          fetchCountryData();
          fetchStateData();

        recyclerView = findViewById(R.id.recyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        Button button = (Button) findViewById(R.id.updateInfoBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fetchCountryData();
                fetchStateData();
                v.startAnimation(buttonClick);
                Toast.makeText(MainActivity.this, "Updated Data",Toast.LENGTH_SHORT).show();
            }
        });

        View cardview = (View) findViewById(R.id.mainCardView);
        final View mainExpandableCardView = (View) findViewById(R.id.expandableInfoAmerica);
        cardview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mainExpandableCardView.getVisibility() == View.GONE){
                    mainExpandableCardView.setVisibility(View.VISIBLE);
                }else{
                    mainExpandableCardView.setVisibility(View.GONE);
                }
            }
        });

    }

    //Code for search bar for recycler view.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.actionsearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                Log.e("New Text", newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    //#region Code using Volley to get JSON data for America - and its states.
    public void fetchCountryData(){
        String AmericaURL = "https://covidtracking.com/api/us";
        RequestQueue allUSArequestqueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectrequest = new JsonArrayRequest(
                AmericaURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Rest Response", response.toString());
                String jsonobj = response.toString();
                generateAllUsaObj(jsonobj);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }
        );
        allUSArequestqueue.add(objectrequest);
    }


    public void fetchStateData(){

        String URL = "https://covidtracking.com/api/v1/states/current.json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Rest Response", response.toString());
                String jsonarr = response.toString();
                generateRecyclerView(jsonarr);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                generateErrorRecyclerView();
            }
        }
        );
        requestQueue.add(arrayRequest);

    }
    //#endregion

    //#region Code using GSON to convert JSON Objects(string) to Java Objects.
    public void generateRecyclerView(String jsonstr){

        Gson gson = new Gson();
        StateObj [] stateArr = gson.fromJson(jsonstr, StateObj[].class);

        for(StateObj state: stateArr){
            try{
                state.setState(stateConversion.get(state.getState()) + " (" + state.getState() + ")");
            }catch(Exception  e){
                Log.e("Error Hash: ", e.toString());
            }
        }
        stateList = new ArrayList<StateObj>(Arrays.asList(stateArr));
        recyclerAdapter = new RecyclerAdapter(stateList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void generateAllUsaObj(String jsonstr){

        Gson gson = new Gson();
        StateObj [] amrarr = gson.fromJson(jsonstr, StateObj[].class);
        AllAmericanObj = amrarr[0];
        // json request returns a json array with one value.

        //#region Textviews for main USA Info
        TextView posUSA = (TextView)findViewById(R.id.usaPositiveTestTxt);
        TextView negUSA = (TextView)findViewById(R.id.usaNegativeTestTxt);
        TextView recovUSA = (TextView)findViewById(R.id.usaRecoveredTxt);
        TextView deathUSA = (TextView)findViewById(R.id.usaDeathsTxt);
        TextView inICUUSA = (TextView)findViewById(R.id.usaInIcuCurrently);
        TextView hospUSA = (TextView)findViewById(R.id.usaInHospital);
        TextView totestUSA = (TextView)findViewById(R.id.usaTotalTests);
        TextView lastUpdatedUSA = (TextView)findViewById(R.id.usaUpdated);
        //#endregion
        posUSA.setText(Integer.toString(AllAmericanObj.getPositive()));
        negUSA.setText(Integer.toString(AllAmericanObj.getNegative()));
        recovUSA.setText(Integer.toString(AllAmericanObj.getRecovered()));
        deathUSA.setText(Integer.toString(AllAmericanObj.getDeath()));
        inICUUSA.setText(Integer.toString(AllAmericanObj.getInIcuCurrently()));
        hospUSA.setText(Integer.toString(AllAmericanObj.getHospitalizedCurrently()));
        totestUSA.setText(Integer.toString(AllAmericanObj.getTotalTestResults()));
        lastUpdatedUSA.setText(AllAmericanObj.getLastModified());

    }
  //#endregion

    //#region Code incase there is an issue retrieving state data i.e No WIFI
    public void generateErrorRecyclerView(){
        StateObj errorObj = new StateObj("Error",0,"Error",0,0,0,0,0,0,0,
                0,0,"Error",0,0,0,0,"Error", "Error");
        stateList.add(errorObj);
        recyclerAdapter = new RecyclerAdapter(stateList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }
    //#endregion

    //#region Code from interface for click events on specific states
    @Override
    public void onItemclick(int position) {
        StateObj state = stateList.get(position);
        state.setisExpanded(!state.getisExpanded());
        recyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onLongItemClick(int position) {
        // do something if the user does a long click on an item
        // -- still finding a use for this
    }
    //#endregion


}
