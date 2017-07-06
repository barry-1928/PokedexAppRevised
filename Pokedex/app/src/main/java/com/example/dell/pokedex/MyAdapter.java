package com.example.dell.pokedex;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.pokedex.Databases.ContractClass;
import com.example.dell.pokedex.Databases.MyHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.dell.pokedex.Databases.ContractClass.PokemonSearchHistoryTable.POKEMON_NAME;
import static com.example.dell.pokedex.Databases.ContractClass.PokemonSearchHistoryTable.POKEMON_SPRITE_URL;


/**
 * Created by dell on 01-07-2017.
 */

public class MyAdapter extends android.support.v7.widget.RecyclerView.Adapter<MyAdapter.Holder> {

    LayoutInflater layoutInflater;
    ArrayList<String> name;
    ArrayList<String> path;
    Context context;
    Cursor cursor;
    ArrayList<name_url> details;
    int details_size;
    LinkedHashSet<String> set;
    MyAdapter adapter;
    RecyclerView recyclerView;

    MyAdapter(Context context, Cursor cursor, RecyclerView recyclerView) {
        adapter=this;
        this.recyclerView = recyclerView;
        this.context = context;
        this.cursor = cursor;
        name = new ArrayList<>();
        path = new ArrayList<>();
        details = new ArrayList<>();
        set = new LinkedHashSet<>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(cursor.getColumnIndex(POKEMON_NAME)));
                path.add(cursor.getString(cursor.getColumnIndex(POKEMON_SPRITE_URL)));
            }
        }
        Collections.reverse(path);
        Collections.reverse(name);
        set.addAll(name);
        name.clear();
        name.addAll(set);
        set.clear();
        set.addAll(path);
        path.clear();
        path.addAll(set);
        int n = name.size();
        for(int i=0;i<n;i++) {
            details.add(new name_url(name.get(i),path.get(i)));
        }
        details_size = details.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Picasso.with(context).load(details.get(position).url).into(holder.imageView);
        holder.textView.setText(details.get(position).name);

        //Log.d("xyz",details.get(details_size-1-position).url+" "+details.get(details_size-1-position).name);
    }

    @Override
    public int getItemCount() {
        return details_size;
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.row_image);
            textView = (TextView) itemView.findViewById(R.id.row_text);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Do you want to delete this row permanently ?");
                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyHelper myHelper = new MyHelper(context);
                            SQLiteDatabase db = myHelper.getReadableDatabase();
                            db.delete(ContractClass.PokemonSearchHistoryTable.TABLE_NAME,""+ContractClass.PokemonSearchHistoryTable.POKEMON_NAME+"=?",new String[] {name.get(getAdapterPosition())});
                            String[] columns = {ContractClass.PokemonSearchHistoryTable.POKEMON_NAME, ContractClass.PokemonSearchHistoryTable.POKEMON_SPRITE_URL};
                            Cursor cursor = db.query(ContractClass.PokemonSearchHistoryTable.TABLE_NAME,columns,null,null,null,null,null);
                            MyAdapter adapter1 = new MyAdapter(context,cursor,recyclerView);
                            adapter = adapter1;
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            cursor.close();
                            db.close();
                        }
                    });
                    builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.show();
                return true;
                }
            });
        }
    }

    class name_url {
        String name;
        String url;

        name_url(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}
