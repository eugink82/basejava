package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        sqlStorage = (SqlStorage) Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");

        List<Resume> resumes = sqlStorage.getAllSorted();
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.println("<table border='1' cellpadding='5'>");
            for (Resume resume : resumes) {
                pw.println("<tr>");
                pw.println("<td  bgcolor=\"#c5ffa0\">");
                pw.println(resume.getUuid());
                pw.println("</td>");
                pw.println("<td bgcolor=\"#c0e4ff\">");
                pw.println(resume.getFullName());
                pw.println("</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }
    }
}
