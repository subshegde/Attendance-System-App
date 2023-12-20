package com.example.facultdash;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class TakeAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button submitBTN;
    String[] section = {"SUBJECTS", "Maths", "CN","DBMS","ML"};
    Spinner spin;


//    register the rv in TakeAttendanceActivity (recycleview id)
    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);



        submitBTN = findViewById(R.id.btnsubmit);


        spin = findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, section);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        // recycleView
//        typecast
        recyclerView = this.<RecyclerView>findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        creating model for the data(i.e columns name)
//        creating a java class for this (MainModel)

        // Initialize Firebase
//        FirebaseApp.initializeApp(this);
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }



//        <chat> i.e MainModel we have to pass the model , it is model class
//        Syntax

//        FirebaseRecyclerOptions<chat> options =
//                new FirebaseRecyclerOptions.Builder<chat>()
//                        .setQuery(Quaey,chat.class)
//                        .build();

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student"),MainModel.class)
                                .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);



        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TakeAttendance.this);
                builder.setMessage("You have taken attendance")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Dismiss the dialog when the "OK" button is clicked
                                dialog.dismiss();
                            }
                        });

                // Show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




    }





    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        String selectedSection = section[position];
//        Toast.makeText(getApplicationContext(), "Selected Section: " + selectedSection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
