package aterrizar;

import java.util.ArrayList;

public class Busqueda {
	private SistemaDeComprasAterrizar aterrizar;
	private Usuario usuario;
	private String origen;
	private String destino;
	private Fecha fecha;
	private String clase;
	private String ubicacion;
	private Float precioMin;
	private Float precioMax;
	private Boolean conReservados;
	private Criterio criterio;
	private Boolean conEscalas;
	
	public Busqueda(String origen, String destino, Fecha fecha, String clase,
					String ubicacion, Float precioMin, Float precioMax,
					Boolean conReservados, Criterio unCriterio, Boolean conEscalas,
					Usuario usuario){
		this.aterrizar = SistemaDeComprasAterrizar.getInstance();
		this.setUsuario(usuario);
		this.setOrigen(origen);
		this.setDestino(destino);
		this.setFecha(fecha);
		this.setClase(clase);
		this.setUbicacion(ubicacion);
		this.setPrecioMin(precioMin);
		this.setPrecioMax(precioMax);
		this.setConReservados(conReservados);
		this.setCriterio(unCriterio);
		this.setConEscalas(conEscalas);
		
	}
	
	public ArrayList<Asiento> buscar(){
		ArrayList<Asiento> asientos = this.aterrizar.buscarAsientos(getOrigen(), getDestino(), getFecha(), getClase(), getUbicacion(), getPrecioMin(), 
				getPrecioMax(), getConReservados(), getCriterio(), getConEscalas(), getUsuario());
		return asientos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Float getPrecioMin() {
		return precioMin;
	}

	public void setPrecioMin(Float precioMin) {
		this.precioMin = precioMin;
	}

	public Float getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(Float precioMax) {
		this.precioMax = precioMax;
	}

	public Boolean getConReservados() {
		return conReservados;
	}

	public void setConReservados(Boolean conReservados) {
		this.conReservados = conReservados;
	}

	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	public Boolean getConEscalas() {
		return conEscalas;
	}

	public void setConEscalas(Boolean conEscalas) {
		this.conEscalas = conEscalas;
	}
}
