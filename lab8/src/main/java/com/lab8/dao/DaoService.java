package com.lab8.dao;

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

    public String getAlbumMinSong() {
        List<Object[]> res;
        try(Session session = factory.openSession()) {
            res = session.createNativeQuery("select s.name, min(a.name) as al, c.name as cname, min(c.duration) as dur from singer s " +
                    "left join album a on s.id = a.singer_id left join composition c on c.album_id = a.id group by s.name, c.name")
                    .getResultList();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object[]o : res) {
            stringBuilder.append(String.format("Singer: %s, Album: %s, Composition: %s, Duration: %s </br>", o[0], o[1], o[2], o[3]));
        }
        return stringBuilder.toString();
    }

    public String[] getAlbumWithMaxSongs() {
        List<Object[]> res;
        try(Session session = factory.openSession()) {
            res = session.createNativeQuery("select an,cn from (select a.name as an, count (c.name) as cn " +
                    "from album a left join composition c on a.id = c.album_id group by a.name) an " +
                    "where cn = (select max(sub.co) from (select count(c.name) as co from album a " +
                    "left join composition c on a.id = c.album_id group by a.name) sub)").getResultList();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object[]o : res) {
            stringBuilder.append(String.format("%s </br>", o[0]));
        }
        String[] resStr = new String[2];
        resStr[0] = stringBuilder.toString();
        resStr[1] = String.valueOf(res.get(0)[1]);
        return resStr;
    }

    public List<String> findByLetters(String letters, String clazzName) {
        try(Session session = factory.openSession()) {
            return session.createQuery(
                    String.format("select e.name from %s e where e.name like '%%%s%%'", clazzName, letters),
                    String.class).
                    getResultList();
        }
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
