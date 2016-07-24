<%@page import="br.com.edu.artigo.Topicos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.edu.artigo.Autor"%>
<%@page import="br.com.edu.artigo.Artigo"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:import url="cabecalho.jsp"/>
<c:import url="menu.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="jumbotron">
        <h1>TopWikiLak</h1>
        <p>Projeto de Web Semantica</p>
    </div>
    <hr>
    <%
        Artigo artigo;
        artigo = (Artigo) request.getAttribute("artigo");
        ArrayList<Autor> listaAutor = new ArrayList<>();
        listaAutor = artigo.getAutor();
        ArrayList<Topicos> listaTopicos = new ArrayList<>();
        listaTopicos = artigo.getTopicos();
    %>
    
    <div class="row">
        <div class="col-md-6">
            <h3>Titulo</h3>
            <%
                out.print("<p>"+artigo.getTitulo()+"</p>");
            %>
            <hr>
            <h3>Conferência</h3>
            <%
                out.print("<p>"+artigo.getConferencia()+"</p>");
            %>
            <hr>
            <h3>Autores</h3>
            <%
                for(Autor a: listaAutor){
                    out.print("<p>"+a.getPrimeiro_nome()+" "+a.getUltimo_nome()+"</p>");
                }
            %>
            <hr>
            <h3>Tópicos</h3>
            <%
                out.print("<dl class=\"dl-horizontal\">");
                String contextPath = request.getContextPath().concat("/topico");
                for(Topicos t: listaTopicos){
                    out.print("<form action=\""+contextPath+"\" method=\"post\">"
                        +"<dt>"+t.getNome()+"</dt>"
                        +"<input type=\"hidden\" name=\"nomeTopico\" value=\""+t.getNome()+"\" />"
                        +"<dd>"+"<input class='btn btn-default btn-xs' type='submit' value='Ver Artigos'/>"+"</dd>"
                        +"</form>");
                }
                out.print("</dl>");
            %>
        </div>
        <div class="col-md-6">
            <h2>Resumo</h2>
            <%
                out.print("<p>"+artigo.getResumo()+"</p>");
            %>
        </div>
      </div>
    
    <p><a onclick="history.back();" class="btn btn-primary btn-lg" href="#" role="button">Voltar</a></p>
</div> <!-- /container -->

<c:import url="rodape.jsp"/>