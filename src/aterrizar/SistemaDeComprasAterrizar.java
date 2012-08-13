package aterrizar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Sistema de compras Aterrizar. Aqu� se agregar�n las nuevas aerol�neas con las
 * que se firme contrato. Esta clase se encargar� de interactuar con todas las 
 * aerol�neas de forma adecuada. El usuario interact�a solo con este facade
 * de nuestro sistema, y la comunicacion con las aerol�neas es transparente para el.
 * Deber�a existir solo una implementaci�n de esta clase.
 *
 */
public class SistemaDeComprasAterrizar {
	private static final SistemaDeComprasAterrizar INSTANCE = new SistemaDeComprasAterrizar();
	
	ArrayList<AerolineaAdapter> aerolineas;
	
	public static SistemaDeComprasAterrizar getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Constructor
	 */
	private SistemaDeComprasAterrizar(){
		this.aerolineas = new ArrayList<AerolineaAdapter>();
		this.aerolineas.add(new AerolineaLanchitaAdapter());
		
	}
	
	public void setAerolineas(ArrayList<AerolineaAdapter> aerolineas){
		this.aerolineas = aerolineas;
	}
	
	/**
	 * Busca asientos en todas las aerolineas que conoce para un determinado usuario,
	 * con origen, destino, fecha de salida y fecha de llegada.
	 */
	
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, String clase,
											Float precioMin, Float precioMax, Boolean conReservados, Criterio uncriterio, Usuario usuario) {
		
		ArrayList<Asiento> asientos = new ArrayList<Asiento>();
		for(AerolineaAdapter unaAerolinea: aerolineas){
			ArrayList<Asiento> asientosAct = unaAerolinea.buscarAsientos(origen, destino, salida, new Fecha(0,0,0), usuario);
			asientosAct = this.filtrarAsientos(asientosAct,clase, precioMin, precioMax, conReservados);
			asientos.addAll(asientosAct);
		}
		//Aca el ordenamiento
		Collections.sort(asientos, uncriterio);
		return asientos;
	}


	public ArrayList<Asiento> filtrarAsientos(ArrayList<Asiento> asientos, String clase, Float precioMin, 
							Float precioMax,Boolean conReservados) {
		ArrayList<Asiento> asientosFiltrados = new ArrayList<Asiento>();
		for(Asiento asientoAct: asientos){
			
			if(clase.contains(asientoAct.getClase()) &&
				precioMin <= asientoAct.getPrecio() &&
				precioMax >= asientoAct.getPrecio()){
				
				asientosFiltrados.add(asientoAct);
			}
			
		}
		return asientosFiltrados;
	}

	public void comprar(Asiento unAsiento, Usuario unUsuario) {
		unAsiento.getAerolinea().comprar(unAsiento);
				
	}
	
}
