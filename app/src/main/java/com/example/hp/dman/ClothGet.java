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

public class ClothGet extends AppCompatActivity {

    Button gethelp;
    EditText pincode;
    Spinner spinnercloth;
    EditText qty;
    Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_get);
        checkout=(Button)findViewById(R.id.clothcheck);
        pincode=(EditText)findViewById(R.id.pinclothget);
        qty=(EditText)findViewById(R.id.qtyclothget);
        spinnercloth=(Spinner)findViewById(R.id.spinnerclothget);
        final String res_type=getIntent().getExtras().getString("ResType");
        String foodlist[]={"Sarees","Shirts","Pants","Blankets"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, foodlist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercloth.setAdapter(adapter);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClothGet.this,Checkout.class);
                i.putExtra("ResType",res_type);
                i.putExtra("Pincode",pincode.getText().toString());
                i.putExtra("Quantity",qty.getText().toString());
                i.putExtra("Type",spinnercloth.getSelectedItem().toString());
                startActivity(i);

            }
        });
    }
}
