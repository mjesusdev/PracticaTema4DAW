<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" import="java.util.*,es.studium.PracticaMVC.*"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/estiloaplicacion.css" type="text/css">
		<title>Gestión de Autores</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-12 mt-2">
					<div class="p-4 mb-3 bg-dark text-white">
						<h1 class="text-danger">Gestión de Autores - Aplicación de Gestión</h1>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<!--  BreadCrumb -->
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="./login.jsp">Login</a></li>
							<li class="breadcrumb-item"><a href="./aplicaciongestion.jsp">Aplicación Gestión</a></li>
							<li class="breadcrumb-item active" aria-current="page">Gestión de Autores</li>
						</ol>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
					  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
					    	<span class="navbar-toggler-icon"></span>
					  	</button>
					  	<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
					    	<div class="navbar-nav">
						      	<a class="nav-item nav-link" href="./gestionlibros.jsp">Gestión de Libros</a>
						      	<a class="nav-item nav-link active">Gestión de Autores <span class="sr-only">(current)</span></a>
						      	<a class="nav-item nav-link" href="./gestioneditoriales.jsp">Gestión de Editoriales</a>
						      	<a class="nav-item nav-link" href="./gestionpedidos.jsp">Gestión de Pedidos</a>
					    	</div>
					  	</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col-5 mt-2">
				    <table class="table table-dark">
				    	<thead>
				    		<tr>
				    			<th scope="col">Id</th>
				    			<th scope="col">Nombre</th>
				    			<th scope="col">Apellidos</th>
				    			<th scope="col">Fecha de Nacimiento</th>
				    		</tr>
				    	</thead>
				    	<tbody>
				    		<%
								for (int i = 0; i < ModeloMVC.insertarAutores().size(); i++) {
							%>
								<tr>
									<td scope="col"><%=ModeloMVC.insertarAutores().get(i)%></td>
									<td scope="col"><%=ModeloMVC.insertarAutores().get(i)%></td>
									<td scope="col"><%=ModeloMVC.insertarAutores().get(i)%></td>
									<td scope="col"><%=ModeloMVC.insertarAutores().get(i)%></td>
								</tr>
							<%
								}
				    		%>
				    	</tbody>
				    </table>
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	</body>
</html>