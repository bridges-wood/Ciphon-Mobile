package com.example.cipherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submit(View view){
        EditText editText = findViewById(R.id.cipherText);
        String message = editText.getText().toString();
        Client client = new Client(message);
        client.startClient();
        System.out.println("decoded = " + client.decodedText);
    }

}
