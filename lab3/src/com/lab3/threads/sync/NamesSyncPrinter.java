package com.lab3.threads.sync;

import com.lab1.vehicles.Vehicle;

public class NamesSyncPrinter extends SyncPrinter {

    public NamesSyncPrinter(Vehicle vehicle, TransportSynchronizer synchronizer) {
        super(vehicle, synchronizer);
    }

    @Override
    public void run() {
        for (int i = 0; i < vehicle.getModelsNum(); i++) {
            try {
                synchronizer.printModel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
