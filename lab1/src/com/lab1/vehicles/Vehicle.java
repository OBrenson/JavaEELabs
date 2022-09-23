package com.lab1.vehicles;

import com.lab1.exceptions.DuplicateModelNameException;
import com.lab1.exceptions.NoSuchModelNameException;

public interface Vehicle {

    String s = "";

    void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;

    String[] getModelsNames();

    double getModelPriceByName(String name) throws NoSuchModelNameException;

    void setModelPriceByName(String name, double price) throws NoSuchModelNameException;

    void addModel(String name, double price) throws DuplicateModelNameException;

    void deleteModel(String name) throws NoSuchModelNameException ;

    int getModelsNum();

    Double[] getModelsPrices();

    void setBrand(String brand);

    String getBrand();
}
