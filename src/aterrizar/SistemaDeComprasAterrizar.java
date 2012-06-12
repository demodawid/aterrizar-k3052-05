package aterrizar;

import java.util.ArrayList;

/**
 * 
 * Sistema de compras Aterrizar. Aquí se agregarán las nuevas aerolineas con las
 * que se firme contrato. El usuario interactúa solo con este facade de nuestro sistema.
 * Debería existir solo una implementación de esta clase.
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
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, 
											Fecha llegada, Usuario usuario) {
		ArrayList<Asiento> asientos = new ArrayList<Asiento>();
		for(AerolineaAdapter unaAerolinea: aerolineas){
			asientos.addAll(unaAerolinea.buscarAsientos(origen, destino, salida, llegada, usuario));
		}
		return asientos;
	}

	public void comprar(Asiento unAsiento, Usuario unUsuario) {
		unAsiento.getAerolinea().comprar(unAsiento);
				
	}
}
