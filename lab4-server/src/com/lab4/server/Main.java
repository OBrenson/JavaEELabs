package com.lab4.server;

import com.lab1.VehicleUtils;
import com.lab1.vehicles.Vehicle;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        while (true) {
            try {
                start(6666);
            } catch (IOException e) {
                System.out.println("connection was rejected");
            }
        }
    }

    public static void start(int port) throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            Vehicle vehicle = (Vehicle) ois.readObject();
            VehicleUtils.printModelsNamesAndPrices(vehicle);
            System.out.println("\n\n");
            double avr = VehicleUtils.getAverage(vehicle);
            dos.writeDouble(avr);
        }
    }
}
