package aterrizar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.model.Entity;
import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

import aterrizar.Flexible;
import aterrizar.AerolineaAdapter;
import aterrizar.Hora;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;

@Transactional
@Observable
@PersistentClass
public class Asiento extends Entity {

	private String origen;
	private String destino;
	private String horaLle;
	private String horaSal;
	private String fechaSal;
	private String fechaLle;
	private int numero;
	private String codigoDeVuelo;
	private Float precio;
	private String clase;
	private String ubicacion;
	private String estado;
	private int[] duracion;

	private AerolineaAdapter aerolinea;
	
	public Asiento(String origen, String destino, String fechaSal, String fechaLle, String horaSal, String horaLle,int numero, String codigoDeVuelo, Float precio, String clase, String ubicacion, String estado, AerolineaAdapter aerolinea) throws Exception{
		this.numero = numero;
		this.codigoDeVuelo = codigoDeVuelo;
		this.setPrecio(precio);
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.aerolinea = aerolinea;
		this.origen = origen;
		this.destino = destino;
		this.horaLle = horaLle;
		this.horaSal = horaSal;
		this.fechaLle = fechaLle;
		this.fechaSal = fechaSal;
		this.duracion = calcularDuracion(fechaSal,fechaLle,horaSal,horaLle);

	}
	
	public Boolean esSuperOferta(){
		return 	( this.clase == "P" && this.precio < 8000 )  
				||
				(this.clase == "E" && this.precio < 4000 );
	}
	
	//
	public Asiento(){
	}
	
	public Asiento(String fechaSal,int numero, String codigoDeVuelo, Float precio, String clase, String ubicacion,AerolineaAdapter aerolinea){
		this.fechaSal = fechaSal;
		this.numero = numero;
		this.codigoDeVuelo = codigoDeVuelo;
		this.precio = precio;
		this.clase = clase;
		this.ubicacion = ubicacion;
		this.aerolinea = aerolinea;
	}

	public int[] calcularDuracion(String fechaSal,String fechaLle,String horaSal,String horaLle) throws Exception
	{
		int[] diferenciaTiempo = new int[] {0,0};
		Flexible fechaSalidaFlex = new Flexible(fechaSal);
		Flexible fechaLlegadaFlex = new Flexible(fechaLle);
		Hora horaSalidaH = new Hora(horaSal);
		Hora horaLlegadaH = new Hora(horaLle);
		
		GregorianCalendar salida = new GregorianCalendar();
		GregorianCalendar llegada = new GregorianCalendar();
		salida.set(fechaSalidaFlex.getFecha().get(Calendar.YEAR),fechaSalidaFlex.getFecha().get(Calendar.MONTH)+1,fechaSalidaFlex.getFecha().get(Calendar.DAY_OF_MONTH),horaSalidaH.horas,horaSalidaH.minutos);
		llegada.set(fechaLlegadaFlex.getFecha().get(Calendar.YEAR),fechaLlegadaFlex.getFecha().get(Calendar.MONTH)+1,fechaLlegadaFlex.getFecha().get(Calendar.DAY_OF_MONTH),horaLlegadaH.horas,horaLlegadaH.minutos);
		
		long diferencia = llegada.getTime().getTime() - salida.getTime().getTime();
		long totalMinutos = Math.round(diferencia/60000) ;
		
		diferenciaTiempo[0] = Math.round(totalMinutos/60);
		diferenciaTiempo[1] = Math.round(totalMinutos%60);
		
		return diferenciaTiempo;
	}
	
	
	@PersistentField
	public int getNumero() {
		return numero;
	}
	@PersistentField
	public String getCodigoDeVuelo() {
		return codigoDeVuelo;
	}
	@PersistentField
	public String getClase() {
		return clase;
	}
	@PersistentField
	public String getUbicacion() {
		return ubicacion;
	}
	public String getEstado() {
		return estado;
	}
	@PersistentField
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	@PersistentField
	public AerolineaAdapter getAerolinea() {
		return aerolinea;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
	public String getHoraLle() {
		return horaLle;
	}
	public String getHoraSal() {
		return horaSal;
	}
	@PersistentField
	public String getFechaSal() {
		return fechaSal;
	}
	public String getFechaLle() {
		return fechaLle;
	}
	public int[] getDuracion() {
		return duracion;
	}
}
