package com.lab3.threads.executors;

import com.lab1.vehicles.Vehicle;

public class NamesExecsPrinter implements Runnable {

    private final Vehicle vehicle;

    public NamesExecsPrinter(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        for (String name : vehicle.getModelsNames()) {
            System.out.println(name);
        }
    }
}
