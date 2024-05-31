package com.example.a71p;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ListView listView;
    MyDatabaseHelper myDB;
    ArrayList<Integer> itemids;
    ArrayList<String> list;
    ArrayAdapter<String> totallist;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listView = findViewById(R.id.listview);
        myDB = new MyDatabaseHelper(this);
        list = new ArrayList<>();
        itemids = new ArrayList<>();

        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = itemids.get(position);
                String itemDetails = list.get(position);

                Intent intent = new Intent(ShowActivity.this, ShowItemActivity.class);
                intent.putExtra("ITEM_ID", itemId);
                intent.putExtra("ITEM_DETAILS", itemDetails);
                startActivity(intent);
            }
        });
    }

    private void viewData() {
        Cursor Cursor = myDB.getAllItems();

        if (Cursor.getCount()==0){
            Toast.makeText(this, "No data to display", Toast.LENGTH_SHORT).show();
        } else {
            while (Cursor.moveToNext())
            {
                itemids.add(Cursor.getInt(0));
                list.add(Cursor.getString(1)+" " + Cursor.getString(2)+" " + Cursor.getString(3)+" " + Cursor.getString(4)+" " + Cursor.getString(5));
            }
            totallist = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(totallist);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
