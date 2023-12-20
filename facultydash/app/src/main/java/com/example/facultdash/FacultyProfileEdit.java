package com.example.facultdash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FacultyProfileEdit extends AppCompatActivity {

    EditText editName, editEmail, editDepartment, editProgram, editPassword;
    Button discardButton, saveButton;
    ImageButton im;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile_edit);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");

        editName = findViewById(R.id.e1);
        editEmail = findViewById(R.id.e2);
        editDepartment = findViewById(R.id.e3);
        editProgram = findViewById(R.id.e4);
        editPassword = findViewById(R.id.e5);
        discardButton = findViewById(R.id.discardchanges);
        saveButton = findViewById(R.id.saveButton);

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the EditText fields
                editName.setText("");
                editEmail.setText("");
                editDepartment.setText("");
                editProgram.setText("");
                editPassword.setText("");

                AlertDialog.Builder builder = new AlertDialog.Builder(FacultyProfileEdit.this);
                builder.setMessage("Discarded")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Dismiss the dialog when the "OK" button is clicked
                                dialog.dismiss();
                            }
                        });

                // Show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference facultyRef = database.getReference("Faculty");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String userID = sharedPreferences.getString("userID", null);

                if (userID != null) {
                    // Only if 'userID' is available, proceed with updating the profile

                    final String name = editName.getText().toString().trim();
                    final String email = editEmail.getText().toString().trim();
                    final String department = editDepartment.getText().toString().trim();
                    final String program = editProgram.getText().toString().trim();
                    final String newPassword = editPassword.getText().toString().trim();

                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Name", name);
                    updateData.put("Email", email);
                    updateData.put("Course", department);
                    updateData.put("Program", program);
                    updateData.put("Password", newPassword);

                    facultyRef.child(userID) // Use the 'userID' to identify the faculty
                            .updateChildren(updateData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Data updated successfully
                                        AlertDialog.Builder builder = new AlertDialog.Builder(FacultyProfileEdit.this);
                                        builder.setMessage("Your profile updated")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // Dismiss the dialog when the "OK" button is clicked
                                                        dialog.dismiss();
                                                    }
                                                });

                                        // Show the AlertDialog
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(FacultyProfileEdit.this);
                                        builder.setMessage("Failed to update profile")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // Dismiss the dialog when the "OK" button is clicked
                                                        dialog.dismiss();
                                                    }
                                                });

                                        // Show the AlertDialog
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                } else {
                    // Handle the case where 'userID' is not available (e.g., not retrieved from SharedPreferences)
                    AlertDialog.Builder builder = new AlertDialog.Builder(FacultyProfileEdit.this);
                    builder.setMessage("User ID not found")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Dismiss the dialog when the "OK" button is clicked
                                    dialog.dismiss();
                                }
                            });

                    // Show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                // Clear the EditText fields
                editName.setText("");
                editEmail.setText("");
                editDepartment.setText("");
                editProgram.setText("");
                editPassword.setText("");
            }
        });
    }
}
