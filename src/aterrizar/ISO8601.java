package aterrizar;

public class ISO8601 extends Formato { // "YYYY-MM-DD"
	
	/**
	 * Convierte un String en formato "YYYY-MM-DD" en un objeto Fecha.
	 */
	public Fecha convertir(String fechaStr){
		if(fechaStr.charAt(4) != '-' || fechaStr.charAt(7) != '-')
			throw new NoSePuedeConvertirException();
		Integer anio = Integer.parseInt( fechaStr.substring(0,3) );
		Integer mes  = Integer.parseInt( fechaStr.substring(5,6) );
		Integer dia  = Integer.parseInt( fechaStr.substring(8,9) );
		return ( new Fecha(dia,mes,anio) );
	}
}
