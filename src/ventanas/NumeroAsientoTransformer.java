package ventanas;

import com.uqbar.commons.collections.Transformer;
import aterrizar.Viaje;

public class NumeroAsientoTransformer  implements Transformer<Viaje, String>{
	
	public String transform(Viaje unViaje) {
		return Integer.toString(unViaje.getAsientoUno().getNumero());
	}
}
