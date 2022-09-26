package com.lab3.threads.lock;

import com.lab1.vehicles.Vehicle;

import java.util.concurrent.locks.ReentrantLock;

public class LockNamesPrinter extends LockPrinter {

    public LockNamesPrinter(Vehicle vehicle, ReentrantLock locker) {
        super(vehicle, locker);
    }

    @Override
    public void run() {
        locker.lock();
        try {
            for (String name : vehicle.getModelsNames()) {
                System.out.println(name);
            }
        } finally {
            locker.unlock();
        }
    }
}
