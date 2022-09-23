package com.lab4.client;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, DuplicateModelNameException {
        Vehicle vehicle = generateVehicle("car", 10, true);
        send(6666, vehicle);
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
