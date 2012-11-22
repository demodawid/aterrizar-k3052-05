package aterrizar;

import java.util.Comparator;

public abstract class Criterio implements Comparator<Asiento> {
	/**
	 * Este metodo debe comparar 2 elementos. Si el primero es mayor,
	 * retorna 1, si es menor retorna -1, si es igual 0.
	 */
	public abstract int compare(Asiento asiento1, Asiento asiento2);
}
