package com.lab3;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;
import com.lab3.threads.NamesPrinterThread;
import com.lab3.threads.PricesPrinterThread;
import com.lab3.threads.executors.NamesExecsPrinter;
import com.lab3.threads.lock.LockNamesPrinter;
import com.lab3.threads.lock.LockPricesPrinter;
import com.lab3.threads.queue.NamesPrinterQueue;
import com.lab3.threads.sync.NamesSyncPrinter;
import com.lab3.threads.sync.PricesSyncPrinter;
import com.lab3.threads.sync.TransportSynchronizer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {

    private static final String PATH_PREFIX = "C:\\Users\\PC\\Desktop\\labs\\JavaEELabs\\lab3\\resources\\";

    public static void main(String[] args) throws DuplicateModelNameException, InterruptedException {

        Vehicle vehicle = generateVehicle("first", 1000, true);
//        Thread threadNames = new NamesPrinterThread(vehicle);
//        Thread threadPrices = new PricesPrinterThread(vehicle);
//        threadNames.setPriority(Thread.NORM_PRIORITY);
//        threadPrices.setPriority(Thread.MAX_PRIORITY);
//        threadNames.start();
//        threadPrices.start();
//        threadNames.join();

//        System.out.println("------synchronized-------");
//
//        TransportSynchronizer ts = new TransportSynchronizer(vehicle);
//        NamesSyncPrinter syncNames = new NamesSyncPrinter(vehicle, ts);
//        PricesSyncPrinter syncPrices = new PricesSyncPrinter(vehicle, ts);
//
//        Thread s1 = new Thread(syncNames);
//        Thread s2 = new Thread(syncPrices);
//
//        s1.start();
//        s2.start();
//
//        System.out.println("------locker-------");
//        ReentrantLock rl = new ReentrantLock();
//        LockNamesPrinter lnp = new LockNamesPrinter(vehicle, rl);
//        LockPricesPrinter lpp = new LockPricesPrinter(vehicle, rl);
//
//        Thread ss1 = new Thread(lnp);
//        Thread ss2 = new Thread(lpp);
//
//        ss1.start();
//        ss2.start();

//        System.out.println("------executors-------");
//        Vehicle exec1 = generateVehicle("exec1", 100, true);
//        Vehicle exec2 = generateVehicle("exec2", 1000, true);
//        Vehicle exec3 = generateVehicle("exec3", 200, true);
//        Vehicle exec4 = generateVehicle("exec4", 300, true);
//
//        NamesExecsPrinter execsPrinter1 = new NamesExecsPrinter(exec1);
//        NamesExecsPrinter execsPrinter2 = new NamesExecsPrinter(exec2);
//        NamesExecsPrinter execsPrinter3 = new NamesExecsPrinter(exec3);
//        NamesExecsPrinter execsPrinter4 = new NamesExecsPrinter(exec4);
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.execute(execsPrinter1);
//        executor.execute(execsPrinter2);
//        executor.execute(execsPrinter3);
//        executor.execute(execsPrinter4);
//        executor.shutdown();

        System.out.println("------queue-------");
        String[] paths = new String[5];
        IntStream.range(0,5).forEachOrdered(i -> paths[i] = String.format("%scar%d.txt", PATH_PREFIX, i + 1));
        ArrayBlockingQueue<Vehicle> queue = new ArrayBlockingQueue<>(1);
        for (String path : paths) {
            new Thread(new NamesPrinterQueue(queue, path)).start();
        }
        int ind = 0;
        while (ind < 5) {
            System.out.println(queue.take().getBrand());
            ind++;
        }
    }

    public static Vehicle generateVehicle(String prefix, int num, boolean isCar) throws DuplicateModelNameException {
        Vehicle vehicle;
        if (isCar) {
            vehicle = new Car(prefix, num);
        } else {
            vehicle = new MotorbikeHandler.Motorbike(prefix, num);
        }
        for (int i = 0; i < num; i++) {
            vehicle.addModel(String.format("%s_model_%d", prefix, i), i*100 + 1);
        }
        return vehicle;
    }
}
