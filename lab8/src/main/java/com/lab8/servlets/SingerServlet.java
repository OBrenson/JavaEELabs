package com.lab8.servlets;

import com.lab8.DataUtil;
import com.lab8.dao.DaoService;
import com.lab8.domain.Singer;

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
        name = "MyServlet",
        urlPatterns = {"/singers"}
)
public class SingerServlet extends HttpServlet {

    List<Singer> singers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        singers = DaoService.getDaoService().findAll(Singer.class);
        singers.add(new Singer.SingerBuilder("").build());
        updatePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Singer s = new Singer.SingerBuilder(name).build();

        String idStr = req.getParameter("id");
        Long id;
        if(!"".equals(idStr) && !"".equals(name)) {
            id = Long.parseLong(idStr);
            s.setId(id);
            singers.stream().filter(singer -> id.equals(singer.getId())).findFirst().get().setName(name);
            DaoService.getDaoService().update(s);
        } else {
            DaoService.getDaoService().save(s);
            singers.add(singers.size() - 1, s);
        }

        updatePage(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = new BufferedReader(new InputStreamReader(req.getInputStream())).readLine();
        if (idStr != null && !"".equals(idStr)) {
            Long id = Long.parseLong(idStr.replace("id=", ""));
            Singer s = DaoService.getDaoService().findById(id, Singer.class);
            if (s != null) {
                DaoService.getDaoService().delete(s);
                resp.getWriter().print(true);
                return;
            }
        }
        resp.getWriter().print(false);
    }

    private void updatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("singers", singers);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/singers.jsp");
        rd.forward(req, resp);
    }
}
