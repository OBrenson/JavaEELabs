package com.lab8.servlets;

import com.lab8.dao.DaoService;
import com.lab8.domain.Album;
import com.lab8.domain.BaseEntity;
import com.lab8.domain.Composition;
import com.lab8.domain.Singer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "RemoveServlet",
        urlPatterns = {"/remove"}
)
public class RemoveActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String type = idStr.split("=")[0];
        if (!"".equals(idStr)) {
            Long id = Long.parseLong(idStr.split("=")[1]);
            Class c = null;
            if (type.equals("singer")) {
                c = Singer.class;
            }
            if (type.equals("composition")) {
                c = Composition.class;
            }
            if (type.equals("album")) {
                c = Album.class;
            }
            if (c != null) {
                doDeleteAndUpdate(type, c, id, req, resp);
            }
        }

    }

    private <T extends BaseEntity> void doDeleteAndUpdate(String type, Class<T> clazz, Long id,
                                                             HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        T e = DaoService.getDaoService().findById(id, clazz);
        if (e != null) {
            DaoService.getDaoService().delete(e);
            resp.sendRedirect(req.getContextPath() + "/" + type + "s");
        }
    }

}
