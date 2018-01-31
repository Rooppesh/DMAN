package com.example.hp.dman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {


    private DatabaseReference mDatabase;

    Button regButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regButton=(Button)findViewById(R.id.registerButton);
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText contact=(EditText)findViewById(R.id.contact);
        final EditText password=(EditText)findViewById(R.id.password);
        final EditText address=(EditText)findViewById(R.id.address);
        final EditText pincode=(EditText)findViewById(R.id.pincode);
        final EditText email=(EditText)findViewById(R.id.emailid);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sn=name.getText().toString();
                final String sc=contact.getText().toString();
                String sp=password.getText().toString();
                String sa=address.getText().toString();
                String spi=pincode.getText().toString();
                String se=email.getText().toString();
                final User u=new User(sn,sp,sc,sa,se,spi);
                mDatabase = FirebaseDatabase.getInstance().getReference();
                final String key=se.split("\\.")[0];
                Query q=mDatabase.child("Users").child(key);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Toast.makeText(getApplicationContext(),"Username already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dataSnapshot.getRef().setValue(u);
                            Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Query q2=mDatabase.child("Pins").child(spi);
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists())
                        {
                            dataSnapshot.getRef().setValue(sc);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
