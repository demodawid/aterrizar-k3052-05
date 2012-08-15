package aterrizar;

import java.util.ArrayList;

public class FiltroUbicacion implements Filtro {
	
	public FiltroUbicacion(){
		super();
	}
	
	@Override
	public ArrayList<Asiento> filtrar(ArrayList<Asiento> asientos,
			String clase, String ubicacion, Float precioMin, Float precioMax,
			Boolean conReservados) {
		ArrayList<Asiento> asientosFin = new ArrayList<Asiento>();
		for(Asiento unAsiento: asientos){
			if( (ubicacion.equals(unAsiento.getUbicacion()) || ubicacion == "" )){
				asientosFin.add(unAsiento);
			}
		}
		return asientosFin;
	}

}
