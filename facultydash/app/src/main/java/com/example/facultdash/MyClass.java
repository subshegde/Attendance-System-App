package com.example.facultdash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MyClass extends AppCompatActivity {

    LinearLayout linearLayout,Addclass,addStudentLiLayout,ChooseClass;
    EditText enterClassNameLL;
    RecyclerView recyclerView;

    ImageButton imgb1,imgb2,imgb3;
    int onoff = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        linearLayout = findViewById(R.id.addStudentLiLayout);
        ChooseClass = findViewById(R.id.linearLayout5);
        Addclass = findViewById(R.id.linearLayout);
        enterClassNameLL = findViewById(R.id.editTextText5);
        addStudentLiLayout = findViewById(R.id.addStudentLiLayout);
        recyclerView = findViewById(R.id.recycler);




        imgb1 = findViewById(R.id.imageButton4);
        imgb2 = findViewById(R.id.imageButton5);
        imgb3 = findViewById(R.id.imageButton6);
        //intent take attendance
        imgb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),TakeAttendance.class);
                startActivity(in);
            }
        });

        imgb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),AbsentStudent.class);
                startActivity(in);
            }
        });

        imgb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),StudentList.class);
                startActivity(in);
            }
        });








                Addclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onoff == 1)
                {
                    enterClassNameLL.setVisibility(View.VISIBLE);
                    addStudentLiLayout.setVisibility(View.VISIBLE);
                    onoff =0;
                } else if (onoff == 0) {

                    enterClassNameLL.setVisibility(View.GONE);
                    addStudentLiLayout.setVisibility(View.GONE);
                    onoff = 1;
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MyClass.this,studentList.class));
            }
        });

        ChooseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onoff == 1)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    onoff =0;
                } else if (onoff == 0) {

                    recyclerView.setVisibility(View.GONE);
                    onoff = 1;
                }
            }
        });

    }
}