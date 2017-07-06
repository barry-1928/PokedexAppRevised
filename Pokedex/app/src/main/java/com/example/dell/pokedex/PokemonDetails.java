package com.example.dell.pokedex;

/**
 * Created by dell on 28-06-2017.
 */

public class PokemonDetails {
    String name;
    Double weight;
    double height;
    String primary_type;
    String secondary_type;
    String image_url;
    String sprite_url;

    PokemonDetails(String name, Double weight, double height, String primary_type, String secondary_type, String image_url, String sprite_url) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.primary_type = primary_type;
        this.secondary_type = secondary_type;
        this.image_url = image_url;
        this.sprite_url = sprite_url;
    }
}
