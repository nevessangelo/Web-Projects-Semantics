    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">TopWikiLak</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="${pageContext.request.contextPath}">Home</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Ver Clusters<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <!--<li role="separator" class="divider"></li> -->
                <li class="dropdown-header">Clusters</li>
                <li><a href="#" onclick="document.getElementById('formClusterA').submit();">Cluster A</a></li>
                <li><a href="#" onclick="document.getElementById('formClusterB').submit();">Cluster B</a></li>
                <li><a href="#" onclick="document.getElementById('formClusterC').submit();">Cluster C</a></li>
                <li><a href="#" onclick="document.getElementById('formClusterD').submit();">Cluster D</a></li>
              </ul>
            </li>
            <li><a href="#about" onclick="document.getElementById('formSobre').submit();">Sobre</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
<form id="formClusterA" action="${pageContext.request.contextPath}/cluster" method="post">
    <input type="hidden" name="grupo" value="Cluster-A">
</form>
<form id="formClusterB" action="${pageContext.request.contextPath}/cluster" method="post">
    <input type="hidden" name="grupo" value="Cluster-B">
</form>
<form id="formClusterC" action="${pageContext.request.contextPath}/cluster" method="post">
    <input type="hidden" name="grupo" value="Cluster-C">
</form>
<form id="formClusterD" action="${pageContext.request.contextPath}/cluster" method="post">
    <input type="hidden" name="grupo" value="Cluster-D">
</form>
<form id="formSobre" action="${pageContext.request.contextPath}/sobre" method="post">
    <input type="hidden" name="sobre" value="Sobre">
</form>