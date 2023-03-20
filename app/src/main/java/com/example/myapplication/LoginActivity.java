package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LoginActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button googleBtn;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    FirebaseStorage storage;
    File localFile;
    StorageReference pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);
        gsc.revokeAccess();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance("gs://dumanka-e1b7e.appspot.com");
        googleBtn = findViewById(R.id.googlebtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = gsc.getSignInIntent();
                startActivityForResult(signInIntent, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        rootNode = FirebaseDatabase.getInstance("https://dumanka-e1b7e-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = rootNode.getReference("users");
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            createnewfile(firebaseUser.getUid());
                            final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.logindialog, viewGroup, false);
                            Button regbtn = dialogView.findViewById(R.id.registrationbtn);
                            //TextView header = dialogView.findViewById(R.id.header2);
                            EditText name1 = dialogView.findViewById(R.id.name);
                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();
                            regbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String username = name1.getText().toString();
                                    UserHelperClass user = new UserHelperClass(email,username, uid , 0, 0);
                                    reference.child(uid).setValue(user);
                                    Toast.makeText(LoginActivity.this, "Успешна регистрация!", Toast.LENGTH_SHORT).show();
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                    navigateToSecondActivity();
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Успешно влизане!", Toast.LENGTH_SHORT).show();
                            navigateToSecondActivity();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    void navigateToSecondActivity(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void createnewfile(String uid){
        storageReference = storage.getReference();
        pathReference = storageReference.child("words/words.txt");
        final long SIZE = 512 * 512;
        pathReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                StorageReference riversRef = storageReference.child("words/"+uid+".txt");
                UploadTask uploadTask = riversRef.putBytes(bytes);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}