package com.example.dell.pokedex;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by dell on 28-06-2017.
 */

public class Utils {

    private static String api = "https://pokeapi.co/api/v2/pokemon/";
    static String name;
    static Double weight;
    static Double height;
    static String primary_type;
    static String secondary_type;
    static String image_url;
    static int id;
    static String sprite_url;
    static String partOfUrl = "http://assets.pokemon.com/assets/cms2/img/pokedex/full/";

    private Utils() {
    }

    public static PokemonDetails parseJsonCode(String JsonCode) {
        //Log.d("xyz",JsonCode);
        try {
            JSONObject root = new JSONObject(JsonCode);
            name = root.getString("name");
            id = root.getInt("id");
            sprite_url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"+id+".png";
            weight = ((Double)((double)root.getInt("weight")))/10;
            height = root.getInt("height")*10.0;
            JSONArray types = root.getJSONArray("types");
            int length = types.length();
            if(length == 1) {
                primary_type = types.getJSONObject(0).getJSONObject("type").getString("name");
            }
            else if(length == 2) {
                secondary_type = types.getJSONObject(0).getJSONObject("type").getString("name");
                primary_type = types.getJSONObject(1).getJSONObject("type").getString("name");
            }
            image_url = partOfUrl + String.format("%03d",id) + ".png";
            PokemonDetails pokemonDetails = new PokemonDetails(name,weight,height,primary_type,secondary_type,image_url,sprite_url);
            return pokemonDetails;

        } catch (JSONException e) {
            Log.e("xyz", e.getMessage());
        }

        return null;

    }

    public static PokemonDetails EstablishUrlConnection(String name) {
        name = name.toLowerCase();
        String jsonCode = "";
        PokemonDetails pokemonDetails;
        try {
            String url_name = api + name + "/";
            URL url = new URL(url_name);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            InputStream inputStream = null;

            if(urlConnection.getResponseCode() == 200) {
                Log.d("xyz",""+urlConnection.getResponseCode());
                inputStream = urlConnection.getInputStream();
                if(inputStream != null){
                    jsonCode = getJsonFromInputStream(inputStream);
                    pokemonDetails = parseJsonCode(jsonCode);
                    return pokemonDetails;
                }
            }
        } catch (MalformedURLException e) {
            Log.e("xyz","Error forming URL");
        } catch (IOException e) {
            Log.e("xyz","Error opening connection");
        }

        pokemonDetails = parseJsonCode(jsonCode);
        return pokemonDetails;
    }

    public static String getJsonFromInputStream(InputStream inputStream) {
        String jsonCode = "";
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            Log.e("xyz","Error reading data");
        }

        while(line != null) {
                stringBuilder.append(line);
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                Log.e("xyz","Error reading data");
            }
        }

        jsonCode = stringBuilder.toString();

        return jsonCode;

    }
}
