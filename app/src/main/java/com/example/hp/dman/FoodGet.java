package com.example.hp.dman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FoodGet extends AppCompatActivity {

    Button gethelp;
    EditText pincode;
    Spinner spinnerfood;
    EditText qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //getting from gethelp
        setContentView(R.layout.activity_food_get);
        Button checkoutHelp=(Button)findViewById(R.id.checkoutHelp);
        pincode=(EditText)findViewById(R.id.pinfoodget);
        qty=(EditText)findViewById(R.id.qtyfoodget);
        spinnerfood=(Spinner)findViewById(R.id.spinnerfoodget);
        String foodlist[]={"Rice","Wheat","Biscuit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, foodlist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfood.setAdapter(adapter);
        checkoutHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FoodGet.this,Checkout.class);
                i.putExtra("Pincode",pincode.getText().toString());
                i.putExtra("Type",spinnerfood.getSelectedItem().toString());
                i.putExtra("Quantity",qty.getText().toString());
                i.putExtra("ResType",getIntent().getExtras().getString("ResType"));
                startActivity(i);
            }
        });
    }
}
