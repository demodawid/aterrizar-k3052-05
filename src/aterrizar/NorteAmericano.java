package aterrizar;

public class NorteAmericano extends Formato { //"MM-DD-YYYY"
	
	/**
	 * Convierte un String en formato "MM-DD-YYYY" en un objeto Fecha.
	 */
	public Fecha convertir(String fechaStr){
		if(fechaStr.charAt(2) != '-' || fechaStr.charAt(5) != '-') //Este chequeo se hace para diferenciar entre los 2 formatos
			throw new NoSePuedeConvertirException();
		Integer anio = Integer.parseInt( fechaStr.substring(6,10) );
		Integer mes  = Integer.parseInt( fechaStr.substring(0,2) );
		Integer dia  = Integer.parseInt( fechaStr.substring(3,5) );
		return ( new Fecha(dia,mes,anio) );
	}
}
