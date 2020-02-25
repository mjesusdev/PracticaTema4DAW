<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="./css/estilologin.css">
		<title>Inicio de Sesión Carrito / AG</title>
	</head> 
	<body>
		<div class="container mt-2">
			<h1 class="text-black-50 text-center">Inicio de Sesión Carrito / AG</h1>
			<form name="login" action="login" method="POST">
				<div class="col-12 mt-4">
					<div class="form-group row justify-content-center"> 
						<label for="inputUser" class="col-sm-2 col-form-label">Usuario:</label>
						<div class="col-sm-3 col-mt-2">
							<input type="text" class="form-control" name="inputUsuario" placeholder="Escriba su usuario aquí" 
							required>
						</div>
					</div>
					<div class="form-group row justify-content-center">
						<label for="inputUser" class="col-sm-2 col-form-label">Contraseña:</label>
						<div class="col-sm-3 col-mt-2">
							<input type="password" class="form-control" name="inputPassword" placeholder="Escriba su contraseña aquí" 
							required>
						</div>
					</div>
					<div class="mx-auto mt-4" style="width: 280px;">
						<button type="submit" class="btn btn-success" id="iniciarcarrito">Iniciar Sesión</button>			
						<button type="reset" class="btn btn-warning">Resetear</button>		
					</div>
				</div>
			</form>
		</div>
	</body>
</html>