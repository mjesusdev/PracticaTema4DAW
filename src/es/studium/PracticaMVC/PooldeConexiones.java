package es.studium.PracticaMVC;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class PooldeConexiones extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static DataSource pool;

	public PooldeConexiones(){
		super();
	}

	public void init(ServletConfig config) throws ServletException{
		try{
			// Crea un contexto para poder luego buscar el recurso DataSource
			InitialContext context = new InitialContext();
			// Busca el recurso DataSource en el contexto
			pool = (DataSource)context.lookup("java:comp/env/jdbc/mysql_tiendalibros_practica");
			if(pool == null){
				throw new ServletException("DataSource desconocida 'mysql_tiendalibros_practica'");
			}
		}
		catch(NamingException ex){}
	}

	public static DataSource conectar() {
		return pool;
	}
}