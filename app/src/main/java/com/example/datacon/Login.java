package com.example.datacon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://mydata-86e0f-default-rtdb.firebaseio.com/");

    EditText UserNameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        // Initialize inputs
        UserNameInput = findViewById(R.id.UserNameInput);
        passwordInput = findViewById(R.id.passwordInput);
    }

    public void loginbtn(View view) {
        String UsreNameTxt = UserNameInput.getText().toString();
        String passwordTxt = passwordInput.getText().toString();

        if (UsreNameTxt.isEmpty() || passwordTxt.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your UserName or password", Toast.LENGTH_SHORT).show();
        } else {

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(UsreNameTxt)) {
                        String getPassword = snapshot.child(UsreNameTxt).child("Password").getValue(String.class);
                      if (getPassword != null && getPassword.equals(passwordTxt)) {
                            Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this , HomePage.class);
                            intent.putExtra("userName", UsreNameTxt);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        Toast.makeText(Login.this, "Wrong UserName", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void registerbtn(View view) {
    }
}
