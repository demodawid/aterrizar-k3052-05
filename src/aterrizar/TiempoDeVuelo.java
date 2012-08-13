package aterrizar;

public class TiempoDeVuelo extends Criterio {

	@Override
	public int compare(Asiento asiento1, Asiento asiento2) {
		if(asiento1.tiempoDeVuelo() > asiento2.tiempoDeVuelo()){
			return 1;
		}else if(asiento1.tiempoDeVuelo() == asiento2.tiempoDeVuelo()){
			return 0;
		}
		return -1;
	}

}
