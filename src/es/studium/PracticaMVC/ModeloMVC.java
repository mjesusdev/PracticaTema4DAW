package es.studium.PracticaMVC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
	private static ArrayList<String> informacionAutores = new ArrayList<String>();
	private static ArrayList<String> informacionEditoriales = new ArrayList<String>();
	private static ArrayList<String> detallesPedido = new ArrayList<String>();
	
	public static int comprobarDatos(String nombreUsuario, String passUsuario) throws ServletException {
		Connection conn = null;
		Statement stmt = null;
		int tipoUsuario = 0;
		int datoscorrectos = 0;
		
		try
		{
			InitialContext ctx = new InitialContext();
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
			
			if (tipoUsuario == 1) {
				datoscorrectos = 1;
			}else if(tipoUsuario == 2){
				datoscorrectos = 2;
			}else{
				datoscorrectos = 0;
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

	public static ArrayList<String> insertarAutores() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> informacionAutores = new ArrayList<String>();
		informacionAutores.add("");
		
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
			String sentenciaSQL = "SELECT * FROM autores;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idAutor = rs.getInt("idAutor");
				String nombreAutor  = rs.getString("nombreAutor");
				String apellidosAutor  = rs.getString("apellidosAutor");
				Date fechaNacimientoAutor = rs.getDate("fechaNacimientoAutor");
				String fechaES = new SimpleDateFormat("dd-MM-yyyy").format(fechaNacimientoAutor);
				informacionAutores.add(idAutor + " - " + nombreAutor + " " + apellidosAutor + " | " + fechaES);
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
		return informacionAutores;
	}
	
	public static ArrayList<String> insertarEditoriales() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> informacionEditoriales = new ArrayList<String>();
		informacionEditoriales.add("");
		
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
			String sentenciaSQL = "SELECT * FROM editoriales;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idEditorial = rs.getInt("idEditorial");
				String nombreEditorial  = rs.getString("nombreEditorial");
				String domicilioEditorial  = rs.getString("domicilioEditorial");
				informacionEditoriales.add(idEditorial + " - " + nombreEditorial + " " + domicilioEditorial);
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
		return informacionEditoriales;
	}
	
	public static int sacarUltimoLibro() {
		Connection conn = null;
		Statement stmt = null;
		int idLibromax = 0;
		
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
			String sentenciaSQL = "SELECT MAX(idLibro) AS 'idLibromax' FROM libros;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idLibromax = rs.getInt("idLibromax");
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
		return idLibromax;
	}
	
	public static void insertarDatosEscriben(String autorSeleccionado) {
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
			String sentenciaSQL = "INSERT INTO escriben VALUES(NULL, " + sacarUltimoLibro() + ", " + autorSeleccionado + ");";
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
	
	public static void insertarDatosPertenecen(String editorialSeleccionada) {
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
			String sentenciaSQL = "INSERT INTO pertenecen VALUES(NULL, " + sacarUltimoLibro() + ", " + editorialSeleccionada + ");";
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
	
	public static int identificarAutor() {
		Connection conn = null;
		Statement stmt = null;
		
		int idAutor = 0;
		
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
			String sentenciaSQL = "SELECT idAutorFK FROM escriben WHERE idLibroFK = '"+informacionLibro.get(2)+"';";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idAutor = rs.getInt("idAutorFK");
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
		return idAutor;
	}
	
	public static int identificarEditorial() {
		Connection conn = null;
		Statement stmt = null;
		int idEditorial = 0;
		
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
			String sentenciaSQL = "SELECT idEditorialFK FROM pertenecen WHERE idLibroFK = '"+informacionLibro.get(2)+"';";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idEditorial = rs.getInt("idEditorialFK");
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
		return idEditorial;
	}
	
	public static ArrayList<String> insertarAutoresModificacion() {
		Connection conn = null;
		Statement stmt = null;
		informacionAutores = new ArrayList<String>();
		informacionAutores.add("");
		
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
			String sentenciaSQL = "SELECT DISTINCT idAutor, nombreAutor, apellidosAutor, fechaNacimientoAutor FROM escriben, autores" + 
					" WHERE escriben.idAutorFK = autores.idAutor";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idAutor = rs.getInt("idAutor");
				String nombreAutor  = rs.getString("nombreAutor");
				String apellidosAutor  = rs.getString("apellidosAutor");
				Date fechaNacimientoAutor = rs.getDate("fechaNacimientoAutor");
				String fechaES = new SimpleDateFormat("dd-MM-yyyy").format(fechaNacimientoAutor);
				informacionAutores.add(idAutor + " - " + nombreAutor + " " + apellidosAutor + " | " + fechaES);
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
		return informacionAutores;
	}
	
	public static ArrayList<String> insertarEditorialesModificacion() {
		Connection conn = null;
		Statement stmt = null;
		informacionEditoriales = new ArrayList<String>();
		informacionEditoriales.add("");
		
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
			String sentenciaSQL = "SELECT DISTINCT idEditorial, nombreEditorial, domicilioEditorial FROM pertenecen, editoriales" + 
					" WHERE pertenecen.idEditorialFK = editoriales.idEditorial";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idEditorial = rs.getInt("idEditorial");
				String nombreEditorial  = rs.getString("nombreEditorial");
				String domicilioEditorial  = rs.getString("domicilioEditorial");
				informacionEditoriales.add(idEditorial + " - " + nombreEditorial + " " + domicilioEditorial);
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
		return informacionEditoriales;
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
	
	public static int sacarIdRelacionAutoresLibros() {
		Connection conn = null;
		Statement stmt = null;
		int idEscriben = 0;
		
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
			String sentenciaSQL = "SELECT idEscriben FROM escriben WHERE idLibroFK = " +saberLibro().get(2)+ ";";
			System.out.println(sentenciaSQL);
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idEscriben = rs.getInt("idEscriben");
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
		return idEscriben;
	}
	
	public static int sacarIdRelacionEditorialesLibros() {
		Connection conn = null;
		Statement stmt = null;
		int idPertenecen = 0;
		
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
			String sentenciaSQL = "SELECT idPertenecen FROM pertenecen WHERE idLibroFK = " +saberLibro().get(2)+ ";";
			System.out.println(sentenciaSQL);
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				idPertenecen = rs.getInt("idPertenecen");
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
		return idPertenecen;
	}
	
	public static void modificarAutorLibro(String idAutor) {
		Connection conn = null;
		Statement stmt = null;
		
		int autorSeleccionado = Integer.parseInt(idAutor);
		
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
			String sentenciaSQL = "UPDATE escriben SET idAutorFK = " +autorSeleccionado+ " WHERE idEscriben = " +sacarIdRelacionAutoresLibros()+ ";";
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
	
	public static void modificarEditorialLibro(String idEditorial) {
		Connection conn = null;
		Statement stmt = null;
		
		int editorialSeleccionada = Integer.parseInt(idEditorial);
		
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
			String sentenciaSQL = "UPDATE pertenecen SET idAutorFK = " +editorialSeleccionada+ " WHERE idEscriben = " +sacarIdRelacionEditorialesLibros()+ ";";
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
	
	public static void borrarArrayLists() {
		informacionLibro.clear();
		informacionAutores.clear();
		informacionEditoriales.clear();
	}
	
	public static ArrayList<String> consultarPedidos(){
		Connection conn = null;
		Statement stmt = null;
		
		ArrayList<String> datosPedidos = new ArrayList<String>();
		datosPedidos.add("");
		
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
			String sentenciaSQL = "SELECT * FROM pedidos;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idPedido = rs.getInt("idPedido");
				Float totalPedido  = rs.getFloat("totalPedido");
				Date fechaPedido = rs.getDate("fechaPedido");
				String fechaES = new SimpleDateFormat("dd-MM-yyyy").format(fechaPedido);
				Time horaPedido = rs.getTime("horaPedido");
				datosPedidos.add("Pedido Nº " + idPedido + " | " + totalPedido + "€" + " | " + fechaES + " | " + horaPedido);
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
		return datosPedidos;
	}
	
	public static ArrayList<String> detallesPedido(String pedidoSeleccionado) {
		Connection conn = null;
		Statement stmt = null;
		int idPedidoSeleccionado = Integer.parseInt(pedidoSeleccionado);
		detallesPedido.add("");
		
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
			String sentenciaSQL = "SELECT * FROM pedidos, lineapedidos WHERE idPedido = 3 AND pedidos.idPedido = lineapedidos.idPedidoFK;";
			ResultSet rs = stmt.executeQuery(sentenciaSQL);
			while (rs.next()) 
			{
				int idPedido = rs.getInt("idPedido");
				Float totalPedido  = rs.getFloat("totalPedido");
				Date fechaPedido = rs.getDate("fechaPedido");
				String fechaES = new SimpleDateFormat("dd-MM-yyyy").format(fechaPedido);
				Time horaPedido = rs.getTime("horaPedido");
				int idLibroFK = rs.getInt("idLibroFK");
				int cantidad = rs.getInt("cantidad");
				int estadoPedido = rs.getInt("estadoPedido");

				String[] opciones = {"No Enviado", "Enviado"};
				
				// Añadir datos los datos al ArrayList
				detallesPedido.add(idPedido + "");
				detallesPedido.add(idLibroFK + "");
				detallesPedido.add(cantidad + "");
				detallesPedido.add(totalPedido + "");
				detallesPedido.add(fechaES + " | " + horaPedido);
				detallesPedido.add(opciones[(int)estadoPedido] + "");
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
		return detallesPedido;
	}
	
	public static ArrayList<String> insertarDetallesPedido(){
		return detallesPedido;
	}
	
	public static void cambiarEstadoPedido() {
		Connection conn = null;
		Statement stmt = null;
		
		int estadoPedido = 0;
		if (detallesPedido.get(6).equals("No Enviado")) {
			estadoPedido = 1;
		}else{
			estadoPedido = 0;
		}
		
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
			String sentenciaSQL = "UPDATE pedidos SET estadoPedido = " + estadoPedido + " WHERE idPedido = " + detallesPedido.get(1)+ ";";
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
	
	public static void actualizardatosPedido(){
		String idPedido = detallesPedido.get(1);
		detallesPedido.clear();
		detallesPedido(idPedido);
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