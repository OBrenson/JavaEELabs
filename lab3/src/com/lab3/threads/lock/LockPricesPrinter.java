package com.lab3.threads.lock;

import com.lab1.vehicles.Vehicle;

import java.util.concurrent.locks.ReentrantLock;

public class LockPricesPrinter extends LockPrinter {

    public LockPricesPrinter(Vehicle vehicle, ReentrantLock locker) {
        super(vehicle, locker);
    }

    @Override
    public void run() {
        locker.lock();
        try {
            for (Double price : vehicle.getModelsPrices()) {
                System.out.println(price);
            }
        } finally {
            locker.unlock();
        }
    }
}
