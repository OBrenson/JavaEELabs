package com.lab2;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, DuplicateModelNameException {
        if (args.length != 4) {
            System.out.println("Not enough params");
            return;
        }

        String brand = args[0];
        int size = Integer.parseInt(args[1]);
        String[] names = args[2].split(",");
        Double[] prices = Arrays.stream(args[3].split(",")).map(Double::parseDouble).toArray(Double[]::new);
        if (names.length != prices.length) {
            System.out.println("Number of prices and names must be equal");
            return;
        }

        Car car = new Car(brand, size);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            try {
                car.addModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
                System.exit(1);
            }
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

        file = File.createTempFile("bike", ".txt");
        OutputStream originalOut = System.out;
        System.setIn(new FileInputStream(file));
        System.setOut(new PrintStream(new FileOutputStream(file)));
        try (
                InputStream in = System.in;
                PrintStream out = System.out;
        ) {
            StreamVehicleUtils.outputVehicle(car, out);
            Vehicle carS = StreamVehicleUtils.inputVehicle(in);
            System.setOut(new PrintStream(originalOut));
            compareVehicles(car, carS, "System.in/System.out");
        }

        serializeVehicles();
    }

    private static void serializeVehicles() throws IOException, ClassNotFoundException {
        String[] names = new String[]{"Car1", "Car2", "Car3", "Car4"};
        Double[] prices = new Double[]{100., 200., 300., 400.,};
        Car car = new Car("vlada", 4);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            try {
                car.addModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
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
            try {
                motorbike.addModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
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
