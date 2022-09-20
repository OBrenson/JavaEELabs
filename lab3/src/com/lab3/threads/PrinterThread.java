package com.lab3.threads;

import com.lab1.vehicles.Vehicle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class PrinterThread extends Thread {

    protected final Vehicle vehicle;

    public PrinterThread (Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        throw new NotImplementedException();
    }
}
