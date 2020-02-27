<%-- Página de confirmación del pedido --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*,es.studium.PracticaMVC.*"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/estilo.css" type="text/css">
		<title>Confirmación de Pedido</title>
	</head>
	<body>
		<div class="container mt-2">
			<div class="row justify-content-center">
				<div class="col-6 mt-2">
					<h1 class="text-black-50">Confirmación de Pedido</h1>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-4 mt-2">
					<p class="font-weight-bolder">Vas a comprar los siguientes libros:</p>
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
						</tr>
						<%
							// Muestra los elementos del carrito
							ArrayList<ElementoPedido> cesta = (ArrayList<ElementoPedido>) session.getAttribute("carrito");
							for (ElementoPedido item : cesta) {
						%>
						<tr>
							<td><%=item.getTitulo()%></td>
							<td><%=item.getAutor()%></td>
							<td><%=item.getPrecio()%> €</td>
							<td><%=item.getCantidad()%></td>
						</tr>
						<%
							}
							session.invalidate();
						%>
						<tr>
							<th class="text-warning" colspan="2">Total</th>
							<th class="text-warning"><%=request.getAttribute("precioTotal")%> €</th>
							<th><%=request.getAttribute("cantidadTotal")%></th>
						</tr>
					</table>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-4">
					<a class="btn btn-info" data-toggle="collapse" href="shopping" role="button" aria-expanded="false" aria-controls="collapseExample">Pulsa aquí para comprar más libros</a>
				</div>
			</div>
		</div>
	</body>
</html>