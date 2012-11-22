package ventanas;

import com.uqbar.commons.collections.Transformer;
import aterrizar.Viaje;

public class ClaseTransformer implements Transformer<Viaje, String> {
	public String transform(Viaje unViaje) {
		String clase =  unViaje.getAsientoUno().getClase();
		if(clase.equals("T"))
			return "Turista";
		else if(clase.equals("E"))
			return "Ejecutiva";
		else
			return "Primera";
	}
}
