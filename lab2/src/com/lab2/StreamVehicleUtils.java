package com.lab2;

import com.lab1.VehicleUtils;
import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.vehicles.Car;
import com.lab1.vehicles.MotorbikeHandler;
import com.lab1.vehicles.Vehicle;

import java.io.*;

//Нужно записать марку транспортного средства, количество моделей, а затем список моделей и цен моделей
public class StreamVehicleUtils extends VehicleUtils {

    public static void outputVehicle(Vehicle vehicle, OutputStream out) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeBoolean(vehicle instanceof Car);
            dos.writeInt(vehicle.getBrand().getBytes().length);
            dos.write(vehicle.getBrand().getBytes());
            dos.writeInt(vehicle.getModelsNum());
            for (String name : vehicle.getModelsNames()) {
                dos.writeInt(name.getBytes().length);
                dos.write(name.getBytes());
            }
            for (Double price : vehicle.getModelsPrices()) {
                dos.writeDouble(price);
            }
        }
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException, DuplicateModelNameException {

        byte[] brand;
        int modelsNum = 0;
        String[] names;
        Double[] prices;
        boolean isCar = false;

        try (DataInputStream dis = new DataInputStream(in)) {
            isCar = dis.readBoolean();

            int charNum = dis.readInt();
            brand = new byte[charNum];
            dis.read(brand);

            modelsNum = dis.readInt();
            names = new String[modelsNum];
            int index = 0;
            while (index != modelsNum) {
                charNum = dis.readInt();
                byte[] name = new byte[charNum];
                dis.read(name);
                names[index] = new String(name);
                index++;
            }

            prices = new Double[modelsNum];
            index = 0;
            while (index != modelsNum) {
                prices[index] = dis.readDouble();
                index++;
            }
        }

        Vehicle result;
        if (isCar) {
            result = new Car(new String(brand), modelsNum);
        } else {
            result = new MotorbikeHandler.Motorbike(new String(brand), modelsNum);
        }
        for (int i = 0; i < modelsNum; i++) {
            result.addModel(names[i], prices[i]);
        }

        return result;
    }

    public static void writeVehicle(Vehicle vehicle, Writer out) {
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.println(vehicle instanceof Car);

            pw.println(vehicle.getBrand().length());
            pw.write(vehicle.getBrand().toCharArray());

            pw.println(vehicle.getModelsNum());

            for (String n : vehicle.getModelsNames()) {
                pw.println(n.length());
                pw.write(n.toCharArray());
            }

            for (Double p : vehicle.getModelsPrices()) {
                pw.println(p);
            }
        }
    }

    public static Vehicle readVehicle(Reader in) throws IOException,DuplicateModelNameException {
        char[] brand;
        int modelsNum = 0;
        String[] names;
        Double[] prices;
        boolean isCar = false;

        BufferedReader br = new BufferedReader(in);

        isCar = Boolean.parseBoolean(br.readLine());

        int charNum = Integer.parseInt(br.readLine());
        brand = new char[charNum];
        br.read(brand);

        modelsNum = Integer.parseInt(br.readLine());

        int index = 0;
        names = new String[modelsNum];
        while (index != modelsNum) {
            charNum = Integer.parseInt(br.readLine());
            char[] name = new char[charNum];
            br.read(name);
            names[index] = new String(name);
            index++;
        }

        index = 0;
        prices = new Double[modelsNum];
        while (index != modelsNum) {
            prices[index] = Double.parseDouble(br.readLine());
            index ++;
        }

        Vehicle result;
        if (isCar) {
            result = new Car(new String(brand), modelsNum);
        } else {
            result = new MotorbikeHandler.Motorbike(new String(brand), modelsNum);
        }
        for (int i = 0; i < modelsNum; i++) {
            result.addModel(names[i], prices[i]);
        }

        return result;
    }
}
