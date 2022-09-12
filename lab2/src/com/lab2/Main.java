package com.lab2;

import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.io.*;
import java.util.Objects;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String[] names = new String[]{"Car1", "Car2", "Car3", "Car4"};
        Double[] prices = new Double[]{100., 200., 300., 400.,};
        Car car = new Car("vlada", 4);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            car.addModel(names[i], prices[i]);
        });

        File file = File.createTempFile("vehicle", ".txt");
        try (
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
        ) {
            StreamVehicleUtils.outputVehicle(car, fos);
            Vehicle carS = StreamVehicleUtils.inputVehicle(fis);

            compareVehicles(car, carS, "Input/Output");
        }

        file = File.createTempFile("vehicle2", ".txt");
        try (
                FileReader fr = new FileReader(file);
                FileWriter fw = new FileWriter(file)
        ) {
            StreamVehicleUtils.writeVehicle(car, fw);
            Vehicle carSR = StreamVehicleUtils.readVehicle(fr);

            compareVehicles(car, carSR, "Reader/Writer");
        }

        try (
                InputStream in = System.in;
                OutputStream out = System.out;
        ) {
            try (
                InputStreamReader isr = new InputStreamReader(in);
                OutputStreamWriter osw = new OutputStreamWriter(out);
            ) {
                StreamVehicleUtils.writeVehicle(car, osw);
                Vehicle carS = StreamVehicleUtils.readVehicle(isr);
                compareVehicles(car, carS, "StreamReader");
            }
        }

        serializeVehicles();
    }

    private static void serializeVehicles() throws IOException, ClassNotFoundException {
        String[] names = new String[]{"Car1", "Car2", "Car3", "Car4"};
        Double[] prices = new Double[]{100., 200., 300., 400.,};
        Car car = new Car("vlada", 4);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            car.addModel(names[i], prices[i]);
        });

        File file = File.createTempFile("car", ".txt");

        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(car);

            try (
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);)
            {
                Vehicle carS = (Vehicle) ois.readObject();
                compareVehicles(car, carS, "serialization");
            }
        }

        MotorbikeHandler.Motorbike motorbike = new MotorbikeHandler.Motorbike("bike", 1);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            motorbike.addModel(names[i], prices[i]);
        });

        file = File.createTempFile("bike", ".txt");

        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(motorbike);

            try (
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);)
            {
                Vehicle motorbikeS = (Vehicle) ois.readObject();
                compareVehicles(motorbike, motorbikeS, "serialization");
            }
        }
    }

    private static void compareVehicles(Vehicle carO, Vehicle carS, String streamName) {

        assert carO.getBrand().equals(carS.getBrand());
        assert carO.getModelsNum() == carS.getModelsNum();

        String[] namesO = carO.getModelsNames();
        String[] namesS = carS.getModelsNames();
        IntStream.range(0, carO.getModelsNum()).forEachOrdered(i -> {
            assert namesO[i].equals(namesS[i]);
        });

        Double[] pricesO = carO.getModelsPrices();
        Double[] pricesS = carS.getModelsPrices();

        IntStream.range(0, carO.getModelsNum()).forEachOrdered(i -> {
            assert Objects.equals(pricesO[i], pricesS[i]);
        });

        if ("serialization".equals(streamName) && carO instanceof MotorbikeHandler.Motorbike) {
            assert ((MotorbikeHandler.Motorbike) carO).getLastModified() ==
                    ((MotorbikeHandler.Motorbike) carS).getLastModified();
        }

        System.out.println(streamName);

        System.out.println("Original vehicle:");
        System.out.println(carO.getBrand());
        StreamVehicleUtils.printModelsNamesAndPrices(carO);

        System.out.println("Serialized vehicle:");
        System.out.println(carS.getBrand());
        StreamVehicleUtils.printModelsNamesAndPrices(carS);

        System.out.println("\n");
    }
}
