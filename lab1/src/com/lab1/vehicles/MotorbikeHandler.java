package com.lab1.vehicles;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.ModelPriceOutOfBoundsException;
import com.lab1.exceptions.NoSuchModelNameException;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

public class MotorbikeHandler implements InvocationHandler {

    private final Motorbike original;

    public MotorbikeHandler(String name, int size) {
        this.original = new Motorbike(name, size);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().contains("set") ||
                method.getName().contains("add") ||
                method.getName().contains("delete")) {
            original.lastModified = new Date().getTime();
        }
        try {
           Object res = method.invoke(original, args);
           return res;
        } catch (InvocationTargetException | UndeclaredThrowableException e) {
            throw e.getCause();
        }
    }

    public static class Motorbike implements Vehicle, Serializable {

        private int size = 0;

        private Model head;

        private long lastModified;

        private String brand;

        {
            lastModified = new Date().getTime();
        }

        public Motorbike(String brand, int size)  {
            this.brand = brand;
            this.size = size;
            if (size > 0) {
                this.head = new Model();
                head.prev = head;
                head.next = head;
                IntStream.range(1, size).forEach(i -> {
                    Model node = new Model();
                    node.prev = head.prev;
                    node.next = head;
                    head.prev.next = node;
                    head.prev = node;
                });
            }
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
            String[] names = getArray(String.class, m -> m.name);
            return Arrays.stream(names).filter(Objects::nonNull).toArray(String[]::new);
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
            Double[] prices = getArray(Double.class, m -> m.price);
            return Arrays.stream(prices).filter(Objects::nonNull).toArray(Double[]::new);
        }

        public void addModel(String name, double price) throws DuplicateModelNameException {
            if (head == null) {
                head = new Model(name, price);
                head.next = head;
                head.prev = head;
                size++;
            } else {
                try {
                    findModelByName(name);
                } catch (NoSuchModelNameException e) {  //основная логика в catch

                    Model node = head;
                    do {
                        if (node.name == null) {
                            node.name = name;
                            node.price = price;
                            return;
                        }
                        node = node.next;
                    } while (node != head);

                    Model newNode = new Model(name, price);
                    newNode.next = head;
                    newNode.prev = head.prev;
                    head.prev.next = newNode;
                    head.prev = newNode;
                    size++;
                    return;
                }
                throw new DuplicateModelNameException(name);
            }
        }

        public void deleteModel(String name) throws NoSuchModelNameException {
            if (head == null) {
                throw new NoSuchModelNameException(name);
            }
            Model node = findModelByName(name);
            node.next.prev = node.prev;
            node.prev.next = node.next;
            if(head == node) {
                head = node.next;
            }
            size--;
        }

        public int getModelsNum() {
            int res = 0;
            Model node = head;
            do {
                if(node.name != null) {
                    res++;
                }
                node = node.next;
            } while (node != head);
            return res;
        }

        private <T> T[] getArray(Class<T> clazz, Function<Model, T> supplier) {
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
                if (node.name != null && node.name.equals(name)) {
                    return node;
                }
                node = node.next;
            } while (node != head);
            throw new NoSuchModelNameException(name);
        }

        private class Model implements Serializable {

            public Model(String name, double price) {

                this.name = name;
                this.price = price;
            }

            public Model() {

            }

            private Model next;

            private Model prev;

            private String name;

            private Double price;
        }
    }
}