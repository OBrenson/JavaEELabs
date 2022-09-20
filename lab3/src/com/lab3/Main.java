package com.lab3;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;
import com.lab3.threads.*;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws DuplicateModelNameException, InterruptedException {

        Vehicle vehicle = generateVehicle("first", 20000, true);
//        Thread threadNames = new NamesPrinterThread(vehicle);
//        Thread threadPrices = new PricesPrinterThread(vehicle);
//        threadNames.setPriority(Thread.NORM_PRIORITY);
//        threadPrices.setPriority(Thread.MAX_PRIORITY);
//        threadNames.start();
//        threadPrices.start();
//        threadNames.join();

        System.out.println("------synchronized-------");

        TransportSynchronizer ts = new TransportSynchronizer(vehicle);
        NamesSyncPrinter syncNames = new NamesSyncPrinter(vehicle, ts);
        PricesSyncPrinter syncPrices = new PricesSyncPrinter(vehicle, ts);

        Thread s1 = new Thread(syncNames);
        Thread s2 = new Thread(syncPrices);

//        s1.setPriority(Thread.MAX_PRIORITY);
//        s2.setPriority(Thread.MIN_PRIORITY);
        s1.start();
        s2.start();
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
