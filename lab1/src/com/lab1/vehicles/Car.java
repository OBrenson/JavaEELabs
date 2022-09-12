package com.lab1.vehicles;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.ModelPriceOutOfBoundsException;
import com.lab1.exceptions.NoSuchModelNameException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Car implements Vehicle, Serializable {

    public Car(String brand, int modelsLength) {
        this.brand = brand;
        this.models = new Model[modelsLength];
        IntStream.range(0, modelsLength).forEachOrdered(i -> models[i] = new Model());
    }

    private String brand;

    private Model[] models;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String oldName, String newName) throws NoSuchModelNameException {
        Optional<Model> model = findModelByName(oldName);
        model.orElseThrow(() -> new NoSuchModelNameException(oldName)).name = newName;
    }

    public String[] getModelsNames() {
        return Arrays.stream(models).map(m -> m.name).toArray(String[]::new);
    }

    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        Optional<Model> model = findModelByName(name);
        return model.orElseThrow(() -> new NoSuchModelNameException(name)).price;
    }

    public void setModelPriceByName(String name, double price) throws NoSuchModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException();
        }
        Optional<Model> model = findModelByName(name);
        model.orElseThrow(() -> new NoSuchModelNameException(name)).price = price;
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException();
        }
        if (Arrays.stream(this.models).anyMatch(m -> m.name.equals(name))) {
            throw new DuplicateModelNameException(name);
        }
        for (int i = 0; i < this.models.length; i++) {
            if (models[i].name.equals("")) {
                models[i].name = name;
                models[i].price = price;
                return;
            }
        }
        Model[] models = Arrays.copyOf(this.models, this.models.length + 1);
        models[models.length - 1] = new Model(name, price);
        this.models = models;
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        OptionalInt modelIndex = IntStream.range(0, this.models.length)
                .filter(i -> {
                    if (this.models[i] != null) {
                        return this.models[i].name.equals(name);
                    }
                    return false;
                })
                .findFirst();
        int delInd = modelIndex.orElseThrow(() -> new NoSuchModelNameException(name));
        Model[] models = new Model[this.models.length - 1];
        if (delInd != 0) {
            System.arraycopy(this.models, 0, models, 0, delInd);
        }
        if (delInd != this.models.length - 1) {
            System.arraycopy(this.models, delInd + 1, models, delInd, this.models.length - delInd - 1);
        }
        this.models = models;
    }

    public int getModelsNum() {
        return (int)Arrays.stream(this.models).filter(Objects::nonNull).count();
    }

    public Double[] getModelsPrices() {
        return Arrays.stream(models).filter(m -> m.name != null).map(m -> m.price).toArray(Double[]::new);
    }

    private Optional<Model> findModelByName(String name)  {
        return Arrays.stream(models).filter(m -> m.name != null).filter(m -> m.name.equals(name)).findFirst();
    }

    private class Model implements Serializable {

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public Model() {
            name = "";
            price = 0.0;
        }

        private String name;

        private Double price;
    }
}
