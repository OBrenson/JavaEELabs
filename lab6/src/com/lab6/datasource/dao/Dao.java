package com.lab6.datasource.dao;

import com.lab6.datasource.domain.BaseEntity;

import java.sql.SQLException;

public interface Dao {

    void create(BaseEntity entity) throws SQLException;

    void update(Long id, BaseEntity entity);

    void delete(BaseEntity entity);

    void get(BaseEntity entity);
}
