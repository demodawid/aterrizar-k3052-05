package aterrizar;

import java.util.ArrayList;

public class FiltroClase implements Filtro {
	
	public FiltroClase(){
		super();
	}
	
	@Override
	public ArrayList<Asiento> filtrar(ArrayList<Asiento> asientos,
			String clase, String ubicacion, Double precioMin, Double precioMax,
			Boolean conReservados) {
		ArrayList<Asiento> asientosFin = new ArrayList<Asiento>();
		for(Asiento unAsiento: asientos){
			if( (clase.contains(unAsiento.getClase()) ||  clase == ""  )){
				asientosFin.add(unAsiento);
			}
		}
		return asientosFin;
	}

}
