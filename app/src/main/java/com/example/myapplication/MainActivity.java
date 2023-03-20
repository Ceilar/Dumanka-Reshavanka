package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Switch switchBtn;
    int rowx=0;
    String wordtocheck;
    String equationtocheck;
    MyKeyboard mykeyboard;
    MyKeyboard2 mykeyboard2;
    Button wordbtn, startbtn;
    Button mathbtn;
    LinkedList<String> list;
    LinkedList<EditText> list2 = new LinkedList<EditText>();
    LinkedList<EditText> list3 = new LinkedList<EditText>();
    ViewPager2 vpager;
    TabLayout tabLayout;
    Equations equations;
    ViewPagerAdapter viewPagerAdapter;
    Button profilebtn;
    SharedPreferences myPreferences;
    SharedPreferences.Editor editor;
    LottieAnimationView animationView;
    LottieAnimationView animationView1;
    StorageReference storageReference;
    boolean guesswasmade;
    int random;
    boolean startflag;
    FirebaseStorage storage;
    StorageReference pathReference;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    File newfile;
    boolean flag1;
    private static String MY_PREFS = "switch_prefs";
    private static String SWITCH_STATUS = "switch_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://dumanka-e1b7e-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        storage = FirebaseStorage.getInstance("gs://dumanka-e1b7e.appspot.com");
        profilebtn = findViewById(R.id.profilebtn);
        switchBtn = findViewById(R.id.switchbtn);
        wordbtn = findViewById(R.id.wordbtn);
        mathbtn = findViewById(R.id.mathbtn);
        vpager = findViewById(R.id.viewpager);
        startbtn = findViewById(R.id.start1);
        tabLayout = findViewById(R.id.tablayout);
        flag1 = true;
        guesswasmade = false;
        equations = new Equations();
        viewPagerAdapter = new ViewPagerAdapter(this);
        myPreferences  = getSharedPreferences(MY_PREFS,MODE_PRIVATE);
        startflag = true;
        newfile = fetchfile();
        editor = getSharedPreferences(MY_PREFS,MODE_PRIVATE).edit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        boolean switch_status = myPreferences.getBoolean(SWITCH_STATUS, false);
        switchBtn.setChecked(switch_status);
        if(switch_status){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(switchBtn.isChecked()) {
                   editor.putBoolean(SWITCH_STATUS,true);
                   editor.apply();
                   switchBtn.setChecked(true);
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
               }
               else {
                   editor.putBoolean(SWITCH_STATUS,false);
                   editor.apply();
                   switchBtn.setChecked(false);
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
               }
            }
        });
        vpager.setAdapter(viewPagerAdapter);
        profilebtn.setEnabled(true);
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        vpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                startbtn.setVisibility(View.VISIBLE);
                if (position == 1) {
                    mathbtn.setBackgroundResource(R.drawable.selected);
                    wordbtn.setBackgroundResource(R.drawable.notselected);
                    animationView = findViewById(R.id.animationView1);
                    animationView1 = findViewById(R.id.animationView2);
                    mykeyboard = findViewById(R.id.keyboard);
                    LinkedList<EditText> list1 = new LinkedList<EditText>();
                    EditText equation = findViewById(R.id.equation1);
                    EditText text1 = findViewById(R.id.text1r);
                    EditText text2 = findViewById(R.id.text2r);
                    EditText text3 = findViewById(R.id.text3r);
                    EditText text4 = findViewById(R.id.text4r);
                    EditText text5 = findViewById(R.id.text5r);
                    EditText text6 = findViewById(R.id.text6r);
                    EditText text7 = findViewById(R.id.text7r);
                    EditText text8 = findViewById(R.id.text8r);
                    EditText text9 = findViewById(R.id.text9r);
                    EditText text10 = findViewById(R.id.text10r);
                    EditText text11= findViewById(R.id.text11r);
                    EditText text12 = findViewById(R.id.text12r);
                    EditText text13 = findViewById(R.id.text13r);
                    EditText text14 = findViewById(R.id.text14r);
                    EditText text15 = findViewById(R.id.text15r);
                    EditText text16 = findViewById(R.id.text16r);
                    EditText text17 = findViewById(R.id.text17r);
                    EditText text18 = findViewById(R.id.text18r);
                    EditText text19 = findViewById(R.id.text19r);
                    EditText text20 = findViewById(R.id.text20r);
                    EditText text21 = findViewById(R.id.text21r);
                    EditText text22 = findViewById(R.id.text22r);
                    EditText text23 = findViewById(R.id.text23r);
                    EditText text24 = findViewById(R.id.text24r);
                    EditText text25 = findViewById(R.id.text25r);
                    EditText text26 = findViewById(R.id.text26r);
                    EditText text27 = findViewById(R.id.text27r);
                    EditText text28 = findViewById(R.id.text28r);
                    EditText text29 = findViewById(R.id.text29r);
                    EditText text30 = findViewById(R.id.text30r);
                    EditText text31 = findViewById(R.id.text31r);
                    EditText text32 = findViewById(R.id.text32r);
                    EditText text33 = findViewById(R.id.text33r);
                    EditText text34 = findViewById(R.id.text34r);
                    EditText text35 = findViewById(R.id.text35r);
                    EditText text36 = findViewById(R.id.text36r);
                    EditText text37 = findViewById(R.id.text37r);
                    EditText text38 = findViewById(R.id.text38r);
                    EditText text39 = findViewById(R.id.text39r);
                    EditText text40 = findViewById(R.id.text40r);
                    EditText text41 = findViewById(R.id.text41r);
                    EditText text42 = findViewById(R.id.text42r);
                    list1.add(text1);
                    list1.add(text2);
                    list1.add(text3);
                    list1.add(text4);
                    list1.add(text5);
                    list1.add(text6);
                    list1.add(text7);
                    list1.add(text8);
                    list1.add(text9);
                    list1.add(text10);
                    list1.add(text11);
                    list1.add(text12);
                    list1.add(text13);
                    list1.add(text14);
                    list1.add(text15);
                    list1.add(text16);
                    list1.add(text17);
                    list1.add(text18);
                    list1.add(text19);
                    list1.add(text20);
                    list1.add(text21);
                    list1.add(text22);
                    list1.add(text23);
                    list1.add(text24);
                    list1.add(text25);
                    list1.add(text26);
                    list1.add(text27);
                    list1.add(text28);
                    list1.add(text29);
                    list1.add(text30);
                    list1.add(text31);
                    list1.add(text32);
                    list1.add(text33);
                    list1.add(text34);
                    list1.add(text35);
                    list1.add(text36);
                    list1.add(text37);
                    list1.add(text38);
                    list1.add(text39);
                    list1.add(text40);
                    list1.add(text41);
                    list1.add(text42);


                    list3.add(text1);
                    list3.add(text1);
                    list3.add(text1);
                    list3.add(text1);
                    list3.add(text1);
                    list3.add(text1);
                    list3.add(text1);

                    for(int i =0; i<=41;i++){
                        list1.get(i).setFocusable(false);
                        list1.get(i).setFocusableInTouchMode(false);
                    }
                    startbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vpager.setUserInputEnabled(false);
                            profilebtn.setEnabled(false);
                            switchBtn.setEnabled(false);
                            vpager.requestTransform();
                            equationtocheck = equations.returnEquation();
                            for(int i=0;i<=41;i++) {
                                list1.get(i).setBackgroundResource(R.drawable.border2);
                                list1.get(i).setText("");
                                }
                            startbtn.setVisibility(View.GONE);
                            rowx = 0;
                            startreshavanka(list1,equation,0);
                            }
                    });


                } else if(position==0) {
                    mathbtn.setBackgroundResource(R.drawable.notselected);
                    wordbtn.setBackgroundResource(R.drawable.selected);
                    mykeyboard2 = findViewById(R.id.keyboard2);
                    animationView = findViewById(R.id.animationView);
                    animationView1 = findViewById(R.id.animationView3);
                    LinkedList<EditText> list1 = new LinkedList<EditText>();
                    EditText textword = findViewById(R.id.textword);
                    EditText text1 = findViewById(R.id.text1d);
                    EditText text2 = findViewById(R.id.text2d);
                    EditText text3 = findViewById(R.id.text3d);
                    EditText text4 = findViewById(R.id.text4d);
                    EditText text5 = findViewById(R.id.text5d);
                    EditText text6 = findViewById(R.id.text6d);
                    EditText text7 = findViewById(R.id.text7d);
                    EditText text8 = findViewById(R.id.text8d);
                    EditText text9 = findViewById(R.id.text9d);
                    EditText text10 = findViewById(R.id.text10d);
                    EditText text11= findViewById(R.id.text11d);
                    EditText text12 = findViewById(R.id.text12d);
                    EditText text13 = findViewById(R.id.text13d);
                    EditText text14 = findViewById(R.id.text14d);
                    EditText text15 = findViewById(R.id.text15d);
                    EditText text16 = findViewById(R.id.text16d);
                    EditText text17 = findViewById(R.id.text17d);
                    EditText text18 = findViewById(R.id.text18d);
                    EditText text19 = findViewById(R.id.text19d);
                    EditText text20 = findViewById(R.id.text20d);
                    EditText text21 = findViewById(R.id.text21d);
                    EditText text22 = findViewById(R.id.text22d);
                    EditText text23 = findViewById(R.id.text23d);
                    EditText text24 = findViewById(R.id.text24d);
                    EditText text25 = findViewById(R.id.text25d);
                        list1.add(text1);
                        list1.add(text2);
                        list1.add(text3);
                        list1.add(text4);
                        list1.add(text5);
                    list1.add(text6);
                    list1.add(text7);
                    list1.add(text8);
                    list1.add(text9);
                    list1.add(text10);
                    list1.add(text11);
                    list1.add(text12);
                    list1.add(text13);
                    list1.add(text14);
                    list1.add(text15);
                    list1.add(text16);
                    list1.add(text17);
                    list1.add(text18);
                    list1.add(text19);
                    list1.add(text20);
                    list1.add(text21);
                    list1.add(text22);
                    list1.add(text23);
                    list1.add(text24);
                    list1.add(text25);

                    list2.add(text1);
                    list2.add(text1);
                    list2.add(text1);
                    list2.add(text1);
                    list2.add(text1);
                    for(int i=0;i<=24;i++) {
                        list1.get(i).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                textword.requestFocus();
                            }
                        });
                    }

                    startbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vpager.requestTransform();
                            vpager.setUserInputEnabled(false);
                            profilebtn.setEnabled(false);
                            switchBtn.setEnabled(false);
                            for(int i=0;i<=24;i++) {
                                list1.get(i).setBackgroundResource(R.drawable.border2);
                                list1.get(i).setText("");
                            }
                            wordtocheck = list.get(random);
                            startbtn.setVisibility(View.GONE);
                            rowx = 0;

                            startdumanka(list1,textword,0);
                        }
                    });
                }
            }
        });
    }

    private File fetchfile(){
        storageReference = storage.getReference();
        String str;
        str = "words/"+user.getUid()+".txt";
        pathReference = storageReference.child(str);
        try {
            File localFile = File.createTempFile(user.getUid(), ".txt");
            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    readFile(newfile);
                }
            });
            return localFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readFile(File file)
    {
        String content ="";
        StringBuffer sbuffer = new StringBuffer();
        list = new LinkedList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(file !=null){
            startflag=false;
            try{
                while((content = reader.readLine())!=null){
                    sbuffer.append(content);
                    list.add(content);
                }
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else Toast.makeText(this, "Проверете интернет връзката си или изчакайте момент", Toast.LENGTH_SHORT).show();
       // Random rand = new Random();
       // int upperbound = list.size();
       // random = rand.nextInt(upperbound);
       // return list.get(5);
    }

    public String[] checkword(String s,String textword) {
        char[] input;
        char[] file;
        String[] result = new String[5];
        result[0] = "сива";
        result[1] = "сива";
        result[2] = "сива";
        result[3] = "сива";
        result[4] = "сива";

        input = s.toCharArray();
        file = textword.toCharArray();
        for(int j = 0; j<=4; j++) {
            if (input[j] == file[j]) {
                result[j] = "зелена";
                input[j] = '.';
                file[j] = '.';
            }
        }
        for(int j =0;j<=4;j++){
            if (input[j] != '.') {
                for (int i = 0; i <= 4; i++) {
                    if (input[j] == file[i] ) {
                        result[j] = "жълта";
                        input[j] = ',';
                        file[i] = ',';
                    }
                }
            }
        }
        return result;
    }

    public boolean accessResult(String[] s,LinkedList<EditText> list1){
        for(int i=0;i<=4;i++){
            if(s[i]=="зелена"){
                list1.get(i).setBackgroundResource(R.drawable.bordergreen);
            } else if(s[i]=="жълта"){
                list1.get(i).setBackgroundResource(R.drawable.borderyellow);
            } else list1.get(i).setBackgroundResource(R.drawable.bordergray);
        }
        if(s[0]=="зелена"&&s[1]=="зелена"&&s[2]=="зелена"&&s[3]=="зелена"&&s[4]=="зелена"){
            startbtn.setVisibility(View.VISIBLE);
            mykeyboard2.setVisibility(View.INVISIBLE);
            myRef.child(user.getUid()).child("words").setValue(ServerValue.increment(+1));
            list.remove(5);
            guesswasmade = true;
            vpager.setUserInputEnabled(true);
            animationView.setMinAndMaxProgress(0.0f, 1.0f);
            animationView1.setMinAndMaxProgress(0.0f,1.0f);
            animationView.bringToFront();
            animationView.setSpeed((float)0.3);
            animationView1.bringToFront();
            animationView1.setSpeed((float)0.4);
            animationView.setRepeatCount(0);
            animationView1.setRepeatCount(0);
            animationView.playAnimation();
            animationView1.playAnimation();
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog2w, viewGroup, false);
            Button buttonnewgame=dialogView.findViewById(R.id.buttonnewgame);
            TextView guessedwords = dialogView.findViewById(R.id.guessedwords);
            builder.setView(dialogView);
            myRef.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String test1 ="Отгатнати думи: " + String.valueOf(dataSnapshot.child("words").getValue());
                    guessedwords.setText(test1);
                }
            });
            final AlertDialog alertDialog = builder.create();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    profilebtn.setEnabled(true);
                    switchBtn.setEnabled(true);
                    alertDialog.show();
                }
            }, 1500);
            buttonnewgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animationView.setMinAndMaxProgress(0.0f, 0.0f);
                    animationView1.setMinAndMaxProgress(0.0f,0.0f);
                    startbtn.performClick();
                    alertDialog.dismiss();
                }
            });
            return true;
        } else return false;
    }
    public boolean accessResult1(String[] s,LinkedList<EditText> list1, EditText equation){
        for(int i=0;i<=6;i++){
            if(s[i]=="зелена"){
                list1.get(i).setBackgroundResource(R.drawable.bordergreen);
            } else if(s[i]=="жълта"){
                list1.get(i).setBackgroundResource(R.drawable.borderyellow);
            } else list1.get(i).setBackgroundResource(R.drawable.bordergray);
        }
        if(s[0]=="зелена"&&s[1]=="зелена"&&s[2]=="зелена"&&s[3]=="зелена"&&s[4]=="зелена"&&s[5]=="зелена"&&s[6]=="зелена"){
            myRef.child(user.getUid()).child("equations").setValue(ServerValue.increment(+1));
            vpager.setUserInputEnabled(true);
            mykeyboard.setVisibility(View.INVISIBLE);
            startbtn.setVisibility(View.VISIBLE);
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog1w, viewGroup, false);
            Button buttonnewgame=dialogView.findViewById(R.id.buttonnewgame);
            TextView equationsguessd = dialogView.findViewById(R.id.equationsguessed);
            builder.setView(dialogView);
            myRef.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String test1 ="Отгатнати уравнения: " + String.valueOf(dataSnapshot.child("equations").getValue());
                    equationsguessd.setText(test1);
                }
            });


            final AlertDialog alertDialog = builder.create();
            buttonnewgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animationView.setMinAndMaxProgress(0.0f, 0.0f);
                    animationView1.setMinAndMaxProgress(0.0f,0.0f);
                    startbtn.performClick();
                    alertDialog.dismiss();
                }
            });
            animationView.setMinAndMaxProgress(0.0f, 1.0f);
            animationView1.setMinAndMaxProgress(0.0f,1.0f);
            animationView.bringToFront();
            animationView.setSpeed((float)0.3);
            animationView1.bringToFront();
            animationView1.setSpeed((float)0.4);
            animationView.setRepeatCount(0);
            animationView1.setRepeatCount(0);
            animationView1.playAnimation();
            animationView.playAnimation();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    profilebtn.setEnabled(true);
                    switchBtn.setEnabled(true);
                    alertDialog.show();
                }
            }, 1500);
            return true;
        } else return false;
    }

    public String[] checkequation(String s,String textword) {
        char[] input;
        char[] file;
        String[] result = new String[7];
        result[0] = "сива";
        result[1] = "сива";
        result[2] = "сива";
        result[3] = "сива";
        result[4] = "сива";
        result[5] = "сива";
        result[6] = "сива";


        input = s.toCharArray();
        file = textword.toCharArray();
        for(int j = 0; j<=6; j++) {
            if (input[j] == file[j]) {
                result[j] = "зелена";
                input[j] = '.';
                file[j] = '.';
            }
        }
        for(int j =0;j<=6;j++){
            if (input[j] != '.') {
                for (int i = 0; i <= 6; i++) {
                    if (input[j] == file[i] ) {
                        result[j] = "жълта";
                        input[j] = ',';
                        file[i] = ',';
                    }
                }
            }
        }

        return result;
    }

    public void dialogloss(int i){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog1l, viewGroup, false);
        Button buttonnewgame = dialogView.findViewById(R.id.buttonnewgame);
        TextView header = dialogView.findViewById(R.id.header);
        TextView content1 = dialogView.findViewById(R.id.content1);
        vpager.setUserInputEnabled(true);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        switch (i){
            case 1: header.setText("О, не!");content1.setText("Не успяхте да отгатнете уравнението!");
                mykeyboard.setVisibility(View.INVISIBLE); break;
            case 2: header.setText("О, не!");content1.setText("Не успяхте да отгатнете думата!");
                mykeyboard2.setVisibility(View.INVISIBLE); break;
        }
        buttonnewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbtn.performClick();
                alertDialog.dismiss();
            }
        });
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
                profilebtn.setEnabled(true);
                switchBtn.setEnabled(true);
                startbtn.setVisibility(View.VISIBLE);
            }
        }, 500);
    }



    public void startreshavanka(LinkedList<EditText> list1,EditText equation, int row){
        int j = row*7;
        for(int i=0;i<=6;i++){
            list3.set(i,list1.get(i+j));
        }
        if(row==0) {
            mykeyboard.setVisibility(View.VISIBLE);
        }
        InputConnection ic = equation.onCreateInputConnection(new EditorInfo());
        mykeyboard.setInputConnection(ic);
        mykeyboard.setActivated(true);
        equation.setText("");
        equation.setBackgroundColor(Color.TRANSPARENT);
        equation.setCursorVisible(false);
        equation.setTextColor(Color.TRANSPARENT);
        equation.setImeOptions(EditorInfo.IME_ACTION_GO);
        equation.setImeActionLabel("actionGO",EditorInfo.IME_ACTION_GO);
        equation.requestFocus();
        equation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String s = equation.getText().toString();
                    if(s.length()==7) {
                        rowx++;
                        String[] result1;
                        result1 = checkequation(s, equationtocheck);
                        if(rowx!=6) {
                            if (!accessResult1(result1, list3, equation)) {
                                startreshavanka(list1, equation, rowx);
                            }
                        }
                        if(rowx==6&&!accessResult1(result1, list3, equation)){
                            dialogloss(1);
                        }

                        return true;
                    }  else {
                        Toast.makeText(MainActivity.this, "Моля, попълнете всички кутийки!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
        equation.addTextChangedListener(new TextWatcher() {
            char[] text ={' ',' ',' ',' ',' ',' ',' '};
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                equation.requestFocus();
                for(int i=0;i<=6;i++){
                    list3.get(i).setFocusable(true);
                    list3.get(i).requestFocus();
                    list3.get(i).setText("");
                    list3.get(i).clearFocus();
                    list3.get(i).setFocusable(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                equation.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                text = equation.getText().toString().toCharArray();
                int i;
                for(i=0;i<text.length;i++){
                    list3.get(i).setText(String.valueOf(text[i]));
                }
                equation.requestFocus();
            }
        });

    }

     public void startdumanka(LinkedList<EditText> list1,EditText textword, int row){
         guesswasmade = false;
         if(row==0) {
             mykeyboard2.setVisibility(View.VISIBLE);
         }
        int j = row*5;
        for(int i=0;i<=4;i++){
            list2.set(i,list1.get(i+j));
        }
         InputConnection ic = textword.onCreateInputConnection(new EditorInfo());
         mykeyboard2.setInputConnection(ic);
         mykeyboard2.setActivated(true);
        textword.setText("");
        textword.setCursorVisible(false);
        textword.setTextColor(Color.TRANSPARENT);
        textword.requestFocus();
        textword.setImeOptions(EditorInfo.IME_ACTION_GO);
        textword.setImeActionLabel("actionGO",EditorInfo.IME_ACTION_GO);
        textword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.v("first_fragment1", actionId + " " );
                if (actionId == EditorInfo.IME_ACTION_GO|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String s = textword.getText().toString();
                    if (s.length() == 5) {
                        rowx++;
                        String[] result;
                        result = checkword(s, wordtocheck);
                        if (rowx != 5) {
                          //  accessResult(result, list2);
                            if (!accessResult(result, list2)) {
                                startdumanka(list1, textword, rowx);
                            }
                        }
                        if (rowx == 5 && !accessResult(result, list2)) {
                            textword.clearFocus();
                            dialogloss(2);
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Моля, попълнете всички кутийки!", Toast.LENGTH_SHORT).show();}
                }
                return true;
            }
        });

            textword.addTextChangedListener(new TextWatcher() {
                char[] text ={' ',' ',' ',' ',' '};
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   textword.requestFocus();
                    for(int i=0;i<=4;i++){
                        list2.get(i).requestFocus();
                        list2.get(i).setText("");
                        list2.get(i).clearFocus();
                    }
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    textword.requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    text = textword.getText().toString().toCharArray();
                    int i;
                    for(i=0;i<text.length;i++){
                       list2.get(i).setText(String.valueOf(text[i]));
                    }
                    textword.requestFocus();
                }
            });

        }

    @Override
    public void recreate(){
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    @Override
    protected void onStop() {
        super.onStop();
        if (guesswasmade) {
            try {
                //Toast.makeText(MainActivity.this, "TEST1", Toast.LENGTH_LONG).show();
                guesswasmade = false;
                Log.v("readingfile", "Onstop");
                File uploadfile = File.createTempFile("test", "txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(uploadfile));
                for (int i = 0; i < list.size(); i++) {
                    writer.write(list.get(i) + "\n");
                }
                writer.close();
                Log.v("readingfile", "File procheten");
                storageReference = storage.getReference();
                String str;
                str = "words/" + user.getUid() + ".txt";
                pathReference = storageReference.child(str);
                try {
                    //Toast.makeText(MainActivity.this, "TEST2", Toast.LENGTH_LONG).show();
                    InputStream inputStream = new FileInputStream(uploadfile);
                    UploadTask task = pathReference.putStream(inputStream);
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Log.v("readingfile", "File zapisan");
                        }
                    });
                } finally {

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
