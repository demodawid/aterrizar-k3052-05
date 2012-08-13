package aterrizar;

public class PrecioDescendente extends Criterio {

	@Override
	public int compare(Asiento asiento1, Asiento asiento2) {
		if(asiento1.getPrecio() > asiento2.getPrecio()){
			return 1;
		}else if (asiento1.getPrecio() == asiento2.getPrecio()) {
			return 0;
		}
		return -1;
	}

}
