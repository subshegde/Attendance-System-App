package com.example.facultdash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.navigation.NavigationView;

public class SubMainActivity extends AppCompatActivity {


    String fName;
    String fEmail;
    String fCourse;

    String fPassword;
    String fProgram;
    String fPhone;
    String fRole;
    String fUniqueID;

    TextView namef,emailf,coursef,phonef,programf,rolef,uniqueIDf;

    ImageButton takeattendance, absentstudent, imgBtn_menuIcon, imageButton_studentList, img_btn_myclass;
    TextView txtv1, txtv2, txt_attendanceflow1, txt_attendanceflow2, txt_studentList, txt_myclass;

    FrameLayout frml, frml1, frmlayout, frame_studentList, frm_myclass;
    ProgressBar prgbar1, prgbar2, prgbar3, progress_studentList, prgbar4;
    ImageView imgv;
    RelativeLayout relativeLayout;
    Button btnattendance_progress;
    Button logoutButton;  // Add the logout button

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView nameTextView, emailTextView, courseTextView, programTextView, passwordTextView, phoneTextView, roleTextView, uniqueIDTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        // Initialize your elements
        txtv1 = findViewById(R.id.text_takeattendance);
        takeattendance = findViewById(R.id.imageButton4);
        frml = findViewById(R.id.frame4);
        prgbar1 = findViewById(R.id.progress1);

        // for faculty page intent
        imgv = findViewById(R.id.profileImageView);
        relativeLayout = findViewById(R.id.profileLayout);

        // for absent student
        absentstudent = findViewById(R.id.imageButton7);
        frml1 = findViewById(R.id.frame1);
        txtv2 = findViewById(R.id.text_absentstudent);
        prgbar2 = findViewById(R.id.prgbarblue);

        txt_studentList = findViewById(R.id.text_studentList);
        frame_studentList = findViewById(R.id.frame2);
        progress_studentList = findViewById(R.id.progressbar_studentList);
        imageButton_studentList = findViewById(R.id.imageButton6);

        // for attendance flow
        btnattendance_progress = findViewById(R.id.btnprogress);
        prgbar3 = findViewById(R.id.prgb);
        frmlayout = findViewById(R.id.frm_attendance_flow);
        txt_attendanceflow1 = findViewById(R.id.txt_attendanceflow1);
        txt_attendanceflow2 = findViewById(R.id.txt_attendanceflow2);

        // Initialize the logout button
        logoutButton = findViewById(R.id.logoutbutton);

        //for navigationView
        imgBtn_menuIcon = findViewById(R.id.imgBtn_menuIcon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //My Class
        txt_myclass = findViewById(R.id.text_myclass);
        frm_myclass = findViewById(R.id.frame4);
        prgbar4 = findViewById(R.id.progress_myclass);
        img_btn_myclass = findViewById(R.id.imageButton5);

        //for retrieving data from the database, to set faculty data
        nameTextView = findViewById(R.id.t1);
        emailTextView = findViewById(R.id.t2);
        courseTextView = findViewById(R.id.t3);
        programTextView = findViewById(R.id.t4);
        passwordTextView = findViewById(R.id.t5);
        phoneTextView = findViewById(R.id.t6);
        roleTextView = findViewById(R.id.t7);
        uniqueIDTextView = findViewById(R.id.t8);

        String userID = getIntent().getStringExtra("USER_ID");

        if (userID == null) {
            startActivity(new Intent(SubMainActivity.this, LoginActivity.class));
        }

        // Apply a fade-in animation to the TextView
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000); // 1-second duration
        txtv1.startAnimation(fadeInAnimation);


        //using shared preference to store uniqueid so that we can use this in faculty_profile_edit page
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String facultyUniqueId =userID ; // Replace this with the actual unique ID you want to store

        editor.putString("userID", facultyUniqueId);
        editor.apply();



        // Set up onClickListeners for your elements
        setOnClickListeners();
        setLogoutButtonClickListener(); // Set up the logout button click listener
        getFacultyData(userID); // Retrieve and set faculty data
    }


    // Function to retrieve and set faculty data from the database
    private void getFacultyData(String userID) {
        Toast.makeText(SubMainActivity.this, userID, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference facultyRef = database.getReference("Faculty");

        // Query the database to find the faculty member with the matching unique ID
        Query query = facultyRef.orderByChild("UniqueID").equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot facultySnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first matching result

                    fCourse = facultySnapshot.child("Course").getValue(String.class);
                    fEmail = facultySnapshot.child("Email").getValue(String.class);
                    fName = facultySnapshot.child("Name").getValue(String.class);
                    fPassword= facultySnapshot.child("Password").getValue(String.class);
                    fPhone= facultySnapshot.child("Phone").getValue(String.class);
                    fProgram= facultySnapshot.child("Program").getValue(String.class);
                    fRole= facultySnapshot.child("Role").getValue(String.class);
                    fUniqueID= facultySnapshot.child("UniqueID").getValue(String.class);


                } else {
                    // Handle the case when no data with the specified unique ID is found
                    Log.e("FirebaseDataError", "No data found for uniqueID: " + userID);
                    Toast.makeText(SubMainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read errors if necessary
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }
        });
    }







    private void setOnClickListeners() {
        // Set up onClickListener for txt (Take attendance)
        txtv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
            }
        });

        // Set up onClickListener for FrameLayout
        frml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
            }
        });

        // Set up onClickListener for ProgressBar
        prgbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
            }
        });

        takeattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
            }
        });

        // Intent for faculty profile/edit page
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilePage = new Intent(getApplicationContext(), FacultyProfile.class);
                profilePage.putExtra("fCourse",fCourse);
                profilePage.putExtra("fEmail",fEmail);
                profilePage.putExtra("fName",fName);
                profilePage.putExtra("fPassword",fPassword);
                profilePage.putExtra("fPhone",fPhone);
                profilePage.putExtra("fProgram",fProgram);
                profilePage.putExtra("fRole",fRole);
                profilePage.putExtra("fUniqueID",fUniqueID);

                startActivity(profilePage);
            }
        });


        // Set up onClickListener for ImageButton (Absent Student)
        absentstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AbsentStudent.class));
            }
        });

        // Set up onClickListener for FrameLayout
        frml1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AbsentStudent.class));
            }
        });

        // Set up onClickListener for ProgressBar
        prgbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AbsentStudent.class));
            }
        });

        // Student List
        // Intent for attendance flow, Set up onClickListener for TextView
        txt_studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class));
            }
        });

        // Intent for attendance flow, Set up onClickListener for TextView
        frame_studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class));
            }
        });

        // Set up onClickListener for ProgressBar
        progress_studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class));
            }
        });

        imageButton_studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentList.class));
            }
        });

        // My Class
        // Intent for attendance flow, Set up onClickListener for TextView
        txt_myclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyClass.class));
            }
        });

        // Intent for attendance flow, Set up onClickListener for TextView
        frm_myclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyClass.class));
            }
        });

        // Set up onClickListener for ProgressBar
        prgbar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyClass.class));
            }
        });

        img_btn_myclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyClass.class));
            }
        });

        // Menu OnClickListener (Drawer)
        imgBtn_menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // NavigationView item OnClickListener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_take_attendance) {
                    startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
                } else if (itemId == R.id.nav_myclass) {
                    startActivity(new Intent(getApplicationContext(), MyClass.class));
                } else if (itemId == R.id.nav_studentlist) {
                    startActivity(new Intent(getApplicationContext(), StudentList.class));
                } else if (itemId == R.id.nav_absentstudent) {
                    startActivity(new Intent(getApplicationContext(), AbsentStudent.class));
                }  else if (itemId == R.id.nav_sendMail) {
                    startActivity(new Intent(getApplicationContext(), SubSendMail.class));
                } else {
                    showLogoutDialog();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setLogoutButtonClickListener() {
        // Set up onClickListener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the logout action here (e.g., clear user data)
                        // Redirect to the login page (assuming LoginActivity is the login page)
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish(); // Finish the current activity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," so dismiss the dialog
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Function to retrieve and set faculty data from the database
//    private void getFacultyData(String userID) {
//        Toast.makeText(SubMainActivity.this, userID, Toast.LENGTH_SHORT).show();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference facultyRef = database.getReference("Faculty");
//
//        // Query the database to find the faculty member with the matching unique ID
//        Query query = facultyRef.orderByChild("UniqueID").equalTo(userID);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    DataSnapshot facultySnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first matching result
//
//                    String course = facultySnapshot.child("Course").getValue(String.class);
//                    String email = facultySnapshot.child("Email").getValue(String.class);
//                    String name = facultySnapshot.child("Name").getValue(String.class);
//                    String pass = facultySnapshot.child("Password").getValue(String.class);
//                    String phone = facultySnapshot.child("Phone").getValue(String.class);
//                    String program = facultySnapshot.child("Program").getValue(String.class);
//                    String role = facultySnapshot.child("Role").getValue(String.class);
//                    String uniqueID = facultySnapshot.child("UniqueID").getValue(String.class);
//
//                    // Update the TextViews with the retrieved data
//                    nameTextView.setText(name);
//                    emailTextView.setText(email);
//                    courseTextView.setText(course);
//                    phoneTextView.setText(phone);
//                    passwordTextView.setText(pass);
//                    programTextView.setText(program);
//                    roleTextView.setText(role);
//                    uniqueIDTextView.setText(uniqueID);
//
//                    Toast.makeText(SubMainActivity.this, email, Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case when no data with the specified unique ID is found
//                    Log.e("FirebaseDataError", "No data found for uniqueID: " + userID);
//                    Toast.makeText(SubMainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database read errors if necessary
//                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
//            }
//        });
//    }

}
