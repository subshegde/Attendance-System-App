package com.example.facultdash;

import static com.example.facultdash.R.id.see_big;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SubStudentProfileInStudentDashboard extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    Button seebig;

    ImageButton i012,i013,i014;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_student_profile_in_student_dashboard);

        seebig = findViewById(see_big);

        //for intent part
        i012 = findViewById(R.id.imageButton001);
        i013 = findViewById(R.id.imageButton002);
        i014 = findViewById(R.id.imageButton003);


        String sname = getIntent().getStringExtra("studentName");
        String semail = getIntent().getStringExtra("studentEmail");
        String scourse = getIntent().getStringExtra("studentCourse");
        String sprogram = getIntent().getStringExtra("studentProgram");
        String sphone = getIntent().getStringExtra("studentPhone");
        String srole = getIntent().getStringExtra("studentRole");
        String suniqueid = getIntent().getStringExtra("studentUniqueID");


        tv1 = findViewById(R.id.ttt1);
        tv2 = findViewById(R.id.ttt2);
        tv3 = findViewById(R.id.ttt3);
        tv4 = findViewById(R.id.ttt4);
        tv5 = findViewById(R.id.ttt5);
        tv6 = findViewById(R.id.ttt6);
        tv7 = findViewById(R.id.ttt7);


        tv1.setText(sname);
        tv2.setText(semail);
        tv3.setText(scourse);
        tv4.setText(sprogram);
        tv5.setText(sphone);
        tv6.setText(srole);
        tv7.setText(suniqueid);







        // Button to view a full-screen profile image
        seebig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to display the image in full screen.
                Dialog dialog = new Dialog(SubStudentProfileInStudentDashboard.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                // Set the image resource to the ImageView in the dialog.
                ImageView fullscreenImageView = new ImageView(SubStudentProfileInStudentDashboard.this);
                fullscreenImageView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
                fullscreenImageView.setImageResource(R.drawable.boy3); // Replace with the actual image resource.

                // Add the ImageView to the dialog.
                dialog.setContentView(fullscreenImageView);

                // Show the dialog.
                dialog.show();

                // Add a click listener to close the dialog smoothly when the full-screen image is clicked.
                fullscreenImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Apply an exit animation
                        Animation animation = AnimationUtils.loadAnimation(SubStudentProfileInStudentDashboard.this, R.anim.dialog_exit_animation);
                        fullscreenImageView.startAnimation(animation);

                        // Delay dialog dismissal to allow animation to complete
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        dialog.dismiss(); // Close the dialog.
                                    }
                                },
                                animation.getDuration()
                        );
                    }
                });
            }
        });

        i012.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AttendanceFlow.class));
            }
        });
        i013.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyList.class));
            }
        });
        i014.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubAbsentProfile.class));
            }
        });

    }
}