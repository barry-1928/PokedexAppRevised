package com.example.dell.pokedex;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.dell.pokedex.Databases.ContractClass;
import com.example.dell.pokedex.Databases.MyHelper;

public class SearchHistory extends AppCompatActivity{

    RecyclerView recyclerView;
    MyAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Search History");
        toolbar.inflateMenu(R.menu.menu_search_history);
        toolbar.setTitleTextColor(Color.parseColor("#ffff00"));
        setSupportActionBar(toolbar);
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String[] columns = {ContractClass.PokemonSearchHistoryTable.POKEMON_NAME, ContractClass.PokemonSearchHistoryTable.POKEMON_SPRITE_URL};
        Cursor cursor = db.query(ContractClass.PokemonSearchHistoryTable.TABLE_NAME,columns,null,null,null,null,null);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MyAdapter(this, cursor,recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        cursor.close();
        db.close();
    }

    public void help(MenuItem menuItem) {
        DialogBuilders.dialogForHelpInSearchHistory(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_history,menu);
        return true;
    }


    public void delete_all(MenuItem menuItem) {
        if(!(adapter.name.isEmpty())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete All Items");
            builder.setItems(new String[]{"Do you want to delete all the items from the search history permanently ?"}, null);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyHelper myHelper = new MyHelper(SearchHistory.this);
                    SQLiteDatabase db = myHelper.getReadableDatabase();
                    db.execSQL("DELETE FROM " + ContractClass.PokemonSearchHistoryTable.TABLE_NAME);
                    adapter = new MyAdapter(SearchHistory.this, null, recyclerView);
                    recyclerView.setAdapter(adapter);
                    db.close();
                }
            });
            builder.show();
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete All Items");
            builder.setItems(new String[]{"Sorry! There are no items to be deleted.\n"}, null);
            builder.show();
        }
    }
}
