package com.lab8.servlets;

import com.lab8.DataUtil;
import com.lab8.dao.DaoService;
import com.lab8.domain.Singer;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@WebServlet(
        name = "SingerServlet",
        urlPatterns = {"/singers"}
)
public class SingerServlet extends HttpServlet {

    List<Singer> singers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        singers = DaoService.getDaoService().findAll(Singer.class);
        singers.add(new Singer.SingerBuilder("").build());
        req.setAttribute("minDuration", DaoService.getDaoService().getAlbumMinSong());
        updatePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Singer s = new Singer.SingerBuilder(name).build();

        if (name == null || "".equals(name)) {
            updatePage(req, resp);
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (!"".equals(idStr)) {
                Long id = Long.parseLong(idStr);
                s.setId(id);
                DaoService.getDaoService().update(s);
            } else {
                DaoService.getDaoService().save(s);
            }
        } catch (PersistenceException ex) {
            req.setAttribute("nameException", name);
            updatePage(req, resp);
            return;
        }
        singers = DaoService.getDaoService().findAll(Singer.class);
        singers.add(new Singer.SingerBuilder("").build());
        req.setAttribute("nameException", null);
        updatePage(req, resp);
    }

    private void updatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("singers", singers);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/singers.jsp");
        rd.forward(req, resp);
    }
}
