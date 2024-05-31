package com.example.a71p;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShowItemActivity extends AppCompatActivity {

    TextView name, date, location;
    Button remove;
    MyDatabaseHelper myDB;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_single);




        name = findViewById(R.id.textView);
        remove = findViewById(R.id.removebtn);
        myDB = new MyDatabaseHelper(this);

        itemId = getIntent().getIntExtra("ITEM_ID", -1);
        String itemdetails = getIntent().getStringExtra("ITEM_DETAILS");
        name.setText(itemdetails);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer rows = myDB.deleteItem(String.valueOf(itemId));
                if (rows > 0){
                    Toast.makeText(ShowItemActivity.this, "Item is Removed", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ShowItemActivity.this, "Item is not Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home)
//        {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
