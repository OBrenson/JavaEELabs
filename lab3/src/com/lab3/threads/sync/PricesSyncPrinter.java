package com.lab3.threads.sync;

import com.lab1.vehicles.Vehicle;

public class PricesSyncPrinter extends SyncPrinter {

    public PricesSyncPrinter(Vehicle vehicle, TransportSynchronizer synchronizer) {
        super(vehicle, synchronizer);
    }

    @Override
    public void run() {
        for (int i = 0; i < vehicle.getModelsNum(); i++) {
            try {
                synchronizer.printPrice();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
