<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page session="true" import="java.util.*,es.studium.PracticaMVC.*"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/estilo.css" type="text/css">
		<title>Modificación Libros</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-12 mt-2">
					<div class="p-4 mb-3 bg-dark text-white">
						<h1 class="text-danger">Modificación de Libros - Aplicación de Gestión</h1>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<!--  BreadCrumb -->
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a>Login</a></li>
							<li class="breadcrumb-item"><a href="./aplicaciongestion.jsp">Aplicación Gestión</a></li>
							<li class="breadcrumb-item active" aria-current="page">Gestión de Libros</li>
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
						      	<a class="nav-item nav-link active">Gestión de Libros <span class="sr-only">(current)</span></a>
						      	<a class="nav-item nav-link" href="./gestionautores.jsp">Gestión de Autores</a>
						      	<a class="nav-item nav-link" href="./gestioneditoriales.jsp">Gestión de Editoriales</a>
						      	<a class="nav-item nav-link" href="./gestionpedidos.jsp">Gestión de Pedidos</a>
					    	</div>
					  	</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col-auto mt-3">
					<h2 class="text-success">Opciones Libros</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-3 mt-3">
					<div class="list-group" id="list-tab" role="tablist">
						<a class="list-group-item list-group-item-action list-group-item-success">Alta</a>
						<a class="list-group-item active list-group-item-action list-group-item-danger" id="list-mod-list" data-toggle="list" href="#list-mod" role="tab" aria-controls="mod">Modificación</a>
					</div>
				</div>
				<div class="col-5 mt-2">
				    <div class="tab-content" id="nav-tabContent">
					    <div class="tab-pane fade show active" id="list-mod" role="tabpanel" aria-labelledby="list-mod-list">
					    	<form action="aplicacion" method="post">
					    		<input type="hidden" name="todo" value="modificacionlibros">
							  	<div class="form-group">
								    <label>Nombre</label>
								    <input type="text" class="form-control" name="nombreLibro" value="<% out.println(ModeloMVC.saberLibro().get(0));%>">
								</div>
								<div class="form-group">
								    <label for="exampleInputPassword1">Precio</label>
								    <input type="text" class="form-control" name="precioLibro" value="<% out.println(ModeloMVC.saberLibro().get(1));%>">
								</div>
								<div class="form-group">
									<label for="autores">Modificar autor:</label>
									<select class="form-control" name="autores" id="autores">
										<%
											for (int i = 1; i < ModeloMVC.insertarAutoresModificacion().size(); i++) {
												out.println("<option value='" + i + "'>");
												out.println(ModeloMVC.insertarAutoresModificacion().get(i));
												out.println("</option>");
											}
										%>
								    </select>
								</div>
								<div class="form-group">
									<p class="text-secondary">Autor Seleccionado en la BD: <% out.println(ModeloMVC.identificarAutor());%></p>
								</div>
								<div class="form-group">
									<label for="editoriales">Modificar editorial:</label>
									<select class="form-control" name="editoriales" id="editoriales">
										<%
											for (int i = 1; i < ModeloMVC.insertarEditorialesModificacion().size(); i++) {
												out.println("<option value='" + i + "'>");
												out.println(ModeloMVC.insertarEditorialesModificacion().get(i));
												out.println("</option>");
											}
										%>
								    </select>
								</div>
								<div class="form-group">
									<p class="text-secondary">Editorial Seleccionada en la BD: <% out.println(ModeloMVC.identificarEditorial());%></p>
								</div>
								<input type="submit" class="btn btn-primary" value="Modificar Libro">
							</form>
					    </div>
				    </div>
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	</body>
</html>