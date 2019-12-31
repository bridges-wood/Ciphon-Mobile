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
    private String decodedText;

	public Client(String cipherText) {
		this.cipherText = cipherText;
	}


	public void startClient() {
		final CountDownLatch latch = new CountDownLatch(1);
		Thread clientThread = new HandlerThread("clientThread"){

			@Override
			public void run() {
				// We need a host and port, we want to connect to the ServerSocket at port 8000
				Socket socket = null;
				initServer();
				try {
					socket = new Socket(server, 4848); // Note, don't post out through the in.
					String returned = "";
					if (!socket.equals(null)) {
						System.out.println("Client is connected!");
						send(cipherText, socket);
						returned = receive(socket);
						System.out.println("Received '" + returned + "' from the server.");
					}
					decodedText = returned;
				} catch (IOException e) {
					System.out.println("Could not connect to the socket.");
				} finally {
					try {
						socket.getOutputStream().flush();
						socket.getInputStream().close();
						System.out.println("Closing socket and terminating client.");
						socket.close();
						latch.countDown();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}


			public String receive(Socket socket) {
				try {
					DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					String dataString = in.readUTF();
					return dataString;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			public void send(String dataString, Socket socket) {
				OutputStream outputStream = null;
				try {
					outputStream = socket.getOutputStream();
					DataOutputStream out = new DataOutputStream(outputStream);
					System.out.println("Sending messages to the client");
					out.writeUTF(dataString);
					System.out.println("Closing socket and terminating program.");
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			public void initServer() {
				try {
					server = InetAddress.getByName("185.130.159.240");
					socket = new Socket(server, 4848);
				} catch (IOException e) {
					e.printStackTrace();
				}
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

