package com.example.facultdash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyList extends AppCompatActivity {


    ImageView i57,i58,i59,i60;
    RecyclerView recyclerView;
    ArrayList<Faculty> flist; // Change the type to Absent
    DatabaseReference databaseReference;
    FacultyListOfClass absent; // Change the adapter type to MyStudentList
    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        //for intent part
        i57 = findViewById(R.id.imageButton40);
        i58 = findViewById(R.id.imageButton5);
        i59 = findViewById(R.id.imageButton6);
        i60 = findViewById(R.id.imageButton888);

        recyclerView = findViewById(R.id.recycler);

        databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");

        flist = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        absent = new FacultyListOfClass(this, flist); // Use MyStudentList adapter
        recyclerView.setAdapter(absent);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flist.clear(); // Clear the list before populating it
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Faculty absent = snapshot1.getValue(Faculty.class);
                    flist.add(absent);
                    System.out.println(flist);
                }
                absent.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //intent part


        i57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AttendanceFlow.class));
            }
        });
        i58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyList.class));
            }
        });
        i59.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubAbsentProfile.class));
            }
        });

        i60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubStudentDashboard.class));
            }
        });


    }


}
