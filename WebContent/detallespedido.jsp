<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*,es.studium.PracticaMVC.*"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/estilo.css" type="text/css">
		<title>Gestión de Pedidos - Detalles</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-12 mt-2">
					<div class="p-4 mb-3 bg-dark text-white">
						<h1 class="text-danger">Detalles de Pedidos - Aplicación de Gestión</h1>
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
							<li class="breadcrumb-item active" aria-current="page">Gestión de Pedidos</li>
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
						      	<a class="nav-item nav-link" href="./gestionautores.jsp">Gestión de Autores</a>
						      	<a class="nav-item nav-link" href="./gestioneditoriales.jsp">Gestión de Editoriales</a>
						      	<a class="nav-item nav-link active">Gestión de Pedidos <span class="sr-only">(current)</span></a>
					    	</div>
					  	</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col-auto mt-3">
					<h2 class="text-success">Opción Pedidos</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-3 mt-3">
					<div class="list-group" id="list-tab" role="tablist">
						<a class="list-group-item active list-group-item-action list-group-item-success" id="list-consulta-list" data-toggle="list" href="#list-consulta" role="tab" aria-controls="consulta">Consulta</a>
					</div>
				</div>
				<div class="col-8 mt-2">
				    <div class="tab-content" id="nav-tabContent">
					    <div class="tab-pane fade show active" id="list-consulta" role="tabpanel" aria-labelledby="list-mod-list">
							<h3 class="text-success">
								Detalles del Pedido Nº 
								<% 
									String[] id = ModeloMVC.insertarDetallesPedido().get(1).split(" - ");
									System.out.println(id[0]);
									out.println(id[0]);
								%>
							</h3>
							<table class="table table-dark">
						    	<thead>
						    		<tr>
						    			<th scope="col">Libros Comprado/s</th>
						    			<th scope="col">Precio Libro</th>
						    			<th scope="col">Cantidad</th>
						    			<th scope="col">Hora y Fecha del Pedido</th>
						    		</tr>
						    	</thead>
					    		<tbody>
					    			<%
										for (int i = 1; i < ModeloMVC.insertarDetallesPedido().size(); i++) {
											String[] datos = ModeloMVC.insertarDetallesPedido().get(i).split(" - ");
											System.out.println(datos[0]);
									%>
							    		<tr>
							    			<td><%= datos[1] + " - " + datos[2]%></td>
											<td><%= datos[3]%></td>
											<td><%= datos[4]%></td>
											<td><%= datos[5]%></td>
							    		</tr>
							    	<% 
							    		}
					    			%>
					    			<%
					    				String[] datos1 = ModeloMVC.insertarDetallesPedido().get(1).split(" - ");
					    			%>
					    			<tr>
					    				<th scope="col">Total Pedido</th>
					    				<th scope="col">Estado Pedido</th>
					    			</tr>
					    			<tr>
					    				<td><%= datos1[6]%></td>
					    				<td><%= datos1[7]%></td>
					    				<td>											
					    					<form action="aplicacion" method="post">
							    				<input class="btn btn-warning" type="submit" name="todo" value="Cambiar Estado">
							    			</form>
							    		</td>
							    	</tr>
					    		</tbody>
					    	</table>
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