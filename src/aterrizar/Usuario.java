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
	public ArrayList<Asiento> buscarArientos(String origen, String destino, Fecha salida, Fecha llegada){
		ArrayList<Asiento> busquedaActual = this.aterrizar.buscarAsientos(origen, destino, salida, llegada, this);
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
