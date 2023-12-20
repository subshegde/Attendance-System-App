package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    Button login,singup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance(); // Initialize Firebase Authentication

        // Initialize EditText and Button
        user = findViewById(R.id.editTextText2);
        password = findViewById(R.id.editTextText);
        login = findViewById(R.id.but1);
        singup = findViewById(R.id.button);


        // Attach TextWatcher to the EditText
        user.addTextChangedListener(textWatcher);

        // Set a click listener for the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine if it's a Faculty or Student and call the respective login function
                String userID = user.getText().toString();
                if (userID.startsWith("F")) {
                    loginUserFaculty();
                } else if (userID.startsWith("S")) {
                    loginUserStudent();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid User Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TextWatcher is for watching any changes in EditText
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // This function is called before text is edited
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // This function is called during text input
            String id = user.getText().toString();
            updateButton(id);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // This function is called after text is edited
        }
    };

    private void updateButton(String id) {
        if (id.startsWith("F")) {
            login.setText("Faculty");
        } else if (id.startsWith("S")) {
            login.setText("Student");
        } else {
            login.setText("Login"); // Default text for other cases
        }
    }
    public void signup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    // Faculty login
    private void loginUserFaculty() {
        final String userID = user.getText().toString();
        final String pass = password.getText().toString();

        if (userID.isEmpty() || pass.isEmpty()) {
            // Validate that both fields are not empty
            Toast.makeText(this, "Both fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the "Faculty" node in the Realtime Database
        DatabaseReference facultyRef = FirebaseDatabase.getInstance().getReference("Faculty");

        facultyRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("Password").getValue(String.class);

                    if (pass.equals(storedPassword)) {

                        // Start Faculty activity
                        Intent intent = new Intent(LoginActivity.this, SubMainActivity.class);
                        intent.putExtra("USER_ID", userID);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Student login
    private void loginUserStudent() {
        final String userID = user.getText().toString();
        final String pass = password.getText().toString();

        if (userID.isEmpty() || pass.isEmpty()) {
            // Validate that both fields are not empty
            Toast.makeText(this, "Both fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the "Student" node in the Realtime Database
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("Student");
        studentRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("Password").getValue(String.class);

                    if (pass.equals(storedPassword)) {

                        // Start Student activity
                        Intent intent = new Intent(LoginActivity.this, SubStudentDashboard.class);
                        intent.putExtra("USER_ID", userID);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
