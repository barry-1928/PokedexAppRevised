package com.example.dell.pokedex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 04-07-2017.
 */

public class CSVRead {
    InputStream inputStream;

    CSVRead(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<String> read() {
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            ArrayList<String> pokemon = new ArrayList<>();
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (line != null) {
                String array[] = line.split(",");
                array[1] = array[1].toUpperCase();
                pokemon.add(array[1]);
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pokemon.remove(0);
            return pokemon;
        }
        else
        {
            return null;
        }
    }
}
