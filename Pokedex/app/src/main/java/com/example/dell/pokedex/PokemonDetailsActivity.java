package com.example.dell.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PokemonDetailsActivity extends AppCompatActivity {

    String name;
    Double weight;
    double height;
    String primary_type;
    String image_url;
    String secondary_type;
    TextView nameTextView;
    TextView weightTextView;
    TextView heightTextView;
    TextView primary_typeTextView;
    TextView secondary_typeTextView1,secondary_typeTextView2;
    ImageView pokemon_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Bundle extras = bundle.getBundle("extras");
        name = extras.getString("name");
        weight = extras.getDouble("weight");
        height = extras.getDouble("height");
        primary_type = extras.getString("primary_type");
        secondary_type = extras.getString("secondary_type");
        image_url = extras.getString("image_url");
        assignViews();
    }

    void assignViews() {
        pokemon_image = (ImageView) findViewById(R.id.pokemon_image);
        Picasso.with(this).load(image_url).into(pokemon_image);
        nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(name);
        weightTextView = (TextView) findViewById(R.id.weight);
        weightTextView.setText(""+weight);
        heightTextView = (TextView) findViewById(R.id.height);
        heightTextView.setText(""+height);
        primary_typeTextView = (TextView) findViewById(R.id.primary_type);
        primary_typeTextView.setText(""+primary_type);
        secondary_typeTextView1 = (TextView) findViewById(R.id.secondary_type1);
        secondary_typeTextView2 = (TextView) findViewById(R.id.secondary_type2);
        secondary_typeTextView1.setText(""+secondary_type);
        if(secondary_type == null) {
            secondary_typeTextView1.setVisibility(View.GONE);
            secondary_typeTextView2.setVisibility(View.GONE);
        }
    }
}
