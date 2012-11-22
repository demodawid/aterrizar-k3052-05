package aterrizar;

import java.util.ArrayList;

public class FiltroPrecios implements Filtro {
	
	public FiltroPrecios(){
		super();
	}
	
	@Override
	public ArrayList<Asiento> filtrar(ArrayList<Asiento> asientos,
			String clase, String ubicacion, Double precioMin, Double precioMax,
			Boolean conReservados) {
		ArrayList<Asiento> asientosFinal = new ArrayList<Asiento>();
		for(Asiento unAsiento: asientos){
			if( (precioMin <= unAsiento.getPrecio() ) &&
			   (precioMax >= unAsiento.getPrecio() || precioMax == 0) ){
				
				asientosFinal.add(unAsiento);
				
			}
		}
		return asientosFinal;
	}



}
