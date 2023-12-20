package com.example.facultdash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText uniqueIdEditText, nameEditText, gmailEditText, courseEditText, programEditText, mobileEditText,Password;
    private Button facultyButton, studentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI components
        uniqueIdEditText = findViewById(R.id.editTextText4);
        nameEditText = findViewById(R.id.editTextText1);
        gmailEditText = findViewById(R.id.editTextText7);
        courseEditText = findViewById(R.id.editTextText2);
        programEditText = findViewById(R.id.editTextText3);
        mobileEditText = findViewById(R.id.editTextClass);
        Password = findViewById(R.id.editTextText6);
        facultyButton = findViewById(R.id.button1);
        studentButton = findViewById(R.id.button2);


        // Set onClick listener for the Faculty button
// Set onClick listener for the Faculty button
        facultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Faculty button click
                String id = uniqueIdEditText.getText().toString();
                if (id.startsWith("F")) {
                    sendDataToDatabase("Faculty");
                } else {
                    Toast.makeText(SignUp.this, "Check the Role According to your ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Set onClick listener for the Student button
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Student button click
                String id = uniqueIdEditText.getText().toString();
                if (id.startsWith("S")) {
                    sendDataToDatabase("Student");
                } else {
                    Toast.makeText(SignUp.this, "Check the Role According to your ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void sendDataToDatabase(String role) {
        String id = uniqueIdEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String gmail = gmailEditText.getText().toString();
        String course = courseEditText.getText().toString();
        String program = programEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String pass = Password.getText().toString();

        DatabaseReference databaseReference;
        if ("Faculty".equals(role)) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        }

        String userKey = id;

        Map<String, Object> userData = new HashMap<>();
        userData.put("Name", name);
        userData.put("Program", program);
        userData.put("Course", course);
        userData.put("Email", gmail);
        userData.put("Phone", mobile);
        userData.put("Role", role);
        userData.put("UniqueID", id);
        userData.put("Password", pass);

        databaseReference.child(userKey).setValue(userData);

        // Show a success message as a toast
        Toast.makeText(this, role + " data sent successfully", Toast.LENGTH_SHORT).show();
    }
}
