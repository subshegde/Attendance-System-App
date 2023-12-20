package com.example.facultdash;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SubPerticularStudentProfile extends AppCompatActivity {

    TextView nameTextView, emailTextView, courseTextView, programTextView, phoneTextView, roleTextView, uniqueIDTextView;
    Button see_profilep;

    ImageButton i67,i68,i69;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_perticular_student_profile);

        //for full screen ( see profile)
        see_profilep = findViewById(R.id.see_profile);

        //for intent part
        i67 = findViewById(R.id.imageButton345);
        i68 = findViewById(R.id.imageButton346);
        i69 = findViewById(R.id.imageButton347);


        nameTextView = findViewById(R.id.tt1);
        emailTextView = findViewById(R.id.tt2);
        courseTextView = findViewById(R.id.tt3);
        programTextView = findViewById(R.id.tt4);
        phoneTextView = findViewById(R.id.tt5);
        roleTextView = findViewById(R.id.tt6);
        uniqueIDTextView = findViewById(R.id.tt7);

        // Get the name from the Intent
        String name = getIntent().getStringExtra("Name");

        // Call the method to retrieve data
        getDataFromName(name);
    }

    public void getDataFromName(String name) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference facultyRef = database.getReference("Student");

        // Query the database to find the student with the matching name
        Query query = facultyRef.orderByChild("Name").equalTo(name);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Loop through the results (there may be multiple students with the same name)
                    for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                        try {
                            String studentName = studentSnapshot.child("Name").getValue(String.class);
                            String email = studentSnapshot.child("Email").getValue(String.class);
                            String course = studentSnapshot.child("Course").getValue(String.class);
                            String program = studentSnapshot.child("Program").getValue(String.class);
                            String phone = studentSnapshot.child("Phone").getValue(String.class);
                            String role = studentSnapshot.child("Role").getValue(String.class);
                            String uniqueID = studentSnapshot.child("UniqueID").getValue(String.class);

                            // Set the data to the respective TextViews or handle the data as needed
                            nameTextView.setText(studentName);
                            emailTextView.setText(email);
                            courseTextView.setText(course);
                            phoneTextView.setText(phone);
                            programTextView.setText(program);
                            roleTextView.setText(role);
                            uniqueIDTextView.setText(uniqueID);

                            // If you only want to process the first matching result, you can break the loop
                            break;
                        } catch (Exception e) {
                            Log.e("FirebaseDataError", "Error: " + e.getMessage());
                        }
                    }
                } else {
                    // Handle the case when no data with the specified name is found
                    Log.e("FirebaseDataError", "No data found for name: " + name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseDataError", "Database Error: " + databaseError.getMessage());
            }
        });



        // Button to view a full-screen profile image
        see_profilep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to display the image in full screen.
                Dialog dialog = new Dialog(SubPerticularStudentProfile.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                // Set the image resource to the ImageView in the dialog.
                ImageView fullscreenImageView = new ImageView(SubPerticularStudentProfile.this);
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
                        Animation animation = AnimationUtils.loadAnimation(SubPerticularStudentProfile.this, R.anim.dialog_exit_animation);
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

        i67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AttendanceFlow.class));
            }
        });
        i68.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyList.class));
            }
        });
        i68.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubAbsentProfile.class));
            }
        });

    }
}
