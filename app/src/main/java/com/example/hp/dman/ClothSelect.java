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

public class ClothSelect extends AppCompatActivity {

    Spinner spinnercloth;
    String clothtype;
    EditText pinEdit;
    EditText qEdit;
    Button clothhelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_select);
        spinnercloth=(Spinner)findViewById(R.id.spinnercloth);
        String medlist[]={"Sarees","Shirts","Pants","Blankets"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, medlist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercloth.setAdapter(adapter);
        clothhelp=(Button)findViewById(R.id.clothhelp);
        clothhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothtype = spinnercloth.getSelectedItem().toString();
                final DatabaseReference mdb=FirebaseDatabase.getInstance().getReference();
                pinEdit=(EditText)findViewById(R.id.pincloth);
                qEdit=(EditText)findViewById(R.id.qtycloth);
                final String sp=pinEdit.getText().toString();
                final Integer sq=Integer.parseInt(qEdit.getText().toString());
                Query q2=mdb.child("Clothing").child(sp);
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Query q=mdb.child("Clothing").child(sp).child(clothtype);
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
                            dataSnapshot.getRef().child(clothtype).setValue(sq);
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