package com.example.dell.pokedex.Databases;

import android.provider.BaseColumns;

/**
 * Created by dell on 30-06-2017.
 */

public class ContractClass {

    private ContractClass() {};

    public static class PokemonSearchHistoryTable implements BaseColumns {
        public static final String TABLE_NAME = "SEARCH_TABLE";
        public static final String POKEMON_NAME = "name";
        public static final String POKEMON_SPRITE_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+_ID+" INTEGER PRIMARY KEY, "+POKEMON_NAME+" VARCHAR(255), "+POKEMON_SPRITE_URL+" VARCHAR(255))";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME+"";
    }
}
