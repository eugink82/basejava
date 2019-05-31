package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.sql.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-type","text/html;charset=UTF-8");
        String name=request.getParameter("name");
        //response.getWriter().write(name==null ? "Hello resumes!" : "Hello "+name+"!");

        try {
            Class.forName("org.postgresql.Driver");
            //Class.forName("out.com.");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
       // SqlHelper sqlHelper = new SqlHelper(() -> DriverManager.getConnection(Config.get().getDbUrl().toString(),Config.get().getDbUser().toString(),Config.get().getDbPassword().toString()));
        SqlHelper sqlHelper = new SqlHelper(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes","postgres","postgres"));
        sqlHelper.execute("SELECT * FROM resume",ps->{
            ResultSet rs=ps.executeQuery();
            PrintWriter pw=null;
            try {
                pw = response.getWriter();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            if(pw!=null) {
                pw.println("<table border='1' cellpadding='5'>");
                while (rs.next()) {
                    pw.println("<tr>");
                    pw.println("<td  bgcolor=\"#c5ffa0\">");
                    pw.println(rs.getString("uuid"));
                    pw.println("</td>");
                    pw.println("<td bgcolor=\"#c0e4ff\">");
                    pw.println(rs.getString("full_name"));
                    pw.println("</td>");
                    pw.println("</tr>");
                }
                pw.println("</table>");
            }
            return null;
        });

    }
}
