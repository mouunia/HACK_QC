package com.example.khalilbennani.gestiondesarbres.FakeServer;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class FakeClient {

    private static Socket socket = null;

    public static void send(JSONObject message) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(message.toString());
        writer.flush();
    }

    public static void send(String message) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(message);
        writer.flush();
    }

    public static void main(String[] args) {
        try {
            //socket = new Socket("10.240.201.120", 5000);
            socket = new Socket("10.240.201.45", 2009);
            //socket = new Socket("https://4e515ed8.ngrok.io");

            send("Hello from Java");
            send("Et un deuxieme");
            System.out.print("Message sent.");

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
