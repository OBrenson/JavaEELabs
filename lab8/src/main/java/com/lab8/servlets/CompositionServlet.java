package com.lab8.servlets;

import com.lab8.dao.DaoService;
import com.lab8.domain.Album;
import com.lab8.domain.Composition;
import com.lab8.domain.Singer;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "CompositionServlet",
        urlPatterns = {"/compositions"}
)
public class CompositionServlet extends HttpServlet {

    List<Composition> compositions = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        compositions = DaoService.getDaoService().findAll(Composition.class);
        compositions.add(new Composition.CompositionBuilder("", 0, new Album()).build());
        updatePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String albumName = req.getParameter("albumName");
        String dur = req.getParameter("duration");
        int duration = Integer.parseInt(dur);
        if (duration < 0) {
            req.setAttribute("durException", "true");
            updatePage(req, resp);
            return;
        }

        Album album = DaoService.getDaoService().findByName(albumName, Album.class);
        if (album == null) {
            req.setAttribute("albumException", albumName);
            updatePage(req, resp);
            return;
        }

        if (name == null || "".equals(name)) {
            updatePage(req, resp);
            return;
        }
        Composition c = new Composition.CompositionBuilder(name, Integer.parseInt(dur), album).build();

        String idStr = req.getParameter("id");
        if (!"".equals(idStr)) {
            Long id = Long.parseLong(idStr);
            c.setId(id);
            DaoService.getDaoService().update(c);
        } else {
            DaoService.getDaoService().save(c);
        }

        compositions = DaoService.getDaoService().findAll(Composition.class);
        compositions.add(new Composition.CompositionBuilder("", 0, new Album()).build());

        req.setAttribute("durException", null);
        req.setAttribute("albumException", null);
        updatePage(req, resp);
    }

    private void updatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("compositions", compositions);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/compositions.jsp");
        rd.forward(req, resp);
    }
}
