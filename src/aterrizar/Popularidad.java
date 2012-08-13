package aterrizar;

public class Popularidad extends Criterio {

	@Override
	public int compare(Asiento asiento1, Asiento asiento2) {
		if(	SistemaDeComprasAterrizar.getInstance().popularidad(asiento1.vuelo()) > 
			SistemaDeComprasAterrizar.getInstance().popularidad(asiento2.vuelo()) ){
			return 1;
		}else if( SistemaDeComprasAterrizar.getInstance().popularidad(asiento1.vuelo()) > 
			SistemaDeComprasAterrizar.getInstance().popularidad(asiento2.vuelo()) ){
			return 0;
		}
		return -1;
	}

}
