package com.lab8.servlets;

import com.lab8.dao.DaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet(
        name = "AutoCompleteServlet",
        urlPatterns = {"/autocomplete"}
)
public class AutoCompleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = new BufferedReader(new InputStreamReader(req.getInputStream())).readLine();
        String[] params = s.split("=");
        if (params.length == 2 && !params[1].equals("")) {
            List<String> resList = DaoService.getDaoService().findByLetters(params[1], params[0]);
            if(!resList.isEmpty()) {
                StringBuilder res = new StringBuilder();
                for (String str : resList) {
                    res.append(str).append("\n");
                }
                resp.getOutputStream().print(res.toString());
            }
        }
    }

}
