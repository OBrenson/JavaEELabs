package com.lab4.client;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, DuplicateModelNameException {
        Vehicle vehicle = generateVehicle("car", 10, true);
        //send(6666, vehicle);
        mulSend(vehicle);
    }

    public static void mulSend(Vehicle vehicle) {
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            es.execute(() -> {
                try {
                    send(6666, vehicle);
                    System.out.println(finalI);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
    }

    public static void send(int port, Vehicle vehicle) throws IOException, ClassNotFoundException {
        try (
            Socket socket = new Socket("127.0.0.1", port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream())
        ) {
            oos.writeObject(vehicle);
            double res = dis.readDouble();
            System.out.println("Average is " + res);
        }
    }

    public static Vehicle generateVehicle(String prefix, int num, boolean isCar) throws DuplicateModelNameException {
        Vehicle vehicle;
        if (isCar) {
            vehicle = new Car(prefix, num);
        } else {
            vehicle = new MotorbikeHandler.Motorbike(prefix, num);
        }
        for (int i = 0; i < num; i++) {
            vehicle.addModel(String.format("%s_model_%d", prefix, i), i*100 + 1);
        }
        return vehicle;
    }
}
