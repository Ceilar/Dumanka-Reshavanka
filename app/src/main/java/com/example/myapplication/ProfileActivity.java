package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView name, email, words, equations;
    ImageView profilepic;
    Button logoutbtn;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        profilepic = findViewById(R.id.profilepic);

        logoutbtn = findViewById(R.id.logoutbtn);
        words = findViewById(R.id.words);
        name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        email.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        words.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        equations = findViewById(R.id.equations);
        equations.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://dumanka-e1b7e-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        myRef.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                String username ="Име:  " + String.valueOf(dataSnapshot.child("name").getValue());
                String wordsguessed = "Отгатнати думи: " +String.valueOf(dataSnapshot.child("words").getValue());
                String equationsguessed = "Отгатнати уравнения: "+String.valueOf(dataSnapshot.child("equations").getValue());
                name.setText(username);
                equations.setText(equationsguessed);
                words.setText(wordsguessed);
            }
        });
        Uri imgUri = user.getPhotoUrl();
        Picasso.get().load(imgUri).into(profilepic);
        email.setText(user.getEmail());
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}