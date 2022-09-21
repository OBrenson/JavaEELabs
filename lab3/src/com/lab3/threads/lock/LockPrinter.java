package com.lab3.threads.lock;

import com.lab1.vehicles.Vehicle;

import java.util.concurrent.locks.ReentrantLock;

public abstract class LockPrinter implements Runnable{

    protected final Vehicle vehicle;
    protected final ReentrantLock locker;

    public LockPrinter(Vehicle vehicle, ReentrantLock locker) {
        this.vehicle = vehicle;
        this.locker = locker;
    }
}
