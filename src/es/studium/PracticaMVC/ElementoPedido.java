package es.studium.PracticaMVC;

public class ElementoPedido
{
	private int idLibro;
	private int idAutor;
	private int cantidad;
	public ElementoPedido(int idLibro, int idAutor, int cantidad)
	{
		this.idLibro = idLibro;
		this.idAutor = idAutor;
		this.cantidad = cantidad;
	}
	
	public int getIdLibro()
	{
		return idLibro;
	}
	
	public void setIdLibro(int idLibro)
	{
		this.idLibro = idLibro;
	}
	
	public int getIdAutor()
	{
		return idAutor;
	}
	
	public void setIdAutor(int idAutor)
	{
		this.idAutor = idAutor;
	}
	
	public int getCantidad()
	{
		return cantidad;
	}
	
	public void setCantidad(int cantidad)
	{
		this.cantidad = cantidad;
	}
	
	public String getAutor()
	{
		return LibrosMVC.getAutor(idLibro);
	}
	
	public String getTitulo()
	{
		return LibrosMVC.getTitulo(idLibro);
	}
	
	public String getPrecio()
	{
		return LibrosMVC.getPrecio(idLibro);
	}
}