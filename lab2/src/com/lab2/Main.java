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
        if (Arrays.asList(names).contains("")) {
            System.out.println("Empty names are not allowed");
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

        testVehicle(car);

        MotorbikeHandler.Motorbike motorbike = new MotorbikeHandler.Motorbike(brand, size);
        IntStream.range(0, names.length).forEachOrdered(i -> {
            try {
                motorbike.addModel(names[i], prices[i]);
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
        testVehicle(motorbike);

        serializeVehicles(car);
        serializeVehicles(motorbike);
    }

    private static void testVehicle(Vehicle vehicle) throws IOException, ClassNotFoundException, DuplicateModelNameException {
//        Vehicle carIn = StreamVehicleUtils.readVehicle(new InputStreamReader(System.in));
//        StreamVehicleUtils.writeVehicle(carIn, new OutputStreamWriter(System.out));
//        StreamVehicleUtils.printModelsNamesAndPrices(carIn);
        File file = File.createTempFile("vehicle", ".txt");
        try (
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
        ) {
            StreamVehicleUtils.outputVehicle(vehicle, fos);
            Vehicle carS = StreamVehicleUtils.inputVehicle(fis);

            compareVehicles(vehicle, carS, "Input/Output");
        }

        file = File.createTempFile("vehicle2", ".txt");
        try (
                FileReader fr = new FileReader(file);
                FileWriter fw = new FileWriter(file)
        ) {
            StreamVehicleUtils.writeVehicle(vehicle, fw);
            fw.close();
            Vehicle carSR = StreamVehicleUtils.readVehicle(fr);

            compareVehicles(vehicle, carSR, "Reader/Writer");
        }
        OutputStream originalOut = System.out;

        Vehicle carIn = StreamVehicleUtils.readVehicle(new InputStreamReader(System.in));
        StreamVehicleUtils.writeVehicle(carIn, new OutputStreamWriter(System.out));
        //System.setOut(new PrintStream(originalOut));
        System.out.println(carIn instanceof Car ? "Car" : "Motorbike");
        System.out.println(carIn.getBrand());
        StreamVehicleUtils.printModelsNamesAndPrices(carIn);

//        file = File.createTempFile("bike", ".txt");
//        OutputStream originalOut = System.out;
//        System.setIn(new FileInputStream(file));
//        System.setOut(new PrintStream(new FileOutputStream(file)));
//        try (
//                InputStream in = System.in;
//                PrintStream out = System.out;
//        ) {
//            Vehicle carSR = StreamVehicleUtils.readVehicle(new InputStreamReader(System.in));
//            StreamVehicleUtils.outputVehicle(vehicle, out);
//            Vehicle carS = StreamVehicleUtils.inputVehicle(in);
//            compareVehicles(vehicle, carS, "System.in/System.out");
//        }
    }

    private static void serializeVehicles(Vehicle vehicle)
            throws IOException, ClassNotFoundException
    {
        File file = File.createTempFile("car", ".txt");

        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(vehicle);

            try (
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);)
            {
                Vehicle carS = (Vehicle) ois.readObject();
                compareVehicles(vehicle, carS, "serialization");
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
            assert ((MotorbikeHandler.Motorbike) carO).getLastModified() !=
                    ((MotorbikeHandler.Motorbike) carS).getLastModified();
        }

        System.out.println(streamName);
        System.out.println(carO instanceof Car ? "Car" : "Motorbike");
        System.out.println("Original vehicle:");
        System.out.println(carO.getBrand());
        StreamVehicleUtils.printModelsNamesAndPrices(carO);

        System.out.println("Serialized vehicle:");
        System.out.println(carS.getBrand());
        StreamVehicleUtils.printModelsNamesAndPrices(carS);

        System.out.println("\n");
    }
}
