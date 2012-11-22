package ventanas;

import com.uqbar.commons.collections.Transformer;
import aterrizar.Viaje;

public class CodigoVueloTransformer implements Transformer<Viaje, String>{
	public String transform(Viaje unViaje) {
		return unViaje.getAsientoUno().getCodigoDeVuelo();
	}
}
