package com.egebulut.mathelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText answer;
    TextView sonucText;
    Button calculateBtn;
    EditText operationRaw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer = findViewById(R.id.sonuc);
        sonucText = findViewById(R.id.sonucText);
        calculateBtn = findViewById(R.id.calculateBtn);
        operationRaw = findViewById(R.id.operationInput);

        sonucText.setText("Sonuç :");

        operationRaw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(operationRaw.getText().length()>0){
                    calculateBtn.setVisibility(View.VISIBLE);
                }else{
                    calculateBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonucText.setVisibility(View.VISIBLE);
                operationParcer();
            }
        });

    }

    private void operationParcer() {

        String[] raw = operationRaw.getText().toString().split("");
        ArrayList<String> islemler = new ArrayList<>();
        ArrayList<String> cevaplar = new ArrayList<>();

        //Check parenthesis
        ArrayList<Integer> openParenthesisIndex = new ArrayList<>();
        ArrayList<Integer> closeParenthesisIndex = new ArrayList<>();
        int counter = 0;
        for (String c : raw) {
            if (c.equals("(")) {
                openParenthesisIndex.add(counter);
            }
            if (c.equals(")")) {
                closeParenthesisIndex.add(counter);
            }
            counter++;
        }
        if (closeParenthesisIndex.size() != openParenthesisIndex.size()) {
            sonucText.setText("Parantez açma ve Parantez kapama sembollerinin sayısı eşit değil.");
        onCreate(null);
        } else {
            sonucText.setText("Sonuç :");
        }

        int parantezCounter = 0;
        for(int p : openParenthesisIndex){
            String tempIslem = "";
            for(int i = p+1; i<closeParenthesisIndex.get(parantezCounter); i++){
                tempIslem += raw[i];
            }
            islemler.add(tempIslem);
            parantezCounter++;
        }

        if(islemler.size()>0){
            for(String i : islemler){
                cevaplar.add(solve(i));
            }
        }

        int indexCounter = 0;
        String sadelesmisIslem = "";
        for(int i = 0; i<raw.length; i++){
            if(openParenthesisIndex.contains(i)){
                int tempIndex = openParenthesisIndex.indexOf(i);

                sadelesmisIslem += cevaplar.get(tempIndex);

                i = closeParenthesisIndex.get(tempIndex)+1;
            }else{
                sadelesmisIslem += raw[i];
            }
        }

        String cevap = solve(sadelesmisIslem);

        String outputTest = operationRaw.getText() + "\n" +
                "parantez işlemleri :\n";

        int count = 1;
        for(String s : islemler){
            outputTest += count+". "+s+"\n";
        count++;
        }

        outputTest += "\n\n+Cevap : \n"+cevap;

        answer.setText(outputTest);
        answer.setVisibility(View.VISIBLE);

    }

    private String solve(String islemStr){

        String[] islemArr = islemStr.split(" ");

        int sonuc = Integer.parseInt(islemArr[0]);

        int a = -1, b = -1;
        for(int i = 1; i<islemArr.length; i++){
            try{
            a = Integer.parseInt(islemArr[i-1]);
            b = Integer.parseInt(islemArr[i+1]);
            }catch (NumberFormatException ignored){

            }

            switch (islemArr[i]){

                case "+":
                sonuc += b;
                    break;
                case "-":
                sonuc -= b;
                    break;
                case "*": case "x":
                    sonuc *= b;
                    break;
                case "/":
                sonuc /= b;
                    break;

            }
        }
        String sonucStr = sonuc+"";
        return sonucStr;
    }

}