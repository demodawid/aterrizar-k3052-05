package ventanas;

import com.uqbar.commons.collections.Transformer;
import aterrizar.Viaje;

public class UbicacionTransformer implements Transformer<Viaje, String> {
	public String transform(Viaje unViaje) {
		String ubicacion = unViaje.getAsientoUno().getUbicacion();
		if(ubicacion.equals("V"))
			return "Ventana";
		else if(ubicacion.equals("P"))
			return "Pasillo";
		else
			return "Centro";
	}
}
