package com.lab1;

public interface Vehicle {

    void setModelName(String oldName, String newName);

    String[] getModelsNames();

    double getModelPriceByName(String name);

    void setModelPriceByName(String name, double price);

    void addModel(String name, double price);

    void deleteModel(String name);

    int getModelsNum();

    Double[] getModelsPrices();

    void setBrand(String brand);

    String getBrand();
}
