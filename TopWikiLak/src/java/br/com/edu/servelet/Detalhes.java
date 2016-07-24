/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.servelet;

import br.com.edu.artigo.Artigo;
import br.com.edu.fuseki.Fuseki;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author angelo
 */
@WebServlet(name = "Detalhes", urlPatterns = {"/detalhes"})
public class Detalhes extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Detalhes() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }
    
     protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fuseki fuseki = new Fuseki();
        Artigo artigo;
        artigo = fuseki.buscarParametrosArtigo(request.getParameter("uri"));
        
        request.setAttribute("artigo", artigo);
        request.getRequestDispatcher("artigoDetalhe.jsp").forward(request, response);
        response.sendRedirect("artigoDetalhe.jsp");
     }

}
