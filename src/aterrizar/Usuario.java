package aterrizar;

import java.util.ArrayList;

import com.lanchita.excepciones.*;

public abstract class Usuario {
	protected String nombre;
	protected String apellido;
	protected String dni;
	protected SistemaDeComprasAterrizar aterrizar;
	protected ArrayList<Asiento> busquedasHistoricas;
	
	protected Usuario(String nombre, String apellido, String dni, SistemaDeComprasAterrizar aterrizar){
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.aterrizar = aterrizar; //Puede cambiar cuando hayan mas aerolineas!
		this.busquedasHistoricas = new ArrayList<Asiento>();
	}
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada){
		ArrayList<Asiento> busquedaActual = this.aterrizar.buscarAsientos(origen, destino, new Fecha(0,0,0), "PET", 
															(float)0, (float)100000, false, null, this);
		busquedasHistoricas.addAll(busquedaActual);
		return busquedaActual;
	}
	
	/**
	 * Este metodo buscar es el nuevo para la entrega 3
	 */
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha fecha, String clase,
											Float precioMin, Float precioMax, Boolean conReservados, Criterio unCriterio){
		ArrayList<Asiento> busquedaActual = this.aterrizar.buscarAsientos(origen, destino, fecha, clase, precioMin, 
																		precioMax, conReservados, unCriterio, this);
		busquedasHistoricas.addAll(busquedaActual);
		return busquedaActual;
	}
	
	public void comprar(Asiento unAsiento){
		
		try{
			aterrizar.comprar(unAsiento, this);
		} 
		catch(LanchitaException e){
			System.out.println(e.getMessage());			
		}
		
	}
	
	public abstract Boolean puedeVer(Asiento unAsiento);
	public abstract Float adicionalPrecio();
}
