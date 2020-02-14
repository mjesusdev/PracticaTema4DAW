<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/estiloaplicacion.css" type="text/css">
		<title>Aplicación de Gestión</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-12 mt-2">
					<div class="p-4 mb-3 bg-dark text-white">
						<h1 class="text-danger">Aplicación de Gestión</h1>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<!--  BreadCrumb -->
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="./login.jsp">Login</a></li>
							<li class="breadcrumb-item active" aria-current="page">Aplicación Gestión</li>
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
						      	<a class="nav-item nav-link" href="./gestionpedidos.jsp">Gestión de Pedidos</a>
					    	</div>
					  	</div>
					</nav>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-10 mt-3">
					<img src="./books.jpg" class="img-fluid" alt="Foto de Libros sin copyright">
				</div>
			</div>
		</div>
	</body>
</html>