package com.lab8.servlets;

import com.lab8.DataUtil;
import com.lab8.dao.DaoService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ExpandedServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        DaoService daoService = DaoService.getDaoService();
        DataUtil.insertData(daoService);
    }
}
