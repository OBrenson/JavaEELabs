package com.lab3.threads;

import com.lab1.vehicles.Vehicle;

public class NamesPrinterThread extends PrinterThread{

    public NamesPrinterThread(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void run() {
       for (String name : vehicle.getModelsNames()) {
           System.out.println(name);
       }
    }
}
