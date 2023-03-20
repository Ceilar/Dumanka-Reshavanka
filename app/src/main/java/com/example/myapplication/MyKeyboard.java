package com.example.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

import java.security.Key;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    Button button1,
    button2, button3, button4,
            button5, button6, button7, button8,
            button9, button0, buttonDelete, buttonEnter, buttonPlus, buttonMinus, buttonMulti, buttonDivide, buttonEquals;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button_7);
        button7.setOnClickListener(this);
        button8 = (Button) findViewById(R.id.button_8);
        button8.setOnClickListener(this);
        button9 = (Button) findViewById(R.id.button_9);
        button9.setOnClickListener(this);
        button0 = (Button) findViewById(R.id.button_0);
        button0.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);
        buttonEnter = (Button) findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(this);

        buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);
        buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);
        buttonDivide = (Button) findViewById(R.id.button_divide);
        buttonDivide.setOnClickListener(this);
        buttonMulti = (Button) findViewById(R.id.button_multi);
        buttonMulti.setOnClickListener(this);
        buttonEquals = (Button) findViewById(R.id.button_equals);
        buttonEquals.setOnClickListener(this);

        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_2, "2");
        keyValues.put(R.id.button_3, "3");
        keyValues.put(R.id.button_4, "4");
        keyValues.put(R.id.button_5, "5");
        keyValues.put(R.id.button_6, "6");
        keyValues.put(R.id.button_7, "7");
        keyValues.put(R.id.button_8, "8");
        keyValues.put(R.id.button_9, "9");
        keyValues.put(R.id.button_0, "0");
        keyValues.put(R.id.button_enter, "\n");
        keyValues.put(R.id.button_plus, "+");
        keyValues.put(R.id.button_minus, "-");
        keyValues.put(R.id.button_divide, "/");
        keyValues.put(R.id.button_multi, "*");
        keyValues.put(R.id.button_equals, "=");

    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null)
            return;

        if (view.getId() == R.id.button_delete) {
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL));

        } else {
            String value = keyValues.get(view.getId());
            switch(value){
                case "\n": inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));break;
                case "0":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));break;
                case "1":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));break;
                case "2":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2));break;
                case "3":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_3));break;
                case "4":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_4));break;
                case "5":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_5));break;
                case "6":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_6));break;
                case "7":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_7));break;
                case "8":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_8));break;
                case "9":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_9));break;
                case "+":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_PLUS));break;
                case "-":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MINUS));break;
                case "*":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_STAR));break;
                case "/":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SLASH));break;
                case "=":inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_EQUALS));break;

            }
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}