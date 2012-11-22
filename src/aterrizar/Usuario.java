package aterrizar;

import java.util.ArrayList;
import java.util.Iterator;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

import aterrizar.Asiento;
import aterrizar.SistemaDeComprasAterrizar;
import aterrizar.Filtrar;
import aterrizar.Ordenar;
import aterrizar.Viaje;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;


@Transactional
@Observable
@PersistentClass
public abstract class Usuario extends Entity{
	
	protected String nombre;
	protected String apellido;
	protected String dni;
	protected ArrayList<Asiento> asientosReservados = new ArrayList<Asiento>();
	protected ArrayList<Asiento> asientosComprados = new ArrayList<Asiento>();
	
	
	protected SistemaDeComprasAterrizar sistema;
	
	protected ArrayList<Asiento> historialDeBusquedas;
	
	public Usuario(){
	}
	
	public Usuario(String nombre, String apellido, String dni)
	{
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.sistema = SistemaDeComprasAterrizar.getInstance();
		this.historialDeBusquedas = new ArrayList<Asiento>();
	}
	
	public ArrayList<Viaje> buscarAsientosDisponibles(String origen, String destino, String salida, String horaSalida, String llegada, String horaLlegada,Integer cantEscalas, Ordenar orden, Filtrar filtros) throws Exception
	{
		ArrayList<Viaje> busqueda = this.sistema.buscarAsientos(origen, destino, salida, horaSalida, llegada, horaLlegada,cantEscalas, this,orden,filtros);
		
		Iterator<Viaje> viajeIterator = busqueda.iterator();
		while(viajeIterator.hasNext())
		{
			Viaje viaje = viajeIterator.next();
			
			if(viaje.getAsientoUno()!=null)
				this.historialDeBusquedas.add(viaje.getAsientoUno());
			
			if(viaje.getAsientoDos()!=null)
				this.historialDeBusquedas.add(viaje.getAsientoDos());
			
			if(viaje.getAsientoTres()!=null)
				this.historialDeBusquedas.add(viaje.getAsientoTres());
			
		}
		return busqueda;
	}
	@Relation
	public void comprarAsiento(Asiento unAsiento) throws Exception{
			sistema.comprar(unAsiento, this);
			this.asientosComprados.add(unAsiento);
	}
	@Relation
	public void reservarAsiento(Asiento unAsiento){
			sistema.reservar(unAsiento, this);
			this.asientosReservados.add(unAsiento);
		return;
	}
	
	
	public abstract Boolean puedeListar(Asiento unAsiento);
	public abstract float impuestoAdicional();

	public String getNombre() {
		return this.nombre;
	}
	@PersistentField
	public ArrayList<Asiento> getAsientosReservados() {
		return asientosReservados;
	}
	@PersistentField
	public ArrayList<Asiento> getAsientosComprados() {
		return asientosComprados;
	}

	public void setAsientosComprados(ArrayList<Asiento> asientosComprados) {
		this.asientosComprados = asientosComprados;
	}

	public void setAsientosReservados(ArrayList<Asiento> asientosReservados) {
		this.asientosReservados = asientosReservados;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void agregarAsiento(Asiento unAsiento){
		this.asientosComprados.add(unAsiento);
		this.asientosReservados.add(unAsiento);
	}
}
