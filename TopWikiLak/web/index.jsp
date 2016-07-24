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
    <c:import url="todosArtigos.jsp"/>
</div> <!-- /container -->

<c:import url="rodape.jsp"/>
