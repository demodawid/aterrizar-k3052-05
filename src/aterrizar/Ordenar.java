package aterrizar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


public class Ordenar {
	Comparator<Viaje> orden;
	
	public Ordenar(Comparator<Viaje> orden){
		this.orden = orden;
	}
	
	public ArrayList<Viaje> ordenarLista(ArrayList<Viaje> asientos)
	{
		ArrayList<Viaje> asientosOrdenados = (ArrayList<Viaje>) asientos.clone();

		Collections.sort(asientosOrdenados,this.orden);
		
		return asientosOrdenados;
		
	}

}
