package com.example.facultdash;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SubStudentProfile2 extends AppCompatActivity {

    Button see_bigbig;
    ImageButton i71,i72,i73;
    TextView t1,t2,t3,t4,t5,t6,t7;
    String fCourse, fEmail, fName, fPassword, fPhone, fProgram, fRole, fUniqueID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_student_profile2);

        see_bigbig = findViewById(R.id.see_bigbig);


        //for intent part
        i71 = findViewById(R.id.imageButton4444);
        i72 = findViewById(R.id.imageButton5555);
        i73 = findViewById(R.id.imageButton6666);

        t1 = findViewById(R.id.tttt1);
        t2 = findViewById(R.id.tttt2);
        t3 = findViewById(R.id.tttt3);
        t4 = findViewById(R.id.tttt4);
        t5 = findViewById(R.id.tttt5);
        t6 = findViewById(R.id.tttt6);
        t7 = findViewById(R.id.tttt7);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefss", Context.MODE_PRIVATE);
        String USERID = sharedPreferences.getString("USERID", null);

        // Button to view a full-screen profile image
        see_bigbig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to display the image in full screen.
                Dialog dialog = new Dialog(SubStudentProfile2.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                // Set the image resource to the ImageView in the dialog.
                ImageView fullscreenImageView = new ImageView(SubStudentProfile2.this);
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
                        Animation animation = AnimationUtils.loadAnimation(SubStudentProfile2.this, R.anim.dialog_exit_animation);
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

        loadUserData(USERID);
    }

    private void loadUserData(String userID) {
        if (userID != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference studentRef = database.getReference("Student");

            // Query the database to find the student with the matching unique ID
            Query query = studentRef.orderByChild("UniqueID").equalTo(userID);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DataSnapshot studentSnapshot = dataSnapshot.getChildren().iterator().next();

                        fCourse = studentSnapshot.child("Course").getValue(String.class);
                        fEmail = studentSnapshot.child("Email").getValue(String.class);
                        fName = studentSnapshot.child("Name").getValue(String.class);
                        fPassword = studentSnapshot.child("Password").getValue(String.class);
                        fPhone = studentSnapshot.child("Phone").getValue(String.class);
                        fProgram = studentSnapshot.child("Program").getValue(String.class);
                        fRole = studentSnapshot.child("Role").getValue(String.class);
                        fUniqueID = studentSnapshot.child("UniqueID").getValue(String.class);

                        // set
                        t1.setText(fName);
                        t2.setText(fEmail);
                        t3.setText(fCourse);
                        t4.setText(fProgram);
                        t5.setText(fPhone);
                        t6.setText(fRole);
                        t7.setText(fUniqueID);
                    } else {
                        // Handle the case when no data with the specified unique ID is found
                        Log.e("FirebaseDataError", "No data found for uniqueID: " + userID);
                        Toast.makeText(SubStudentProfile2.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database read errors if necessary
                    Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                }
            });
        } else {
            // Handle the case when the unique ID is not found in shared preferences
            Log.e("SharedPrefsError", "Unique ID not found in shared preferences");
            Toast.makeText(SubStudentProfile2.this, "Unique ID not found", Toast.LENGTH_SHORT).show();
        }

        i71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AttendanceFlow.class));
            }
        });

        i72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyList.class));
            }
        });
        i73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubAbsentProfile.class));
            }
        });

    }
}
