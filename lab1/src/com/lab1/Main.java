package com.lab1;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.ModelPriceOutOfBoundsException;
import com.lab1.exceptions.NoSuchModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Car car = new Car("lada", 1);
        testVehicle(car);
        car = new Car("lada-plus", 10);
        testVehicle(car);

        Vehicle motorbike = (Vehicle) Proxy.newProxyInstance(
                MotorbikeHandler.Motorbike.class.getClassLoader(),
                MotorbikeHandler.Motorbike.class.getInterfaces(),
                new MotorbikeHandler("yamaha", 1));
        testVehicle(motorbike);

        Vehicle motorbike1 = (Vehicle) Proxy.newProxyInstance(
                MotorbikeHandler.Motorbike.class.getClassLoader(),
                MotorbikeHandler.Motorbike.class.getInterfaces(),
                new MotorbikeHandler("yamaha", 10));
        testVehicle(motorbike1);
    }

    private static void testVehicle(Vehicle vehicle) {
        System.out.println("Testing vehicles " + vehicle.getBrand());
        vehicle.addModel("vesta", 100000.0);
        vehicle.addModel("x-ray", 150000.0);

        double avr = VehicleUtils.getAverage(vehicle);
        assert avr == 250000.0/2.0;

        String[] names = vehicle.getModelsNames();
        Double[] prices = vehicle.getModelsPrices();
        assert names[0].equals("vesta");
        assert names[1].equals("x-ray");
        assert prices[0] == 100000.0;
        assert prices[1] == 150000.0;

        vehicle.addModel("priora", 200000.0);
        vehicle.addModel("kalina", 250000.0);
        vehicle.addModel("kalina-sport", 300000.0);
        VehicleUtils.printModelsNamesAndPrices(vehicle);

        assert vehicle.getModelsNum() == 5;

        vehicle.setModelName("kalina", "kalina-plus");
        names = vehicle.getModelsNames();
        assert Arrays.asList(names).contains("kalina-plus");
        assert !Arrays.asList(names).contains("kalina");

        vehicle.setModelPriceByName("vesta", 120000.0);
        assert vehicle.getModelPriceByName("vesta") == 120000.0;

        boolean isException = false;
        try {
            vehicle.setModelPriceByName("kalina", 20000.0);
        } catch (NoSuchModelNameException e) {
            isException = true;
        }
        assert isException;
        isException = false;

        try {
            vehicle.getModelPriceByName("kalina");
        } catch (NoSuchModelNameException e) {
            isException = true;
        }
        assert isException;
        isException = false;

        try {
            vehicle.setModelPriceByName("kalina-plus", -100);
        } catch (ModelPriceOutOfBoundsException e) {
            isException = true;
        }
        assert isException;
        isException = false;

        try {
            vehicle.addModel("kalina-plus", 1000000.0);
        } catch (DuplicateModelNameException e) {
            isException = true;
        }
        assert isException;
        isException = false;

        vehicle.deleteModel("vesta");
        vehicle.deleteModel("priora");
        vehicle.deleteModel("kalina-sport");
        try {
            vehicle.deleteModel("vesta");
        } catch (NoSuchModelNameException e) {
            isException = true;
        }
        assert isException;

        System.out.println("After remove");
        VehicleUtils.printModelsNamesAndPrices(vehicle);
    }
}
