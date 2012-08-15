package aterrizar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Sistema de compras Aterrizar. Aquï¿½ se agregarï¿½n las nuevas aerolï¿½neas con las
 * que se firme contrato. Esta clase se encargarï¿½ de interactuar con todas las 
 * aerolï¿½neas de forma adecuada. El usuario interactï¿½a solo con este facade
 * de nuestro sistema, y la comunicacion con las aerolï¿½neas es transparente para el.
 * Deberï¿½a existir solo una implementaciï¿½n de esta clase.
 *
 */
public class SistemaDeComprasAterrizar {
	private static final SistemaDeComprasAterrizar INSTANCE = new SistemaDeComprasAterrizar();
	
	private ArrayList<Asiento> ComprasHistoricas; 
	
	private SobreReservaObserver sobreReservaObserver;
	
	ArrayList<AerolineaAdapter> aerolineas;
	
	private ArrayList<Filtro> filtros;
	
	public static SistemaDeComprasAterrizar getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Constructor
	 */
	private SistemaDeComprasAterrizar(){
		this.aerolineas = new ArrayList<AerolineaAdapter>();
		this.aerolineas.add(new AerolineaLanchitaAdapter());
		this.ComprasHistoricas = new ArrayList<Asiento>();
		this.sobreReservaObserver = new SobreReservaObserver();
		filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroClase());
		filtros.add(new FiltroPrecios());
		filtros.add(new FiltroReservados());
		filtros.add(new FiltroUbicacion());
		
	}
	
	public void setAerolineas(ArrayList<AerolineaAdapter> aerolineas){
		this.aerolineas = aerolineas;
	}
	
	/**
	 * Busca asientos en todas las aerolineas que conoce para un determinado usuario,
	 * con origen, destino, fecha de salida y fecha de llegada.
	 */
	
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, String clase, String ubicacion,
											Float precioMin, Float precioMax, Boolean conReservados, Criterio uncriterio, Usuario usuario) {
		
		ArrayList<Asiento> asientos = new ArrayList<Asiento>();
		for(AerolineaAdapter unaAerolinea: aerolineas){
			ArrayList<Asiento> asientosAct = unaAerolinea.buscarAsientos(origen, destino, salida, new Fecha(0,0,0), usuario);
			asientosAct = this.filtrarAsientos(asientosAct,clase, ubicacion, precioMin, precioMax, conReservados);
			asientos.addAll(asientosAct);
		}
		//Aca el ordenamiento
		Collections.sort(asientos, uncriterio);
		return asientos;
	}


	public ArrayList<Asiento> filtrarAsientos(ArrayList<Asiento> asientos, String clase, String ubicacion, Float precioMin, 
							Float precioMax,Boolean conReservados) {
		
		ArrayList<Asiento> asientosFiltrados = new ArrayList<Asiento>();
		asientosFiltrados.addAll(asientos);
		
		for(Filtro unFiltro: filtros){
			asientosFiltrados = unFiltro.filtrar(asientosFiltrados, clase, ubicacion, precioMin, precioMax, conReservados);
		}
		return asientosFiltrados;
	}

	public void comprar(Asiento unAsiento, Usuario unUsuario) {
		sobreReservaObserver.chequearSobreReservas(unAsiento);
		unAsiento.getAerolinea().comprar(unAsiento, unUsuario);
		ComprasHistoricas.add(unAsiento);
				
	}
	public int popularidad(String unVuelo){
		int cant = 0;
		for(Asiento unAsiento: ComprasHistoricas){
			if(unAsiento.vuelo() == unVuelo)
				cant++;
		}
		return cant;
	}

	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, String clase, String ubicacion,
											Float precioMin, Float precioMax, Boolean conReservados, Criterio uncriterio, Boolean conEscala, Usuario usuario) {
		
		ArrayList<Asiento> asientos = this.buscarAsientos(origen, destino, salida, clase, ubicacion, precioMin, precioMax, conReservados, uncriterio, usuario);
		if(conEscala){
			//Acá el código que agrega a la colección los que tienen escalas!
			//asientos.add(bla);
		}
		return asientos;
	}
	
	public void reservar(Asiento unAsiento, Usuario unUsuario){
		sobreReservaObserver.chequearSobreReservas(unAsiento);
		
		if(sobreReservaObserver.estaSobreReservado(unAsiento)){
			throw new NoPuedeReservarException();
		}else{
			try{
				unAsiento.getAerolinea().reservar(unAsiento, unUsuario);
			}catch(NoPuedeReservarException e){
				sobreReservaObserver.sobreReservar(unUsuario, unAsiento);
			}
		}
	}
	
	public void comprarReserva(Asiento unAsiento, UsuarioEstandar usuarioEstandar){
		sobreReservaObserver.usuarioCompraSobreReserva(unAsiento, usuarioEstandar);
	}
}
