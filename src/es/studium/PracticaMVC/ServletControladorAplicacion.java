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
		// Indicar codificación de carácteres UTF-8
		request.setCharacterEncoding("UTF-8");
		String todo = request.getParameter("todo");
		String pedidoSeleccionado = "";
		String nextPage = "";
		if (todo == null) {
			nextPage = "/aplicaciongestion.jsp";
		}else if (todo.equals("altalibros")) {
			String tituloLibro = request.getParameter("nombre");
			System.out.println(tituloLibro);
			float precioLibro = Float.parseFloat(request.getParameter("precio"));
			String autorSeleccionado = request.getParameter("autores");
			String editorialSeleccionada = request.getParameter("editoriales");
			if (tituloLibro != "" && precioLibro != 0.0) {
				ModeloMVC.insertarlibros(tituloLibro, precioLibro);
				ModeloMVC.insertarDatosEscriben(autorSeleccionado);
				ModeloMVC.insertarDatosPertenecen(editorialSeleccionada);
				nextPage = "/gestionlibros.jsp";
			}
		}else if(todo.equals("consultalibros")){
			String libroSeleccionado = request.getParameter("libros");
			ModeloMVC.determinarLibro(libroSeleccionado);
			nextPage = "/modificacionlibros.jsp";
		}else if(todo.equals("modificacionlibros")) {
			String nombreLibro = request.getParameter("nombreLibro");
			float precioLibro = Float.parseFloat(request.getParameter("precioLibro"));
			ModeloMVC.modificarLibro(nombreLibro, precioLibro);
			nextPage = "/gestionlibros.jsp";
			ModeloMVC.borrarArrayList();
		}else if(todo.equals("consultapedidos")) {
			pedidoSeleccionado = request.getParameter("pedidos");
			ModeloMVC.detallesPedido(pedidoSeleccionado);
			nextPage = "/detallespedido.jsp";
		}else if(todo.equals("Cambiar Estado")) {
			ModeloMVC.cambiarEstadoPedido();
			ModeloMVC.actualizardatosPedido();
			nextPage = "/detallespedido.jsp";
		}
		
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}