package com.lab8.servlets;

import com.lab8.dao.DaoService;
import com.lab8.domain.Composition;
import com.lab8.domain.Singer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "CompositionServlet",
        urlPatterns = {"/compositions"}
)
public class CompositionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Composition> compositions = DaoService.getDaoService().findAll(Composition.class);
        req.setAttribute("compositions", compositions);
        RequestDispatcher rd = req.getRequestDispatcher("/compositions.jsp");
        rd.forward(req, resp);
    }
}
