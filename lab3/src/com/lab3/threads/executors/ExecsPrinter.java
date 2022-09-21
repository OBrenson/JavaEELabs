package com.lab3.threads.executors;

import com.lab1.vehicles.Vehicle;

public abstract class ExecsPrinter implements Runnable {

    protected Vehicle vehicle;

    public ExecsPrinter(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
