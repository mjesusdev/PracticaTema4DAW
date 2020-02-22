<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*,es.studium.PracticaMVC.*"%>
<!DOCTYPE html>
	<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<title>Carrito de Libros</title>
	</head>
	<body>
		<div class="container mt-2">
			<div class="row justify-content-center">
				<div class="col-4">
					<h1 class="text-black-50">Carrito de Libros</h1>
				</div>
			</div>
			<form name="AgregarForm" action="shopping" method="POST">
				<div class="row">	
					<div class="col-12 mt-4">
						<div class="form-group row justify-content-center"> 
							<label for="inputUser" class="col-sm-2 col-form-label">Elija un libro</label>
							<div class="col-md-5">
								<input type="hidden" name="todo" value="add">
								<select class="form-control" name="idLibro">
									<%
										// Scriplet 1: Carga los libros en el SELECT
										for (int i = 0; i < LibrosMVC.tamano(); i++) {
											out.println("<option value='" + i + "'>");
											out.println(LibrosMVC.getTitulo(i) + " | " + LibrosMVC.getAutor(i) + " | " + LibrosMVC.getPrecio(i));
											out.println("</option>");
										}
									%>
								</select>
							</div>
						</div>
						<div class="form-group row justify-content-center">
							<label for="inputUser" class="col-sm-2 col-form-label">Cantidad</label>
							<div class="col-md-5 col-mt-2">
								<input type="number" class="form-control" name="cantidad" size="10" value="1">
							</div>
						</div>
						<div class="row justify-content-center">
							<div class="col-2">
								<input type="submit" class="btn btn-success" value="Añadir a la cesta">
							</div>
						</div>
					</div>
				</div>
			</form>
		<hr />
		<div class="row justify-content-center">
			<%
				// Scriplet 2: Chequea el contenido de la cesta
				ArrayList<ElementoPedido> cesta = (ArrayList<ElementoPedido>) session.getAttribute("carrito");
				if (cesta != null && cesta.size() > 0) {
			%>
			<div class="col-2">
				<p>
					<strong>Tu cesta contiene:</strong>
				</p>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-12 mt-2">
				<table class="table table-striped table-dark" border="1">
					<tr>
						<th>Título</th>
						<th>Autor</th>
						<th>Precio</th>
						<th>Cantidad</th>
						<th>&nbsp;</th>
					</tr>
					<%
						// Scriplet 3: Muestra los libros del carrito
						for (int i = 0; i < cesta.size(); i++) {
							ElementoPedido elementoPedido = cesta.get(i);
					%>
					<tr>
						<form name="borrarForm" action="shopping" method="POST">
							<input type="hidden" name="todo" value="remove">
							<input type="hidden" name="indiceElemento" value="<%=i%>">
							<td><%=elementoPedido.getTitulo()%></td>
							<td><%=elementoPedido.getAutor()%></td>
							<td><%=elementoPedido.getPrecio()%> €</td>
							<td><%=elementoPedido.getCantidad()%></td>
							<td><input type="submit" class="btn btn-danger" value="Eliminar de la cesta"></td>
						</form>
					</tr>
					<%
						}
					%>
				</table>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-2">
				<form name="checkoutForm" action="shopping" method="POST">
					<input type="hidden" name="todo" value="checkout"> 
					<input type="submit" class="btn btn-success" value="Confirmar compra">
				</form>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	</body>
</html>