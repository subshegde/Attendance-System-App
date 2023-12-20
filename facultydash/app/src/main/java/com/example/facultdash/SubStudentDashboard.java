package com.example.facultdash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SubStudentDashboard extends AppCompatActivity {

    String studentName;
    String studentEmail;
    String studentCourse;
    String studentProgram;
    String studentPhone;
    String studentRole;
    String studentUniqueID;



    // for quotes
    private TextView quoteTextView;
    private Handler handler;
    private String[] quotes;
    private int currentQuoteIndex = 0;

    ImageButton takeattendance, absentstudent,imgBtn_menuIcon1,imageButton_studentList,Faculty,im1 ;
    TextView txtv1, txtv2,txt_studentList;

    FrameLayout frml, frml1,  frame_studentList,fm1 ;

    ProgressBar prgbar1, prgbar2, progress_studentList;
    ImageView imgv;
    RelativeLayout relativeLayout;
    Button logoutButton;  // Add the logout button

    DrawerLayout drawerLayout1;
    NavigationView navigationView1;

    TextView nameT,emailT,courseT,phoneT,programT,roleT,uniqueIDT;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_student_dashboard);

        // Initialize your elements
        txtv1 = findViewById(R.id.text_takeattendance);
        takeattendance = findViewById(R.id.imageButton4);
        frml = findViewById(R.id.frame4);
        prgbar1 = findViewById(R.id.progress1);

        // for faculty page intent
        imgv = findViewById(R.id.sprofileImageView);
        relativeLayout = findViewById(R.id.sprofileLayout);

        // for absent student
        absentstudent = findViewById(R.id.imageButton7);
        frml1 = findViewById(R.id.frame1);
        txtv2 = findViewById(R.id.text_absentstudent);
        prgbar2 = findViewById(R.id.prgbarblue);


        txt_studentList = findViewById(R.id.text_studentList);
        frame_studentList = findViewById(R.id.frame2);
        progress_studentList = findViewById(R.id.progressbar_studentList);
        imageButton_studentList = findViewById(R.id.imageButton6);


        // Initialize the logout button
        logoutButton = findViewById(R.id.logoutbutton);

        //for navigationView
        imgBtn_menuIcon1 = findViewById(R.id.imgButton_menuIcon);
        drawerLayout1 = findViewById(R.id.drawerlayout);
        navigationView1 = findViewById(R.id.nav_view_student_dash);

        //For Attendance status
        fm1 = findViewById(R.id.frame4);
        im1 = findViewById(R.id.imageButton14);


        //for faculty
        Faculty = findViewById(R.id.imageButton5);

        //for quotes
        quoteTextView = findViewById(R.id.txt_quotes);
        handler = new Handler();

        // Sample quotes (you can add more)
        quotes = new String[]{
                "Your time is limited, don't waste it living someone else's life ",
                "Dream big and dare to fail ",
                "The future depends on what you do today ",
                "The biggest risk is not taking any risk. In a world that's changing quickly, the only strategy that is guaranteed to fail is not taking risks ",
                "Success is not final, failure is not fatal: It is the courage to continue that counts ",
                "Your education is a gift; don't forget to share it",
                "The roots of education are bitter, but the fruit is sweet"
        };

        // Start updating the text every 5 seconds
        updateQuoteText();



        nameT = findViewById(R.id.ttt1);
        emailT = findViewById(R.id.ttt2);
        courseT = findViewById(R.id.ttt3);
        phoneT = findViewById(R.id.ttt4);
        programT = findViewById(R.id.ttt5);
        roleT = findViewById(R.id.ttt6);
        uniqueIDT = findViewById(R.id.ttt7);




        String userid = getIntent().getStringExtra("USER_ID");

// Using shared preferences to store the unique ID
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefss", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERID", userid); // Store the unique ID
        editor.apply();



        // Apply a fade-in animation to the TextView
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000); // 1-second duration
        txtv1.startAnimation(fadeInAnimation);

        // Set up onClickListeners for your elements
        setOnClickListeners();
        getDataFromUniqueID(userid);
//        nameT.setText("hello");
        setLogoutButtonClickListener(); // Set up the logout button click listener
    }

//    -----------------------------------------------------------------------------------------
    private void updateQuoteText() {
        // Update the text with the current quote
        quoteTextView.setText(quotes[currentQuoteIndex]);

        // Increment the index for the next quote or wrap around
        currentQuoteIndex = (currentQuoteIndex + 1) % quotes.length;

        // Schedule the next text update after 5 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateQuoteText();
            }
        }, 4000); // 5000 milliseconds (5 seconds)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }
//    -----------------------------------------------------------------------------------------




    // get data from uniqueid
    private void getDataFromUniqueID(String userid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference studentRef = database.getReference("Student");

        // Query the database to find the student with the matching UniqueID
        Query query = studentRef.orderByChild("UniqueID").equalTo(userid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot studentSnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first matching result

                    try {
                         studentName = studentSnapshot.child("Name").getValue(String.class);
                         studentEmail = studentSnapshot.child("Email").getValue(String.class);
                         studentCourse = studentSnapshot.child("Course").getValue(String.class);
                         studentProgram = studentSnapshot.child("Program").getValue(String.class);
                         studentPhone = studentSnapshot.child("Phone").getValue(String.class);
                         studentRole = studentSnapshot.child("Role").getValue(String.class);
                         studentUniqueID = studentSnapshot.child("UniqueID").getValue(String.class);



                    } catch (Exception e) {
                        Log.e("FirebaseDataError", "Error: " + e.getMessage());
                    }
                } else {
                    // Handle the case when no data with the specified uniqueID is found
                    Log.e("FirebaseDataError", "No data found for UniqueID: " + userid);
                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseDataError", "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void setOnClickListeners() {

        Faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FacultyList.class);
                startActivity(intent);
            }
        });




        // Intent for faculty profile/edit page
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilePage = new Intent(getApplicationContext(), SubStudentProfileInStudentDashboard.class);
                profilePage.putExtra("studentName",studentName);
                profilePage.putExtra("studentEmail",studentEmail);
                profilePage.putExtra("studentCourse",studentCourse);
                profilePage.putExtra("studentProgram",studentProgram);
                profilePage.putExtra("studentPhone",studentPhone);
                profilePage.putExtra("studentRole",studentRole);
                profilePage.putExtra("studentUniqueID",studentUniqueID);

                startActivity(profilePage);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubPerticularStudentProfile.class));
            }
        });


        // Set up onClickListener for ImageButton (Absent Student)
        absentstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubAbsentProfile.class));
            }
        });

        // Set up onClickListener for FrameLayout
        frml1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubAbsentProfile.class));
            }
        });

        // Set up onClickListener for ProgressBar
        prgbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubAbsentProfile.class));
            }
        });



        //intent for Attendance status
        fm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AttendanceFlow.class));
            }
        });

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AttendanceFlow.class));
            }
        });





        // Menu Onclick event ( Drawer )
        imgBtn_menuIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Menu button clicked",Toast.LENGTH_SHORT).show();
                drawerLayout1.openDrawer(GravityCompat.START);
            }
        });



        // NavigationView item OnClickListener
        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.attendance_status) {
//                    Toast.makeText(getApplicationContext(), "Clicked Attendance Flow", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AttendanceFlow.class));
                }
                else if (itemId == R.id.faculty) {
                    startActivity(new Intent(getApplicationContext(), FacultyList.class));
                }
                else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), SubStudentProfile2.class));
                }

                else if(itemId == R.id.sendMail){
                    startActivity(new Intent(getApplicationContext(),SubSendMail.class));
                } else if (itemId == R.id.attendance_summary) {
                    startActivity(new Intent(getApplicationContext(),SubAbsentProfile.class));

                } else {
                    showLogoutDialog();
                }

                drawerLayout1.closeDrawer(GravityCompat.START);
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
                        // Replace LoginActivity.class with your actual login activity
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
}
