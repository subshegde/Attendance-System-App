package com.example.facultdash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class StudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterStudentList mainAdapter;

    ImageButton imageButton_takeAttendance,imageButton_Attendance_flow,imageButton_goBack;

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
        setContentView(R.layout.activity_student_list);


        //intent for takeAttendance
        imageButton_takeAttendance = findViewById(R.id.imageButton_takeattendance);
        imageButton_takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), TakeAttendance.class);
                startActivity(intent);
            }
        });

        //intent for takeAttendance
        imageButton_Attendance_flow = findViewById(R.id.imageButton_attendance_flow);
        imageButton_Attendance_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), AttendanceFlow.class);
                startActivity(intent);
            }
        });

        // intent for go back ( faculty dashboard)
        imageButton_goBack = findViewById(R.id.imageButton_go_faculty_dashboard);
        imageButton_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), SubMainActivity.class);
                startActivity(intent);
            }
        });



        //intent for myclass
//        imageButton_Attendance_flow = findViewById(R.id.imageButton_attendance_flow);
//        imageButton_Attendance_flow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(getApplicationContext(), AttendanceFlow.class);
//                startActivity(intent);
//            }
//        });






        // recycleView
//        typecast
        recyclerView = this.<RecyclerView>findViewById(R.id.recycleViewStudentList);
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

        FirebaseRecyclerOptions<MainModelStudentList> options=
                new FirebaseRecyclerOptions.Builder<MainModelStudentList>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student"),MainModelStudentList.class)
                        .build();

        mainAdapter = new MainAdapterStudentList(options);
        recyclerView.setAdapter(mainAdapter);



    }
}