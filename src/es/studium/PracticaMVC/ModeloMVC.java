package es.studium.PracticaMVC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class ModeloMVC {
	public static DataSource pool;
	
	public ModeloMVC() {
		pool = PooldeConexiones.conectar();
	}

	private static final int MAX_SIZE = sacarLimiteLibros();
	private static int[] id = new int[MAX_SIZE];
	private static String[] titulos = new String[MAX_SIZE];
	private static Float[] precios = new Float[MAX_SIZE];
	
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
	
	public static void insertarlibros(String tituloLibro, float precioLibro) throws ServletException {
		Connection conn = null;
		Statement stmt = null;
		
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
			String sentenciaSQL = "INSERT INTO libros VALUES(NULL, '"+tituloLibro+"' , '"+precioLibro+"');";
			stmt.executeUpdate(sentenciaSQL);
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
	}
	
	public static int sacarLimiteLibros() {
		Connection conn = null;
		Statement stmt = null;
		int idLibro = 0;
		
		try
		{
			InitialContext ctx = new InitialContext();
			pool = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql_tiendalibros_practica");
			if(pool == null)
			{
				throw new ServletException("DataSource desconocida 'mysql_tiendalibros_practica'");
			}
		}
		catch(NamingException ex){} catch (ServletException e) {
			e.printStackTrace();
		}

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "SELECT * FROM libros;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idLibro = rs.getInt("idLibro");
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
		return idLibro;
	}
	
	public static void consultarLibros(){
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			InitialContext ctx = new InitialContext();
			pool = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql_tiendalibros_practica");
			if(pool == null)
			{
				throw new ServletException("DataSource desconocida 'mysql_tiendalibros_practica'");
			}
		}
		catch(NamingException ex){} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "SELECT * FROM libros;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			int contador = 0;
			while (rs.next()) 
			{
				id[contador] = rs.getInt("idLibro");
				titulos[contador] = rs.getString("tituloLibro");
				precios[contador] = rs.getFloat("precioLibro");
				contador++;
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
	}
	
	public static int tamano()
	{
		return titulos.length;
	}

	public static String getTitulo(int idLibro)
	{
		return titulos[idLibro];
	}

	public static float getPrecio(int idLibro)
	{
		return precios[idLibro];
	}
}