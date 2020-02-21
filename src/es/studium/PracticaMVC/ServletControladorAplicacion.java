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
		String todo = request.getParameter("todo");
		String nextPage = "";
		if (todo.equals("alta")) {
			System.out.println("entra en el alta");
			String tituloLibro = request.getParameter("nombre");
			float precioLibro = Float.parseFloat(request.getParameter("precio"));
			if (tituloLibro != "" && precioLibro != 0.0) {
				ModeloMVC.insertarlibros(tituloLibro, precioLibro);
				System.out.println("hace el insert");
				nextPage = "/gestionlibros.jsp";
			}
		}else{
			System.out.println(todo);
			if (todo.equals("consulta")) {
				String libroSeleccionado = request.getParameter("libros");
				ModeloMVC.determinarLibro(libroSeleccionado);
				nextPage = "/modificacionlibros.jsp";
				System.out.println(todo);
			}else if(todo.equals("modificacion")) {
				String nombreLibro = request.getParameter("nombreLibro");
				float precioLibro = Float.parseFloat(request.getParameter("precioLibro"));
				ModeloMVC.modificarLibro(nombreLibro, precioLibro);
				nextPage = "/gestionlibros.jsp";
				ModeloMVC.borrarArrayList();
			}
		}
		System.out.println(todo);
		//nextPage = "/gestionlibros.jsp";
		
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}