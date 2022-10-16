package com.lab8;

import com.lab8.dao.DaoService;
import com.lab8.domain.Album;
import com.lab8.domain.Composition;
import com.lab8.domain.Singer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        DaoService daoService = DaoService.getDaoService();
        DataUtil.insertData(daoService);
        Singer s = daoService.findByName("Led Zeppelin", Singer.class);
        System.out.println(s);
        s.setName("NeW " + s.getName());
        daoService.update(s);
        s = daoService.findByName(s.getName(), Singer.class);
        System.out.println(s);
        daoService.delete(s);
        s = daoService.findByName(s.getName(), Singer.class);
        assert s == null;

        List<Composition> compositions = daoService.findAll(Composition.class);
        for(Composition c : compositions) {
            System.out.println(c);
        }
        List<Singer> singers = daoService.findAll(Singer.class);
        System.out.println(singers);
    }

}
