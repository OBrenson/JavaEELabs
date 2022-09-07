package com.lab1;

import java.util.Arrays;

public class UtilService {

    public static double getAverage(Vehicle vehicle) {
        return Arrays.stream(vehicle.getModelsPrices()).reduce(0.0, Double::sum)/(double)vehicle.getModelsNum();
    }


}
