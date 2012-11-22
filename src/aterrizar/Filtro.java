package aterrizar;

import java.util.ArrayList;

public interface Filtro {
	ArrayList<Asiento> filtrar(ArrayList<Asiento> asientos, String clase, String ubicacion, 
							Double precioMin, Double precioMax,Boolean conReservados);
}
