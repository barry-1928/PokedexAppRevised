package com.example.dell.pokedex;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.pokedex.Databases.ContractClass;
import com.example.dell.pokedex.Databases.MyHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText pokemon_name;
    ArrayList<PokemonDetails> all_details;
    TextView no_internet_textview;
    ProgressBar loading_bar;
    Button submit;
    TextView try_again;
    Toolbar toolbar;
    RecyclerView recycler_view;
    int c=0;
    int length;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.homepage_img);
        loading_bar = (ProgressBar) findViewById(R.id.loading_spinner);
        loading_bar.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pokedex");
        toolbar.inflateMenu(R.menu.menu_main_activity);
        toolbar.setTitleTextColor(Color.parseColor("#ffff00"));
        setSupportActionBar(toolbar);
        no_internet_textview = (TextView) findViewById(R.id.no_internet);
        submit = (Button) findViewById(R.id.submit);
        pokemon_name = (EditText) findViewById(R.id.pokemon_name);
        try_again = (TextView) findViewById(R.id.try_again);
        all_details = new ArrayList<>();
    }

    public void showListOfPokemons(MenuItem menuItem) {
        Intent intent = new Intent(this,PokemonListActivityForNavView.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    class MyAsyncTask extends AsyncTask<String,Void,PokemonDetails> {

        @Override
        protected void onPreExecute() {
            loading_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected PokemonDetails doInBackground(String... params) {

            PokemonDetails pokemonDetails = Utils.EstablishUrlConnection(params[0]);
            return pokemonDetails;
        }

        @Override
        protected void onPostExecute(PokemonDetails pokemonDetails) {
            loading_bar.setVisibility(View.GONE);
            String sprite_url;
            if(pokemonDetails != null)  {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ContractClass.PokemonSearchHistoryTable.POKEMON_NAME, pokemonDetails.name);
                contentValues.put(ContractClass.PokemonSearchHistoryTable.POKEMON_SPRITE_URL, pokemonDetails.sprite_url);
                MyHelper myHelper = new MyHelper(MainActivity.this);
                SQLiteDatabase db = myHelper.getWritableDatabase();
                Long id = db.insert(ContractClass.PokemonSearchHistoryTable.TABLE_NAME, null, contentValues);
                if (id != -1) {
                    Log.d("xyz", "Insert Successful at id = " + id);
                }
                db.close();
                Log.d("xyz",""+pokemonDetails.weight);
                Intent intent = new Intent(MainActivity.this,PokemonDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("name",pokemonDetails.name);
                extras.putDouble("weight",pokemonDetails.weight);
                extras.putDouble("height",pokemonDetails.height);
                extras.putString("primary_type",pokemonDetails.primary_type);
                extras.putString("secondary_type",pokemonDetails.secondary_type);
                extras.putString("image_url",pokemonDetails.image_url);
                intent.putExtra("extras",extras);
                startActivity(intent);
                pokemon_name.setText("");
            }
            else {
                Toast.makeText(MainActivity.this, "No Pokemon with such name!", Toast.LENGTH_SHORT).show();
                pokemon_name.setText("");
            }
        }
    }

    public void submit(View view) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null) {
            if (networkInfo.isConnectedOrConnecting()) {
                new MyAsyncTask().execute(pokemon_name.getText().toString());
            }
        }
        else {
            no_internet_textview.setVisibility(View.VISIBLE);
            pokemon_name.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            try_again.setVisibility(View.VISIBLE);
            pokemon_name.setText("");
        }
    }

    public void restore_original_homepage(View view) {
        no_internet_textview.setVisibility(View.GONE);
        img.setVisibility(View.VISIBLE);
        pokemon_name.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        try_again.setVisibility(View.GONE);
    }

    public void search_history(View view) {
        Intent intent = new Intent(this,SearchHistory.class);
        startActivity(intent);
    }

    public void appDetails(MenuItem menuItem) {
        DialogBuilders.dialogForAppDetails(this);
    }
}
