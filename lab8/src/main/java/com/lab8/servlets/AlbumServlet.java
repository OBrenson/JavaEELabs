package com.lab8.servlets;

import com.lab8.dao.DaoService;
import com.lab8.domain.Album;
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
        name = "AlbumServlet",
        urlPatterns = {"/albums"}
)
public class AlbumServlet extends HttpServlet {

    List<Album> albums = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        albums = DaoService.getDaoService().findAll(Album.class);
        albums.add(new Album.AlbumBuilder("","", new Singer()).build());
        updatePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String genre = req.getParameter("genre");
        String singerName = req.getParameter("singerName");

        Singer singer = DaoService.getDaoService().findByName(singerName, Singer.class);
        if (singer == null) {
            req.setAttribute("singerException", singerName);
            updatePage(req, resp);
            return;
        }
        Album a = new Album.AlbumBuilder(name, genre, singer).build();

        if (name == null || "".equals(name)) {
            updatePage(req, resp);
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (!"".equals(idStr)) {
                Long id = Long.parseLong(idStr);
                a.setId(id);
                DaoService.getDaoService().update(a);
            } else {
                DaoService.getDaoService().save(a);
            }
        } catch (PersistenceException ex) {
            req.setAttribute("nameException", name);
            updatePage(req, resp);
            return;
        }

        albums = DaoService.getDaoService().findAll(Album.class);
        albums.add(new Album.AlbumBuilder("", "", new Singer()).build());

        req.setAttribute("singerException", null);
        req.setAttribute("nameException", null);
        updatePage(req, resp);
    }

    private void updatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] maxSongs = DaoService.getDaoService().getAlbumWithMaxSongs();
        req.setAttribute("count", maxSongs[1]);
        req.setAttribute("maxNames", maxSongs[0]);
        req.setAttribute("albums", albums);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/albums.jsp");
        rd.forward(req, resp);
    }
}
