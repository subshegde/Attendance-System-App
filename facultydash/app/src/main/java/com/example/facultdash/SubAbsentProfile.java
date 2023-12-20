package com.example.facultdash;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class SubAbsentProfile extends AppCompatActivity {
    String fnm, femail;
    ImageView i66;
    TextView tttttt1, tttttt2, tv1, tv2, tv3;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    List<String> childNames = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_absent_profile);

        tv1 = findViewById(R.id.text44);
        tv2 = findViewById(R.id.text33);
        tv3 = findViewById(R.id.text22);
        tttttt1 = findViewById(R.id.textView22);
        tttttt2 = findViewById(R.id.textView33);
        spinner = findViewById(R.id.spn);

        i66 = findViewById(R.id.imageView55);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefss", Context.MODE_PRIVATE);
        String USERID = sharedPreferences.getString("USERID", null);

        if (USERID != null) {
            getFacultyChildren(USERID);
            getStudentDetails(USERID);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                String selectedValue = spinnerAdapter.getItem(position);
                for (String childName : childNames) {
                    if (selectedValue.equals("Maths")) {

                        int present = 12;
                        int absent = 3;
                        int total = present + absent;
                        tv1.setText(String.valueOf(present));
                        tv2.setText(String.valueOf(absent));
                        tv3.setText(String.valueOf(total));
//                        Toast.makeText(SubAbsentProfile.this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedValue.equals("CN")){
                        int present = 14;
                        int absent = 1;
                        int total = present + absent;
                        tv1.setText(String.valueOf(present));
                        tv2.setText(String.valueOf(absent));
                        tv3.setText(String.valueOf(total));
                    }
                    else if(selectedValue.equals("DBMS")){
                        int present = 30;
                        int absent = 12;
                        int total = present + absent;
                        tv1.setText(String.valueOf(present));
                        tv2.setText(String.valueOf(absent));
                        tv3.setText(String.valueOf(total));
                    }
                    else if(selectedValue.equals("ML")){
                        int present = 8;
                        int absent = 7;
                        int total = present + absent;
                        tv1.setText(String.valueOf(present));
                        tv2.setText(String.valueOf(absent));
                        tv3.setText(String.valueOf(total));
                    }
                    else if(selectedValue.equals("VB")){
                        int present = 10;
                        int absent = 3;
                        int total = present + absent;
                        tv1.setText(String.valueOf(present));
                        tv2.setText(String.valueOf(absent));
                        tv3.setText(String.valueOf(total));
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // This method is called when no item is selected in the spinner
            }
        });
    }

    private void getFacultyChildren(String USERID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Absent");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot childSnap2 : childSnapshot.getChildren()) {
                            for (DataSnapshot childSnap3 : childSnap2.getChildren()) {
                                for (DataSnapshot childSnap4 : childSnap3.getChildren()) {
                                    String studentId = childSnap4.getKey();
                                    if (USERID.contains(studentId)) {
                                        childNames.add(childSnap3.getKey());
                                    }
                                }
                            }
                        }
                    }
                    loadInSpinner();
                } else {
                    // Handle the case when there are no children under USERID in the "Faculty" table
                    // You might want to display a message or handle this scenario
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if needed
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                Toast.makeText(SubAbsentProfile.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadInSpinner() {
        spinnerAdapter = new ArrayAdapter<>(SubAbsentProfile.this, android.R.layout.simple_spinner_item, childNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void getStudentDetails(String userid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference studentRef = database.getReference("Student");

        Query query = studentRef.orderByChild("UniqueID").equalTo(userid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot studentSnapshot = dataSnapshot.getChildren().iterator().next();

                    femail = studentSnapshot.child("Email").getValue(String.class);
                    fnm = studentSnapshot.child("Name").getValue(String.class);

                    tttttt1.setText(fnm);
                    tttttt2.setText(femail);
                } else {
                    // Handle the case when no data with the specified unique ID is found
                    Log.e("FirebaseDataError", "No data found for uniqueID: " + userid);
                    Toast.makeText(SubAbsentProfile.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read errors if necessary
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                Toast.makeText(SubAbsentProfile.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        i66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to display the image in full screen.
                Dialog dialog = new Dialog(SubAbsentProfile.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                // Set the image resource to the ImageView in the dialog.
                ImageView fullscreenImageView = new ImageView(SubAbsentProfile.this);
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
                        Animation animation = AnimationUtils.loadAnimation(SubAbsentProfile.this, R.anim.dialog_exit_animation);
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

    }
}
