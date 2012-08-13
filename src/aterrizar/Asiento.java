package aterrizar;

import java.sql.Time;

public class Asiento {
	private String codigo;
	private Float precio;
	private String clase;
	private String ubicacion;
	private String estado;
	private Time horaSalida;
	private Time horaLlegada;
	private Fecha fecha;
	private Fecha fechaLlegada;
	private AerolineaAdapter aerolinea;
	private String origen;
	private String destino;
	
	@SuppressWarnings("deprecation")
	public Asiento(String codigo, Float precio, String clase, String ubicacion, String estado, AerolineaAdapter aerolinea){
		super();
		this.codigo = codigo;
		this.setPrecio(precio);
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.aerolinea = aerolinea;
		this.horaLlegada = new Time(0,0,0);
		this.horaSalida = new Time(0,0,0);
		this.fecha = new Fecha(0,0,0);
	}
	
	public Asiento(String codigo, Float precio, String clase, String ubicacion, String estado, AerolineaAdapter aerolinea, Time horaLlegada, Time horaSalida, Fecha fecha){
		super();
		this.codigo = codigo;
		this.setPrecio(precio);
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.aerolinea = aerolinea;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.fecha = fecha;
	}
	
	public Asiento(String codigo, Float precio, String clase, String ubicacion, String estado, AerolineaAdapter aerolinea, 
								Time horaLlegada, Time horaSalida, Fecha fecha, String origen, String destino, Fecha fechaLlegada){
		super();
		this.codigo = codigo;
		this.setPrecio(precio);
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.aerolinea = aerolinea;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.fecha = fecha;
		this.origen = origen;
		this.destino = destino;
		this.fechaLlegada = fechaLlegada;
	}
	
	public Boolean esSuperOferta(){
		return 	( this.clase == "P" && this.precio < 8000 )  
				||
				(this.clase == "E" && this.precio < 4000 );
	}
	
	public int tiempoDeVuelo(){
		return 0;
	}
	
	public String vuelo(){
		String[] temp;
		temp = this.codigo.split("-");
		return temp[0];
	}
	
	public Boolean tieneEscala(){
		return false;
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
	public String getSalida() {
		return origen;
	}
	public String getLlegada() {
		return destino;
	}
	public Fecha getFecha() {
		return fecha; 
	}
	public Time getHoraLlegada() {
		return horaLlegada;
	}
	public Time getHoraSalida() {
		return horaSalida;
	}
	public Fecha getFechaLlegada() {
		return fechaLlegada;
	}
}
