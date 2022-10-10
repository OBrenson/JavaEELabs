package com.lab6.datasource.dao;

import com.lab6.datasource.domain.BaseEntity;

import java.sql.SQLException;

public interface Dao {

    void create(BaseEntity entity) throws SQLException;

    void update(String name, BaseEntity entity) throws SQLException;

    void delete(BaseEntity entity) throws SQLException;

    BaseEntity get(String name) throws SQLException;
}
