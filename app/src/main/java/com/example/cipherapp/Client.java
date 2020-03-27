package com.example.cipherapp;

import android.os.HandlerThread;

import java.util.concurrent.CountDownLatch;

/**
 * Class that handles the interface with the web-server running my API.
 */
public class Client {

    private String decodedText;

    public Client(String cipherText) {
        startClient(cipherText);
    }

    /**
     * Method that starts concurrent networking activity to access API.
     *
     * @param cipherText Contains the text entered by the user to be passed to the API for decryption.
     */
    private void startClient(final String cipherText) {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread clientThread = new HandlerThread("clientThread") {

            @Override
            public void run() {
                JSONParser jsonParser = new JSONParser();
                String result = JSONParser.getJSON("http://polytonic.me/cipher", cipherText);
                setDecodedText(result);
                latch.countDown(); // Activates countdown to notify the latch that the task has been completed successfully.
            }
        };

        clientThread.start();
        try {
            latch.await(); // Waits until the task is completed.
        } catch (InterruptedException e) {
            System.out.println("Failure on client.");
        }
    }

    public String getDecodedText() {
        return decodedText;
    }

    public void setDecodedText(String decodedText) {
        this.decodedText = decodedText;
    }
}

