package com.lab4.multiserver;

import com.lab1.VehicleUtils;
import com.lab1.vehicles.Vehicle;

import java.io.*;
import java.net.Socket;

public class VehicleExecutor implements Runnable {
    private final OutputStream outputStream;
    private final InputStream inputStream;

    public VehicleExecutor(Socket socket) throws IOException {
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            DataOutputStream dos = new DataOutputStream(outputStream);
            Vehicle vehicle = (Vehicle) ois.readObject();
            VehicleUtils.printModelsNamesAndPrices(vehicle);
            System.out.println("\n\n");
            double avr = VehicleUtils.getAverage(vehicle);
            dos.writeDouble(avr);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
