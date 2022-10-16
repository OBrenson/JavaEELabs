package com.lab8.dao;

import com.lab8.DataUtil;
import com.lab8.domain.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class DaoService {

    private SessionFactory factory;

    private static DaoService daoService;

    private DaoService() {
    }

    public static DaoService getDaoService() {
        if (daoService == null) {
            daoService = new DaoService();
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
            daoService.factory = meta.getSessionFactoryBuilder().build();
        }
        return daoService;
    }

    public void init() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        factory = meta.getSessionFactoryBuilder().build();
    }

    public <T extends BaseEntity>T findByName(String name, Class<T> clazz) {
        return findBy(clazz, "name", name);
    }

    public <T extends BaseEntity>T findById(Long id, Class<T> clazz) {
        return findBy(clazz, "id", id);
    }

    private <T extends BaseEntity>T findBy(Class<T> clazz, String field, Object by) {
        String queryName = getClazzName(clazz) + ".findBy" + field;
        try (Session session = factory.openSession()) {
            return session.createNamedQuery(queryName, clazz)
                    .setParameter(field, by).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
        String queryName = getClazzName(clazz) + ".findAll";
        try (Session session = factory.openSession()) {
            return session.createNamedQuery(queryName, clazz).getResultList();
        } catch (NoResultException ex) {
            return new ArrayList<>();
        }
    }

    public <T extends BaseEntity>void save(T baseEntity) {
        execTransaction(baseEntity, (be,s) ->
                s.save(be)
        );
    }

    public <T extends BaseEntity>void update(T baseEntity) {
        execTransaction(baseEntity, (be,s) -> {
                s.update(be);
            }
        );
    }

    public <T extends BaseEntity>void delete(T baseEntity) {
        execTransaction(baseEntity, (be,s) -> {
                    s.delete(be);
                }
        );
    }

    private <T extends BaseEntity> void execTransaction(T baseEntity, BiConsumer<T, Session> func) {
        try (Session session = factory.openSession()) {
            Transaction t = session.beginTransaction();
            func.accept(baseEntity, session);
            t.commit();
        }
    }

    private <T extends BaseEntity>String getClazzName(Class<T> clazz) {
        return clazz.getName().replace("com.lab8.domain.", "");
    }
}
