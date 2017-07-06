package com.example.dell.pokedex;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;

import com.example.dell.pokedex.Databases.ContractClass;
import com.example.dell.pokedex.Databases.MyHelper;

/**
 * Created by dell on 05-07-2017.
 */

public class DialogBuilders {

    private DialogBuilders() {}

    public static void dialogForHelpInSearchHistory(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Help");
        builder.setItems(new String[] {"Browsing history comprises of a record of Pokemons that you have searched in past browsing sessions, and typically includes the name of the Pokemon as well as its corresponding image."+"\n \n"+"You can delete any of the items you want by simply long-clicking on that item." + "\n \n" + "There is also an option to clear all the previously searched history by clicking on the \"DELETE\" menu item on the Action Bar."+"\n \n"},null);
        builder.show();
    }

    public static void dialogForAppDetails(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pokedex: Gotta Catch 'em All");
        builder.setItems(new String[] {"The Pokedex App is an unofficial, free app which lets you search details for any Pokemon you desire, alongwith an image of the Pokemon."+"\n \n"+"The exhaustive list of the name of the Pokemons can be found by clicking on the \"List\" menu item on the Action Bar of this page."+"\n \n"+"No copyright infringement intended."+"\n \n"+"Enjoy the app!"+"\n \n"+"Developed by:"+"\n"+"Rajat Bhatta(106116066)"+"\n \n"+"THANK YOU! CHEERS!"+"\n \n"},null);
        builder.show();
    }
}
