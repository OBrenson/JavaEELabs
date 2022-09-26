package com.lab4.multiserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

    }

    public static void start(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
        ) {
            new Thread(new VehicleExecutor(socket.getOutputStream(), socket.getInputStream()));
        }
    }
}
