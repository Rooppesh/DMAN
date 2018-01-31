package com.example.hp.dman;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Checkout extends AppCompatActivity {

    Button gethelp,contact;
    TextView Description;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        gethelp=(Button)findViewById(R.id.gethelp2);
        contact=(Button)findViewById(R.id.contactdonor);
        Bundle b=getIntent().getExtras();
        final String sp=b.getString("Pincode");
        final Integer sq=Integer.parseInt(b.getString("Quantity"));
        final String restype=b.getString("Type");
        final String baseres=b.getString("ResType");
        Description=(TextView) findViewById(R.id.trdetails);
        Description.setText("Your Request: \n");
        Description.append(baseres+":"+restype+"\n");
        Description.append("Quantity:"+sq.toString()+"\n");
        Description.append("Location:"+sp);
        gethelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference mdb= FirebaseDatabase.getInstance().getReference();
                Query q2=mdb.child(baseres).child(sp);
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Query q=mdb.child(baseres).child(sp).child(restype);
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Integer i = dataSnapshot.getValue(Integer.class);
                                    if (dataSnapshot.exists()) {
                                        if (i > sq) {
                                            i = i - sq;
                                            dataSnapshot.getRef().setValue(i);
                                            Toast.makeText(getApplicationContext(),"Transaction Successful", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(getApplicationContext(), "Insufficient Resources", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Resource not present", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                            Toast.makeText(getApplicationContext(),"Area not present", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference mdb=FirebaseDatabase.getInstance().getReference();
                Query q=mdb.child("Pins").child(sp);
                Log.d("Pincode",sp);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            String phone=dataSnapshot.getValue(String.class);
                            Intent i=new Intent(Intent.ACTION_DIAL);
                            //Log.d("Phone number",phone);
                            i.setData(Uri.parse("tel:"+phone));
                            startActivity(i);
                        }
                        else
                            Log.d("Nope","Does not Exist");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
