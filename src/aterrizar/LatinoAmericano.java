package aterrizar;

public class LatinoAmericano extends Formato { // "DD/MM/YYYY"
	
	/**
	 * Convierte un String en formato "DD/MM/YYYY" en un objeto Fecha.
	 */
	public Fecha convertir(String fechaStr){
		if(fechaStr.charAt(2) != '/' || fechaStr.charAt(5) != '/') //Este chequeo se hace para diferenciar entre los 2 formatos
			throw new NoSePuedeConvertirException();
		Integer anio = Integer.parseInt( fechaStr.substring(6,10) );
		Integer mes  = Integer.parseInt( fechaStr.substring(3,5) );
		Integer dia  = Integer.parseInt( fechaStr.substring(0,2) );
		return ( new Fecha(dia,mes,anio) );
	}
}
