package com.lab3.threads.sync;

import com.lab1.vehicles.Vehicle;

public abstract class SyncPrinter implements Runnable{

    protected TransportSynchronizer synchronizer;

    protected final Vehicle vehicle;

    public SyncPrinter(Vehicle vehicle, TransportSynchronizer synchronizer) {
        this.vehicle = vehicle;
        this.synchronizer = synchronizer;
    }
}
