<%@page import="br.com.edu.fuseki.Fuseki"%>
<%@page import="br.com.edu.artigo.Artigo"%>
<%@page import="org.apache.jena.base.Sys"%>
<%@page import="br.com.edu.cluster.Cluster"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.edu.arff.LoadArff"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:import url="cabecalho.jsp"/>
<c:import url="menu.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="jumbotron">
        <h1>TopWikiLak</h1>
        <p>Cluster</p>
    </div>
    <hr>

<table class="table table-bordered table-hover">
        <tr>
            <th>Título</th>
            <th>URI</th>
            <th>Detalhes</th>
        </tr>


        <%

            String render = "";
            ServletContext context = request.getServletContext();
            String caminho = context.getRealPath("/cluster/resultado.arff");
            ArrayList<Cluster> lista = new ArrayList<>();
            LoadArff load = new LoadArff();
            lista = load.carregarArff(caminho);
            Fuseki fuseki = new Fuseki();
            String contextPath = request.getContextPath().concat("/detalhes");
            String grupo = (String) request.getAttribute("grupo");
            Integer count = 0;
            for (Cluster cl : lista) {
                String titulo = fuseki.buscarTitulo(cl.getUri());
                
                if (cl.getGrupo().equals(grupo)){
                    out.print(render
                        .concat("<tr>")
                        .concat("<td>")
                        .concat(titulo)
                        .concat("</td>")
                        .concat("<td>")
                        .concat(cl.getUri())
                        .concat("</td>")
                        .concat("<td>")
                        .concat("<form action=\"").concat(contextPath).concat("\" method=\"post\">")
                        .concat("<input type=\"hidden\" name=\"uri\" value=\"").concat(cl.getUri()).concat("\" />")
                        .concat("<input class='btn btn-primary' type='submit' value='Ver Detalhes'/>")
                        .concat("</form>")
                        .concat("</td>")
                        .concat("</tr>"));
                    count+=1;
            }
            }
           out.print("<h1>"+grupo+"</h1>");
           out.print("<h3>Total: "+Integer.toString(count)+"</h3>");

        %>
    </table>
    <p><a onclick="history.back();" class="btn btn-primary btn-lg" href="#" role="button">Voltar</a></p>
    </div> <!-- /container -->

<c:import url="rodape.jsp"/>
