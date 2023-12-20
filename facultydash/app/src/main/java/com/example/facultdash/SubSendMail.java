package com.example.facultdash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;

public class SubSendMail extends AppCompatActivity {

    private EditText e1;
    private EditText e2;
    private EditText e3;
    Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_send_mail);

        e1 = findViewById(R.id.ed1);
        e2 = findViewById(R.id.ed2);
        e3 = findViewById(R.id.ed3);

        b1 = findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String recipientList = e1.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = e2.getText().toString();
        String message = e3.getText().toString();

        if (recipients.length == 0 || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(SubSendMail.this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            for (String recipient : recipients) {
                if (!isValidEmail(recipient.trim())) {
                    Toast.makeText(SubSendMail.this, "Invalid email format for recipient: " + recipient, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an email client"));
        }
    }

    private boolean isValidEmail(String email) {
        // Regular expression to accept Gmail, .edu, and .in formats
        String emailPattern = "^[A-Za-z0-9+_.-]+@(gmail\\.com|edu|in)$";
        return Pattern.matches(emailPattern, email);
    }
}
