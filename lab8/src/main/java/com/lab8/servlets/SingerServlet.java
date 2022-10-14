package com.lab8.servlets;

import com.lab8.dao.DaoService;
import com.lab8.domain.Singer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SingerServlet", value = "/singers")
public class SingerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Singer> singers = new DaoService().findAll(Singer.class);
        req.setAttribute("singers", singers);
        RequestDispatcher rd = req.getRequestDispatcher("/singers.jsp");
        rd.forward(req, resp);
    }

}
