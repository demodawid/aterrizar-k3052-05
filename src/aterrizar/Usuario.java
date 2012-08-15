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
	
	/**
	 * M�todo viejo, NO UTILIZAR!
	 */
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada){
		ArrayList<Asiento> busquedaActual = this.aterrizar.buscarAsientos(origen, destino, new Fecha(0,0,0), "PET", "",
															(float)0, (float)0, false, new SinOrden(), this);
		busquedasHistoricas.addAll(busquedaActual);
		return busquedaActual;
	}
	
	/**
	 * Este metodo buscar es el nuevo para la entrega 3.
	 * Par�metros obligatorios: Origen, Destino, Fecha
	 * Par�metros opcionales: clase, ubicaci�n, (si no se usa: "")
	 * 						  precioMin, precioMax (si no se usa: 0)
	 * 						  conReservados (si no se usa: true)
	 * 						  unCriterio	(si no se usa: new SinOrden() )
	 */
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha fecha, String clase, String ubicacion,
											Float precioMin, Float precioMax, Boolean conReservados, Criterio unCriterio,
											Boolean conEscalas){
		ArrayList<Asiento> busquedaActual = this.aterrizar.buscarAsientos(origen, destino, fecha, clase, ubicacion, precioMin, 
																		precioMax, conReservados, unCriterio, conEscalas, this);
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
	public abstract void reservar(Asiento unAsiento);
}
