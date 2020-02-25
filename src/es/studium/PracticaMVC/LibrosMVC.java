package es.studium.PracticaMVC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class LibrosMVC
{
	public static DataSource pool;
	
	public LibrosMVC() {
		pool = PooldeConexiones.conectar();
	}
	
	private static final int MAX_SIZE = sacarLimiteRegistros();
	private static int[] ids = new int[MAX_SIZE];
	private static String[] titulos = new String[MAX_SIZE];
	private static String[] autores = new String[MAX_SIZE];
	private static String[] precios = new String[MAX_SIZE];
	
	/*
	 * Método para sacar el número de registros que tiene la tabla de libros
	 */
	public static int sacarLimiteRegistros() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = pool.getConnection();
			stmt = conn.createStatement();
			String sentenciaSQL = "SELECT * FROM libros";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while(rs.next())
			{
				idLibro = rs.getInt("idLibro");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return idLibro;
	}
	
	public static void cargarDatos()
	{
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
			String sentenciaSQL = "SELECT * FROM libros, escriben, autores WHERE libros.idLibro = escriben.idLibroFK AND escriben.idAutorFK = autores.idAutor";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			int contador = 0;
			while(rs.next())
			{
				ids[contador] = rs.getInt("idLibro");
				titulos[contador] = rs.getString("tituloLibro");
				precios[contador] = rs.getString("precioLibro");
				// Sacar los datos del autor
				autores[contador] = rs.getString("nombreAutor") + " ";
				autores[contador] += rs.getString("apellidosAutor");
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
	
	public static void insertarPedido(float totalPedido) {
		Connection conn = null;
		Statement stmt = null;
		Calendar calendar = Calendar.getInstance();
		String dia = Integer.toString(calendar.get(Calendar.DATE));
		String mes = Integer.toString(calendar.get(Calendar.MONTH) +1);
		String anyo = Integer.toString(calendar.get(Calendar.YEAR));
		String horaPedido = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		String minutosPedido = Integer.toString(calendar.get(Calendar.MINUTE));
		String fechaPedido = anyo + "-" + mes + "-" + dia;
		String horaCompletaPedido = horaPedido + ":" + minutosPedido;
		
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
			String sentenciaSQL = "INSERT INTO pedidos VALUES(NULL, '"+totalPedido+"', '"+fechaPedido+"', '"+horaCompletaPedido+"', 0, 1);";
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
	
	public static int sacaridPedido() {
		Connection conn = null;
		Statement stmt = null;
		int idPedido = 0;
		
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
			String sentenciaSQL = "SELECT idPedido FROM pedidos;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while(rs.next()) {
				idPedido = rs.getInt("idPedido");
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
		return idPedido;
	}
	
	public static void insertarLineaPedidos(Integer idLibro, int cantidadTotalOrdenada) {
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
			String sentenciaSQL = "INSERT INTO lineapedidos VALUES(NULL, "+idLibro+", "+sacaridPedido()+", "+cantidadTotalOrdenada+");";
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
	
	public static int tamano()
	{
		return titulos.length;
	}

	public static String getTitulo(int idLibro)
	{
		return titulos[idLibro];
	}

	public static String getAutor(int idLibro)
	{
		return autores[idLibro];
	}

	public static String getPrecio(int idLibro)
	{
		return precios[idLibro];
	}
}