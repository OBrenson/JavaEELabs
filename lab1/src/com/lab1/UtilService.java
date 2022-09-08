package com.lab1;

import com.lab1.vehicles.Vehicle;

import java.util.Arrays;
import java.util.stream.IntStream;

public class UtilService {

    public static double getAverage(Vehicle vehicle) {
        return Arrays.stream(vehicle.getModelsPrices()).reduce(0.0, Double::sum)/(double)vehicle.getModelsNum();
    }

    public static void printModelsNamesAndPrices(Vehicle vehicle) {
        Double[] prices = vehicle.getModelsPrices();
        String[] names = vehicle.getModelsNames();
        IntStream.range(0, names.length).forEachOrdered(i ->
                System.out.printf("Name: %s, Price: %f%n", names[i], prices[i]));
    }
}
