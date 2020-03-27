package com.example.cipherapp;
import android.os.AsyncTask;
import android.os.HandlerThread;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private InetAddress server;
    private Socket socket;
    private String cipherText;
	public String decodedText;

	public Client(String cipherText) {
		this.cipherText = cipherText;
	}


	public void startClient() {
		final CountDownLatch latch = new CountDownLatch(1);
		Thread clientThread = new HandlerThread("clientThread"){

			@Override
			public void run() {
				JSONParser jsonParser = new JSONParser();
				jsonParser.getJSON("http://polytonic.me/cipher?", cipherText);
				decodedText = jsonParser.result;
				latch.countDown();
			}
		};
		clientThread.start();
		try {
			latch.await();
		} catch (InterruptedException e) {
			System.out.println("Failure on client.");
		}
	}
}

