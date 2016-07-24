<%@page import="br.com.edu.fuseki.Fuseki"%>
<%@page import="br.com.edu.artigo.Artigo"%>
<%@page import="org.apache.jena.base.Sys"%>
<%@page import="br.com.edu.cluster.Cluster"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.edu.arff.LoadArff"%>   

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
            for (Cluster cl : lista) {
                String titulo = fuseki.buscarTitulo(cl.getUri());
             
//                render = render.concat("<tr><td>").concat(titulo).concat("</td><td>").concat(cl.getUri()).concat("</td><td><a href=\"#\">Detalhes</a></td></tr>");    
                render = render
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
                        .concat("</tr>");    
            }
            out.print("<h1>Todos os Artigos</h1>");
            out.print("<h3>Total: "+lista.size()+"</h3>");
            out.print(render);

        %>
    </table>
