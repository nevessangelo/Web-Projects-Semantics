/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author angelo
 */
@WebServlet(name = "Cluster", urlPatterns = {"/cluster"})
public class Cluster extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Cluster() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }
    
     protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("grupo", request.getParameter("grupo"));
        request.getRequestDispatcher("cluster.jsp").forward(request, response);
        response.sendRedirect("cluster.jsp");
     }

}
