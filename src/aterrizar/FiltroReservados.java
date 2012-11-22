package aterrizar;

import java.util.ArrayList;

public class FiltroReservados implements Filtro {
	
	public FiltroReservados(){
		super();
	}
	
	@Override
	public ArrayList<Asiento> filtrar(ArrayList<Asiento> asientos,
			String clase, String ubicacion, Double precioMin, Double precioMax,
			Boolean conReservados) {
		ArrayList<Asiento> asientosFin = new ArrayList<Asiento>();
		
		
		for(Asiento unAsiento: asientos){
			if(conReservados){
				asientosFin.add(unAsiento);
			}else{
				if(!unAsiento.getEstado().equals("R")){
					asientosFin.add(unAsiento);
				}
			}
		}
		
		return asientosFin;
	}

}
