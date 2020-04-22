package com.example.coronavirususa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
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
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    private static final Map<String, String> stateConversion = initMap();

    private static Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();
        map.put("AL", "Alabama");        map.put("AK", "Alaska");    map.put("AZ", "Arizona");        map.put("AR", "Arkansas");
        map.put("CA", "California");     map.put("CO", "Colorado");  map.put("CT", "Coneticut");      map.put("DE", "Delaware");
        map.put("FL", "Florida");        map.put("GA", "Georgia");   map.put("HI", "Hawaii");         map.put("ID", "Idaho");
        map.put("IL", "Illinois");       map.put("IN", "Indiana");   map.put("IA", "Iowa");           map.put("KS", "Kansas");
        map.put("KY", "Kentucky");       map.put("LA", "Louisiana"); map.put("ME", "Maine");          map.put("MD", "Maryland");
        map.put("MA", "Massachusetts");  map.put("MI", "Michigan");  map.put("MN", "Minnesota");      map.put("MO", "Missouri");
        map.put("MT", "Montana");        map.put("NE", "Nebraska");  map.put("NV", "Nevada");         map.put("NH", "New Hampshire");
        map.put("NM", "New Mexico");     map.put("NY", "New York");  map.put("NC", "North Carolina"); map.put("ND", "North Dakota");
        map.put("OH", "Ohio");           map.put("OK", "Oklahoma");  map.put("OR", "Oregon");         map.put("PA", "Pennsylvania");
        map.put("RI", "Rhode Island");   map.put("SC", "South Carolina");  map.put("SD", "South Dakota");  map.put("TN", "Tennessee");
        map.put("TX", "Texas");          map.put("UT", "Utah");      map.put("VT", "Vermont");               map.put("VA", "Virginia");
        map.put("WA", "Washington");     map.put("WV", "West Virginia");  map.put("WI", "Wisconsin");        map.put("WY", "Wyoming");
        map.put("DC", "District of Columbia");  map.put("MH", "Marshall Islands");  map.put("PR", "Pueto Rico");        map.put("AS", "American Somoa");
        map.put("GU", "Guam");           map.put("MP", "Northern Marina Islanda");  map.put("VI", "US Virgin Islands");        map.put("Error", "error");
        map.put("NJ", "New Jersey");

        return Collections.unmodifiableMap(map);
    }

    //TO-DO
    // 1) Initialize Hashmap with values for the different states _ error
    // 2) Make hte recycler view cardviews expandable.

    //Next Steps:
    // 1) Add a search bar that you can search for your state
    // 2) Add a notification option so you can set notifications for specific states


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          fetchData();

        recyclerView = findViewById(R.id.recyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        Button button = (Button) findViewById(R.id.updateInfoBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fetchData();
                v.startAnimation(buttonClick);
                Toast.makeText(MainActivity.this, "Updated Data",Toast.LENGTH_SHORT).show();
            }
        });


    }



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



    public void fetchData(){

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


    public void generateErrorRecyclerView(){
        StateObj errorObj = new StateObj("Error",0,"Error",0,0,0,0,0,0,0,
                0,0,"Error",0,0,0,0,"Error");
        stateList.add(errorObj);
        recyclerAdapter = new RecyclerAdapter(stateList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public void onItemclick(int position) {
        StateObj state = stateList.get(position);
        state.setisExpanded(!state.getisExpanded());
        recyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onLongItemClick(int position) {
        // do something if the user does a long click on an item
        // -- still finding a use for htis
    }













}
