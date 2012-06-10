package aterrizar;

public class Asiento {
	private String codigo;
	private Float precio;
	private String clase;
	private String ubicacion;
	private String estado;
	private AerolineaAdapter aerolinea;
	
	public Asiento(String codigo, Float precio, String clase, String ubicacion, String estado, AerolineaAdapter aerolinea){
		super();
		this.codigo = codigo;
		this.setPrecio(precio);
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.aerolinea = aerolinea;
	}
	
	public Boolean esSuperOferta(){
		return 	( this.clase == "P" && this.precio < 8000 )  
				||
				(this.clase == "E" && this.precio < 4000 );
	}
	
	
	/**
	 * Getters y Setters:
	 */
	public String getCodigo() {
		return codigo;
	}
	public String getClase() {
		return clase;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public String getEstado() {
		return estado;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public AerolineaAdapter getAerolinea() {
		return aerolinea;
	}
}
