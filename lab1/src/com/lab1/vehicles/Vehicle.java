package com.lab1.vehicles;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.NoSuchModelNameException;

public interface Vehicle {

    void setModelName(String oldName, String newName);

    String[] getModelsNames();

    double getModelPriceByName(String name);

    void setModelPriceByName(String name, double price) throws NoSuchModelNameException;

    void addModel(String name, double price) throws DuplicateModelNameException;

    void deleteModel(String name) throws NoSuchModelNameException ;

    int getModelsNum();

    Double[] getModelsPrices();

    void setBrand(String brand);

    String getBrand();
}
