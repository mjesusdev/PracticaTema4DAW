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

@WebServlet("/aplicacion")
public class ServletControladorAplicacion extends HttpServlet 
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
		String tituloLibro = request.getParameter("nombre");
		float precioLibro = Float.parseFloat(request.getParameter("precio"));
		String altalibros = request.getParameter("altalibros");
		String libros = request.getParameter("libros");
		String nextPage = "";
		if (altalibros == null) {
			if (tituloLibro != "" && precioLibro != 0.0) {
				ModeloMVC.insertarlibros(tituloLibro, precioLibro);
				nextPage = "/gestionlibros.jsp";
			}
		}else if(libros.equals("consulta")) {
			ModeloMVC.consultarLibros();
		}
		
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}