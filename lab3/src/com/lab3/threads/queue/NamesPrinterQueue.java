package com.lab3.threads.queue;

import com.lab1.vehicles.Car;
import com.lab1.vehicles.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class NamesPrinterQueue implements Runnable {

    private final BlockingQueue<Vehicle> queue;
    private final String path;

    public NamesPrinterQueue(BlockingQueue<Vehicle> queue, String path) {
        this.queue = queue;
        this.path = path;
    }

    @Override
    public void run() {
        try (
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
        ){
            String brand = br.readLine();
            queue.add(new Car(brand, 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
