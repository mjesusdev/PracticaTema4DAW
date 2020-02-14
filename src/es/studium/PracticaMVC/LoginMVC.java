package es.studium.PracticaMVC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class LoginMVC {
	public static DataSource pool;
	
	public LoginMVC() {
		pool = PooldeConexiones.conectar();
	}

	public static int comprobarDatos(String nombreUsuario, String passUsuario) throws ServletException {
		Connection conn = null;
		Statement stmt = null;
		int tipoUsuario = 0;
		int datoscorrectos = 0;
		
		try
		{
			// Crea un contexto para poder luego buscar el recurso DataSource
			InitialContext ctx = new InitialContext();
			// Busca el recurso DataSource en el contexto
			pool = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql_tiendalibros_practica");
			if(pool == null)
			{
				throw new ServletException("DataSource desconocida 'mysql_tiendalibros_practica'");
			}
		}
		catch(NamingException ex){}

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "SELECT tipoUsuario FROM usuarios WHERE "
					+ "nombreUsuario = '"+nombreUsuario+"' AND "
					+ "passUsuario = MD5('"+passUsuario+"')";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) {
				tipoUsuario = rs.getInt("tipoUsuario");
			}
			if (tipoUsuario == 0) {
				datoscorrectos = 0;
			}else if(tipoUsuario == 1){
				datoscorrectos = 1;
			}else{
				datoscorrectos = 2;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return datoscorrectos;
	}
}