package com.lab4.multiserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        start(6666);
    }

    public static void start(int port) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(port);
        ) {
            while(true) {
                executorService.execute(new VehicleExecutor(serverSocket.accept()));
            }
        }
    }
}
