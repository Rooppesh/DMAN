package com.example.hp.dman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FoodSelect extends AppCompatActivity {

    Spinner spinnerfood;
    String foodtype;
    EditText pinEdit;
    EditText qEdit;
    Button foodhelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);
        spinnerfood=(Spinner)findViewById(R.id.spinnerfood);
        String foodlist[]={"Rice","Wheat","Biscuit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, foodlist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfood.setAdapter(adapter);
        foodhelp=(Button)findViewById(R.id.medhelp);
        foodhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodtype = spinnerfood.getSelectedItem().toString();
                final DatabaseReference mdb=FirebaseDatabase.getInstance().getReference();
                pinEdit=(EditText)findViewById(R.id.pinfood);
                qEdit=(EditText)findViewById(R.id.qtyfood);
                final String sp=pinEdit.getText().toString();
                final Integer sq=Integer.parseInt(qEdit.getText().toString());
                Query q2=mdb.child("Food").child(sp);
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Query q=mdb.child("Food").child(sp).child(foodtype);
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        Integer i = dataSnapshot.getValue(Integer.class);
                                        i=i+sq;
                                        dataSnapshot.getRef().setValue(i);
                                    }
                                    else
                                    {
                                        dataSnapshot.getRef().setValue(sq);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            dataSnapshot.getRef().child(foodtype).setValue(sq);
                        }

                        Toast.makeText(getApplicationContext(),"Transaction Successful",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
