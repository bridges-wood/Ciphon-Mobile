package com.example.cipherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Logic governing the main view of the application.
 */
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.cipherapp.MESSAGE";

    /**
     * Loads the saved state of the application, and loads the main layout.
     *
     * @param savedInstanceState Saved state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Logic called when the submit button is pressed.
     *
     * @param view The view containing the submit button.
     */
    public void submit(View view) {
        EditText editText = findViewById(R.id.cipherText);
        String message = editText.getText().toString();
        Client client = new Client(message);
        Intent displayDecoded = new Intent(this, DisplayMessageActivity.class);
        displayDecoded.putExtra(EXTRA_MESSAGE, client.getDecodedText());
        startActivity(displayDecoded);
    }

}
