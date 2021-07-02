package com.example.annuaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=AppDatabase.getInstance(this);
        ArrayList<Personne> contatcs= (ArrayList<Personne>) db.personneDao().getAll();
        ContactsAdapter adapter = new ContactsAdapter(this, contatcs);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        MainActivity m = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                TextView ID = view.findViewById(R.id.ID);
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("ID",ID.getText().toString());
                startActivity(i);
                Log.d("1","Click working");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView ID = view.findViewById(R.id.ID);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("supprimer ce contact?")
                        .setCancelable(false)
                        .setPositiveButton("supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Personne contact = new Personne();
                                contact.setID(Integer.parseInt(ID.getText().toString()));
                                db.personneDao().delete(contact);
                                RefreshListView((ArrayList<Personne>) db.personneDao().getAll());

                            }
                        })
                        .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();

                return true;
            }
        });
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_contact_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(myIntent);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshListView((ArrayList<Personne>) db.personneDao().getAll());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MainActivity m =this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                RefreshListView((ArrayList<Personne>) db.personneDao().findByName("%"+newText+"%"));

                return false;
            }
        });
        return true;
    }

    void RefreshListView(ArrayList<Personne> contatcs){
        ContactsAdapter adapter = new ContactsAdapter(this,contatcs);
        listView.setAdapter(adapter);
    }


}

