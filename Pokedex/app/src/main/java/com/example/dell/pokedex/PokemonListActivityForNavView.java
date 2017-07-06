package com.example.dell.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;

public class PokemonListActivityForNavView extends AppCompatActivity {

    ArrayList<String> pokemon_list;
    ListView pokemon_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list_for_nav_view);
        pokemon_list = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.pokemon);
        CSVRead csvRead = new CSVRead(inputStream);
        pokemon_list = csvRead.read();
        pokemon_listview = (ListView) findViewById(R.id.pokemon_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pokemon_list);
        pokemon_listview.setAdapter(adapter);
    }
}
