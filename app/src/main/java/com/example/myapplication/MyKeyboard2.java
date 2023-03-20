package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyKeyboard2 extends LinearLayout implements View.OnClickListener {
    Button button1,
            button2, button3, button4,
            button5, button6, button7, button8,
            button9, button10, button11, button12, button13, button14, button15, button16
            , button17, button18, button19, button20, button21, button22, button23
            , button24, button25, button26, button27, button28, button29, button30
            , button31, button32,
            buttonDelete, buttonEnter;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public MyKeyboard2(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.keyboard2, this, true);
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
        button10 = (Button) findViewById(R.id.button_10);
        button10.setOnClickListener(this);
        button11 = (Button) findViewById(R.id.button_11);
        button11.setOnClickListener(this);
        button12 = (Button) findViewById(R.id.button_12);
        button12.setOnClickListener(this);
        button13 = (Button) findViewById(R.id.button_13);
        button13.setOnClickListener(this);
        button14 = (Button) findViewById(R.id.button_14);
        button14.setOnClickListener(this);
        button15 = (Button) findViewById(R.id.button_15);
        button15.setOnClickListener(this);
        button16 = (Button) findViewById(R.id.button_16);
        button16.setOnClickListener(this);
        button17 = (Button) findViewById(R.id.button_17);
        button17.setOnClickListener(this);
        button18 = (Button) findViewById(R.id.button_18);
        button18.setOnClickListener(this);
        button19 = (Button) findViewById(R.id.button_19);
        button19.setOnClickListener(this);
        button20 = (Button) findViewById(R.id.button_20);
        button20.setOnClickListener(this);
        button21 = (Button) findViewById(R.id.button_21);
        button21.setOnClickListener(this);
        button22 = (Button) findViewById(R.id.button_22);
        button22.setOnClickListener(this);
        button24 = (Button) findViewById(R.id.button_24);
        button24.setOnClickListener(this);
        button25 = (Button) findViewById(R.id.button_25);
        button25.setOnClickListener(this);
        button26 = (Button) findViewById(R.id.button_26);
        button26.setOnClickListener(this);
        button27 = (Button) findViewById(R.id.button_27);
        button27.setOnClickListener(this);
        button28 = (Button) findViewById(R.id.button_28);
        button28.setOnClickListener(this);
        button29 = (Button) findViewById(R.id.button_29);
        button29.setOnClickListener(this);
        button30 = (Button) findViewById(R.id.button_30);
        button30.setOnClickListener(this);
        button31 = (Button) findViewById(R.id.button_31);
        button31.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);
        buttonEnter = (Button) findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(this);



        keyValues.put(R.id.button_1, "я");
        keyValues.put(R.id.button_2, "в");
        keyValues.put(R.id.button_3, "е");
        keyValues.put(R.id.button_4, "р");
        keyValues.put(R.id.button_5, "т");
        keyValues.put(R.id.button_6, "ъ");
        keyValues.put(R.id.button_7, "у");
        keyValues.put(R.id.button_8, "и");
        keyValues.put(R.id.button_9, "о");
        keyValues.put(R.id.button_10, "п");
        keyValues.put(R.id.button_11, "ч");
        keyValues.put(R.id.button_12, "а");
        keyValues.put(R.id.button_13, "с");
        keyValues.put(R.id.button_14, "д");
        keyValues.put(R.id.button_15, "ф");
        keyValues.put(R.id.button_16, "г");
        keyValues.put(R.id.button_17, "х");
        keyValues.put(R.id.button_18, "й");
        keyValues.put(R.id.button_19, "к");
        keyValues.put(R.id.button_20, "л");
        keyValues.put(R.id.button_21, "ш");
        keyValues.put(R.id.button_22, "щ");
        keyValues.put(R.id.button_24, "з");
        keyValues.put(R.id.button_25, "ь");
        keyValues.put(R.id.button_26, "ц");
        keyValues.put(R.id.button_27, "ж");
        keyValues.put(R.id.button_28, "б");
        keyValues.put(R.id.button_29, "н");
        keyValues.put(R.id.button_30, "м");
        keyValues.put(R.id.button_31, "ю");
        keyValues.put(R.id.button_enter, "\n");
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
                case "я" : inputConnection.commitText("я",1);break;
                case "в" : inputConnection.commitText("в",1);break;
                case "е" : inputConnection.commitText("е",1);break;
                case "р" : inputConnection.commitText("р",1);break;
                case "т" : inputConnection.commitText("т",1);break;
                case "ъ" : inputConnection.commitText("ъ",1);break;
                case "у" : inputConnection.commitText("у",1);break;
                case "и" : inputConnection.commitText("и",1);break;
                case "о" : inputConnection.commitText("о",1);break;
                case "п" : inputConnection.commitText("п",1);break;
                case "ч" : inputConnection.commitText("ч",1);break;
                case "а" : inputConnection.commitText("а",1);break;
                case "с" : inputConnection.commitText("с",1);break;
                case "д" : inputConnection.commitText("д",1);break;
                case "ф" : inputConnection.commitText("ф",1);break;
                case "г" : inputConnection.commitText("г",1);break;
                case "х" : inputConnection.commitText("ь",1);break;
                case "й" : inputConnection.commitText("й",1);break;
                case "к" : inputConnection.commitText("к",1);break;
                case "л" : inputConnection.commitText("л",1);break;
                case "ш" : inputConnection.commitText("ш",1);break;
                case "щ" : inputConnection.commitText("щ",1);break;
                case "з" : inputConnection.commitText("з",1);break;
                case "ь" : inputConnection.commitText("ь",1);break;
                case "ц" : inputConnection.commitText("ц",1);break;
                case "ж" : inputConnection.commitText("ж",1);break;
                case "б" : inputConnection.commitText("б",1);break;
                case "н" : inputConnection.commitText("н",1);break;
                case "м" : inputConnection.commitText("м",1);break;
                case "ю" : inputConnection.commitText("ю",1);break;
                default : break;
            }
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}

