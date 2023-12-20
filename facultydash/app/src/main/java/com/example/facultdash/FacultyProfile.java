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
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyProfile extends AppCompatActivity {

    Button editProfile, seeProfileButton;
    ImageView imageView5, btn2, btn22;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        seeProfileButton = findViewById(R.id.btn_see);
        imageView5 = findViewById(R.id.imageView5);
        btn2 = findViewById(R.id.imageButton4);
        btn22 = findViewById(R.id.imageButton6);
        editProfile = findViewById(R.id.editButton);



        String fname = getIntent().getStringExtra("fName");
        String femail = getIntent().getStringExtra("fEmail");
        String fcourse = getIntent().getStringExtra("fCourse");
        String fprogram = getIntent().getStringExtra("fProgram");
        String fphone = getIntent().getStringExtra("fPhone");
        String frole = getIntent().getStringExtra("fRole");
        String funiqueid = getIntent().getStringExtra("fUniqueID");
        String fpassword = getIntent().getStringExtra("fPassword");


        tv1 = findViewById(R.id.t1);
        tv2 = findViewById(R.id.t2);
        tv3 = findViewById(R.id.t3);
        tv4 = findViewById(R.id.t4);
        tv5 = findViewById(R.id.t5);
        tv6 = findViewById(R.id.t6);
        tv7 = findViewById(R.id.t7);
        tv8 = findViewById(R.id.t8);


        tv1.setText(fname);
        tv2.setText(femail);
        tv3.setText(fcourse);
        tv4.setText(fprogram);
        tv5.setText(fpassword);
        tv6.setText(fphone);
        tv7.setText(frole);
        tv8.setText(funiqueid);




        // Button to view a full-screen profile image
        seeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to display the image in full screen.
                Dialog dialog = new Dialog(FacultyProfile.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                // Set the image resource to the ImageView in the dialog.
                ImageView fullscreenImageView = new ImageView(FacultyProfile.this);
                fullscreenImageView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
                fullscreenImageView.setImageResource(R.drawable.boy1); // Replace with the actual image resource.

                // Add the ImageView to the dialog.
                dialog.setContentView(fullscreenImageView);

                // Show the dialog.
                dialog.show();

                // Add a click listener to close the dialog smoothly when the full-screen image is clicked.
                fullscreenImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Apply an exit animation
                        Animation animation = AnimationUtils.loadAnimation(FacultyProfile.this, R.anim.dialog_exit_animation);
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

        // Edit profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FacultyProfileEdit.class)); // Replace with the correct activity name.
            }
        });

        // Navigation buttons
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyClass.class)); // Replace with the correct activity name.
            }
        });

        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class)); // Replace with the correct activity name.
            }
        });
    }
}
