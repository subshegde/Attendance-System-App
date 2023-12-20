package com.example.facultdash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceFlow extends AppCompatActivity {

    Spinner spinner1;
    ArrayAdapter<String> spinnerAdapter1;
    List<String> childNames1 = new ArrayList<>();

    Button bb1, bb2;
    TextView tu, tu1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_flow);

        spinner1 = findViewById(R.id.spn1);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefss", Context.MODE_PRIVATE);
        String USERID = sharedPreferences.getString("USERID", null);

        getFacultyChildren1(USERID);

        bb1 = findViewById(R.id.your_button_id);
        bb2 = findViewById(R.id.your_button_id1);


        tu = findViewById(R.id.t000);
        tu1 = findViewById(R.id.t0000);

        // Set an OnItemSelectedListener to the spinner
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                String selectedValue = spinnerAdapter1.getItem(position);
                for (String childName : childNames1) {
                    if (selectedValue.equals("Maths")) {
                        int present = 12;
                        int absent = 3;
                        int total = present + absent;
                        float present_per = (present * 100) / total;
                        float absent_per = (absent * 100) / total;

                        tu.setText(String.valueOf(present_per+"%"));
                        tu1.setText(String.valueOf(absent_per+"%"));

                        float nn1 = 5 * present_per;
                        float nn2 = 5 * absent_per;

                        ViewGroup.LayoutParams layoutParams1 = bb1.getLayoutParams();
                        layoutParams1.height = (int)nn1;
                        bb1.setLayoutParams(layoutParams1);

                        ViewGroup.LayoutParams layoutParams2 = bb2.getLayoutParams();
                        layoutParams2.height = (int)nn2;
                        bb2.setLayoutParams(layoutParams2);
                    } else if (selectedValue.equals("CN")) {
                        int present = 14;
                        int absent = 1;
                        int total = present + absent;
                        float present_per = (present * 100) / total;
                        float absent_per = (absent * 100) / total;

                        tu.setText(String.valueOf(present_per+"%"));
                        tu1.setText(String.valueOf(absent_per+"%"));

                        float nn1 = 5 * present_per;
                        float nn2 = 5 * absent_per;

                        ViewGroup.LayoutParams layoutParams1 = bb1.getLayoutParams();
                        layoutParams1.height = (int)nn1;
                        bb1.setLayoutParams(layoutParams1);

                        ViewGroup.LayoutParams layoutParams2 = bb2.getLayoutParams();
                        layoutParams2.height = (int)nn2;
                        bb2.setLayoutParams(layoutParams2);
                    }
                    else if (selectedValue.equals("DBMS")) {
                        int present = 30;
                        int absent = 12;
                        int total = present + absent;
                        float present_per = (present * 100) / total;
                        float absent_per = (absent * 100) / total;

                        tu.setText(String.valueOf(present_per+"%"));
                        tu1.setText(String.valueOf(absent_per+"%"));

                        float nn1 = 5 * present_per;
                        float nn2 = 5 * absent_per;

                        ViewGroup.LayoutParams layoutParams1 = bb1.getLayoutParams();
                        layoutParams1.height = (int)nn1;
                        bb1.setLayoutParams(layoutParams1);

                        ViewGroup.LayoutParams layoutParams2 = bb2.getLayoutParams();
                        layoutParams2.height = (int)nn2;
                        bb2.setLayoutParams(layoutParams2);
                    }
                    else if (selectedValue.equals("ML")) {
                        int present = 8;
                        int absent = 7;
                        int total = present + absent;
                        float present_per = (present * 100) / total;
                        float absent_per = (absent * 100) / total;

                        tu.setText(String.valueOf(present_per+"%"));
                        tu1.setText(String.valueOf(absent_per+"%"));

                        float nn1 = 5 * present_per;
                        float nn2 = 5 * absent_per;

                        ViewGroup.LayoutParams layoutParams1 = bb1.getLayoutParams();
                        layoutParams1.height = (int)nn1;
                        bb1.setLayoutParams(layoutParams1);

                        ViewGroup.LayoutParams layoutParams2 = bb2.getLayoutParams();
                        layoutParams2.height = (int)nn2;
                        bb2.setLayoutParams(layoutParams2);
                    }
                    else if (selectedValue.equals("VB")) {
                        int present = 10;
                        int absent = 3;
                        int total = present + absent;
                        float present_per = (present * 100) / total;
                        float absent_per = (absent * 100) / total;

                        tu.setText(String.valueOf(present_per+"%"));
                        tu1.setText(String.valueOf(absent_per+"%"));

                        float nn1 = 5 * present_per;
                        float nn2 = 5 * absent_per;

                        ViewGroup.LayoutParams layoutParams1 = bb1.getLayoutParams();
                        layoutParams1.height = (int)nn1;
                        bb1.setLayoutParams(layoutParams1);

                        ViewGroup.LayoutParams layoutParams2 = bb2.getLayoutParams();
                        layoutParams2.height = (int)nn2;
                        bb2.setLayoutParams(layoutParams2);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // This method is called when no item is selected in the spinner
            }
        });
    }

    private void getFacultyChildren1(String USERID) {
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
                                        childNames1.add(childSnap3.getKey());
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
                Toast.makeText(AttendanceFlow.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadInSpinner() {
        spinnerAdapter1 = new ArrayAdapter<>(AttendanceFlow.this, android.R.layout.simple_spinner_item, childNames1);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter1);
    }
}
