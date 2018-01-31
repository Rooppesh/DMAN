package com.example.hp.dman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText email,password;
    TextView regButton;
    Query query;
    com.firebase.client.Query q;
    boolean valid;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=(Button)findViewById(R.id.loginButton);
        email=(EditText)findViewById(R.id.emailField);
        password=(EditText)findViewById(R.id.passwordField);
        regButton=(TextView)findViewById(R.id.registerButton);
        valid=false;
        Firebase.setAndroidContext(getApplicationContext());
        Log.d("Login","Something");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Login","Vanthuruchu");
                final String emailid=email.getText().toString().split("\\.")[0];
                final String passwd=password.getText().toString();
                mDatabaseReference= FirebaseDatabase.getInstance().getReference("Users/"+emailid);
                query=mDatabaseReference.child("password");
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String p=dataSnapshot.getValue(String.class);
                                    Log.d("Password",p);
                                    if(p.equals(passwd)){
                                        Intent i=new Intent(MainActivity.this,DManNav.class);
                                        i.putExtra("Username",emailid);
                                        startActivity(i);
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Invalid Password", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                            Toast.makeText(getApplicationContext(),"Invalid Username", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
    }
}
