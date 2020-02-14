package es.studium.PracticaMVC;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class ServletControladorLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Guardar en variables el usuario y contraseña enviados por login.jsp
		String nombreUsuario = request.getParameter("inputUsuario");
		String passUsuario = request.getParameter("inputPassword");
		String nextPage = "";
		LoginMVC.comprobarDatos(nombreUsuario, passUsuario);
		
		if (LoginMVC.comprobarDatos(nombreUsuario, passUsuario) == 0){
			// Redirigimos al programa de gestión
			nextPage = "/aplicaciongestion.jsp";
		}else if(LoginMVC.comprobarDatos(nombreUsuario, passUsuario) == 1){
			// Redirigimos a la página del carrito
			nextPage = "/shopping";
		}else{
			// Redirigimos a la página de inicio de sesión
			nextPage = "/login.jsp";
		}
		
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}