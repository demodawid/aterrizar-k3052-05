package ventanas;

import com.uqbar.commons.collections.Transformer;
import aterrizar.Asiento;

public class AerolineaAsNombreTransformer implements Transformer<Asiento, String>  {
		public String transform(Asiento unAsiento) {
			return unAsiento.getAerolinea().getNombre();
		}
}
