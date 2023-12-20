package com.example.facultdash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentFacultyView extends AppCompatActivity {
    SubjectAdapter subjectAdapter;
    RecyclerView recyclerView;
    String facultyid;
    String facultyName,facultyEmail,facultyProgram,facultyuniqueID;
    ArrayList<SubjectListItem>  listFacultySubjects ;

    TextView tx1,tx2,tx3,tx4;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_faculty_view);

        listFacultySubjects = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));

        tx1 = findViewById(R.id.textView1);
        tx2 = findViewById(R.id.text90);
        tx3 = findViewById(R.id.txtgmail);
        tx4 = findViewById(R.id.uni);





        facultyid = getIntent().getStringExtra("FACULTY_ID");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference facultyRef = database.getReference("Faculty");
        DatabaseReference facultyRef2 = database.getReference("AddClass");

        facultyRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot facultySnapshot : snapshot.getChildren()) {

                            if (facultySnapshot.getKey().equals(facultyid)) {
                                // You can access specific properties from the "Faculty" data here
                                ArrayList<String>  facultySubjects = new ArrayList<>();
                                for(DataSnapshot subject:facultySnapshot.getChildren()){
                                    facultySubjects.add(subject.getKey()) ;
                                }

                                for(String subject:facultySubjects){
                                    listFacultySubjects.add(new SubjectListItem(subject)) ;
                                }
                                subjectAdapter =new SubjectAdapter(getApplicationContext(),listFacultySubjects);
                                recyclerView.setAdapter(subjectAdapter);


                                Log.d("FacultyData", "Name: " + facultyName + ", Email: " + facultyEmail);
                            }
                        }
                    } else {
                        //no data
                        Log.d("FacultyData", "No data found in Faculty table");
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        facultyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot facultySnapshot : dataSnapshot.getChildren()) {

                        if (facultySnapshot.child("UniqueID").getValue(String.class).equals(facultyid)) {
                            // You can access specific properties from the "Faculty" data here
                            facultyName = facultySnapshot.child("Name").getValue(String.class);
                            facultyEmail = facultySnapshot.child("Email").getValue(String.class);
                            facultyProgram = facultySnapshot.child("Program").getValue(String.class);
                            facultyuniqueID = facultySnapshot.child("UniqueID").getValue(String.class);
                            tx1.setText(facultyName);
                            tx2.setText(facultyProgram);
                            tx3.setText(facultyEmail);
                            tx4.setText(facultyuniqueID);
                            Log.d("FacultyData", "Name: " + facultyName + ", Email: " + facultyEmail);
                        }
                    }


                } else {
                    //no data
                    Log.d("FacultyData", "No data found in Faculty table");
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // database error
                Log.e("FirebaseError", "Database Error: " + databaseError.getMessage());
            }
        });
    }
}
