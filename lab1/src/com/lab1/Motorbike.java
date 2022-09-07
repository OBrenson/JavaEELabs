package com.lab1;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.ModelPriceOutOfBoundsException;
import com.lab1.exceptions.NoSuchModelNameException;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.function.Function;

public class Motorbike {

    private int size = 0;

    private Model head;

    private long lastModified;

    private String brand;

    {
        lastModified = new Date().getTime();
    }

    public Motorbike(String brand, int size) {
        this.brand = brand;
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String oldName, String newName) throws NoSuchModelNameException {
        Model model = findModelByName(oldName);
        model.name = newName;
    }

    public String[] getModelsNames() {
        return getArray(String.class, m -> m.name);
    }

    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        Model model = findModelByName(name);
        return model.price;
    }

    public void setModelPriceByName(String name, double price) throws NoSuchModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException();
        }
        Model model = findModelByName(name);
        model.price = price;
    }

    public Double[] getModelsPrices() {
        return getArray(Double.class, m -> m.price);
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (head == null) {
            head = new Model(name, price);
            head.next = head;
            head.prev = head;
        } else {
            try {
                findModelByName(name);
            } catch (NoSuchModelNameException e) {  //основная логика в catch
                Model node = new Model(name, price);
                node.next = head;
                node.prev = head.prev;
                head.prev = node;
            }
            throw new DuplicateModelNameException(name);
        }
        size++;
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        if (head == null) {
            throw new NoSuchModelNameException(name);
        }
        Model node = findModelByName(name);
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    public int getModelsNum() {
        return size;
    }

    private <T> T[] getArray(Class<T> clazz, Function<Model,T> supplier) {
        T[] res = (T[]) Array.newInstance(clazz, size);
        if (head == null) {
            return res;
        }
        Model node = head;
        int i = 0;
        do {
            res[i] = supplier.apply(node);
            node = node.next;
            i++;
        } while (node != head);
        return res;
    }

    private Model findModelByName(String name) throws NoSuchModelNameException {
        if (head == null) {
            throw new NoSuchModelNameException(name);
        }
        Model node = head;
        do {
            if (node.name.equals(name)) {
                return node;
            }
            node = node.next;
        } while (node != head);
        throw new NoSuchModelNameException(name);
    }

    private class Model {

        public Model(String name, double price) {

            this.name = name;
            this.price = price;
        }

        private Model next;

        private Model prev;

        private String name;

        private Double price;
    }

}
