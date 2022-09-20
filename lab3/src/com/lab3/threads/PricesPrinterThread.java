package com.lab3.threads;

import com.lab1.vehicles.Vehicle;

public class PricesPrinterThread extends PrinterThread{

    public PricesPrinterThread(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void run() {
       for (Double p : vehicle.getModelsPrices()) {
           System.out.println(p);
       }
    }
}
