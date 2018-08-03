package com.psau.wajjeh;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    TextView floor1, floor2, floor3, floor4, general;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floor1 = findViewById(R.id.floorOne);
        floor2 = findViewById(R.id.floorTwo);
        floor3 = findViewById(R.id.floorThree);
        floor4 = findViewById(R.id.floorFour);

        general  = findViewById(R.id.generalStatus);



        ref.child("floors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] values = new String[10];
                int counter = 0;
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    values[counter++] = s.getValue(String.class);
                }

                showValues (values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showValues (String[] values){

        boolean f1 = values[0].equals("croud");
        floor1.setText(getString(f1 ? R.string.crowded : R.string.not_crowded));
        floor1.setBackgroundColor(Color.parseColor(f1 ? "#f21633" : "#51dc3f"));

        boolean f2 = values[1].equals("croud");
        floor2.setText(getString(f2 ? R.string.crowded : R.string.not_crowded));
        floor2.setBackgroundColor(Color.parseColor(f2 ? "#f21633" : "#51dc3f"));

        boolean f3 = values[2].equals("croud");
        floor3.setText(getString(f3 ? R.string.crowded : R.string.not_crowded));
        floor3.setBackgroundColor(Color.parseColor(f3 ? "#f21633" : "#51dc3f"));

        boolean f4 = values[3].equals("croud");
        floor4.setText(getString(f4 ? R.string.crowded : R.string.not_crowded));
        floor4.setBackgroundColor(Color.parseColor(f4 ? "#f21633" : "#51dc3f"));


    }
}
