package com.example.cipherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.cipherapp.MESSAGE";
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
        Intent displayDecoded = new Intent(this, DisplayMessageActivity.class);
        displayDecoded.putExtra(EXTRA_MESSAGE, client.decodedText);
        startActivity(displayDecoded);
    }

}
