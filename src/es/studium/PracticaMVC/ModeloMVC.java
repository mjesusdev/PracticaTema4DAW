package es.studium.PracticaMVC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
	private static String[] titulos = new String[MAX_SIZE];
	private static Float[] precios = new Float[MAX_SIZE];
	private static ArrayList<String> informacionLibro = new ArrayList<String>();
	private static String libroEscogido = "";
	
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
	
	public static ArrayList<String> consultarLibros(){
		Connection conn = null;
		Statement stmt = null;
		
		ArrayList<String> datosLibros = new ArrayList<String>();
		datosLibros.add("");
		
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
			String sentenciaSQL = "SELECT * FROM libros, escriben, autores WHERE libros.idLibro = escriben.idLibroFK AND escriben.idAutorFK = autores.idAutor;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idLibro = rs.getInt("idLibro");
				String tituloLibro = rs.getString("tituloLibro");
				String nombreAutor = rs.getString("nombreAutor");
				String apellidosAutor = rs.getString("apellidosAutor");
				float precioLibro = rs.getFloat("precioLibro");
				// Añadir datos los datos al ArrayList
				datosLibros.add(idLibro + " - " + tituloLibro + " | " + nombreAutor + " " + apellidosAutor + " | " + precioLibro + "€");
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
		return datosLibros;
	}
	
	public static ArrayList<String> determinarLibro(String libroSeleccionado) {
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
			e.printStackTrace();
		}

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "SELECT tituloLibro, precioLibro FROM libros WHERE idLibro = '"+libroSeleccionado+"';";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				String tituloLibro = rs.getString("tituloLibro");
				float precioLibro = rs.getFloat("precioLibro");
				informacionLibro.add(tituloLibro);
				informacionLibro.add(precioLibro + "");
			}
			informacionLibro.add(libroSeleccionado);
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
		return informacionLibro;
	}
	
	public static ArrayList<String> saberLibro() {
		return informacionLibro;
	}
	
	public static void modificarLibro(String tituloLibro, float precioLibro) {
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
			e.printStackTrace();
		}

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "UPDATE libros SET tituloLibro = '"+tituloLibro+"', precioLibro = " + precioLibro + " WHERE idLibro = "+ saberLibro().get(2) +";";
			System.out.println(sentenciaSQL);
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
	
	public static void borrarArrayList() {
		informacionLibro.clear();
		System.out.println(informacionLibro);
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