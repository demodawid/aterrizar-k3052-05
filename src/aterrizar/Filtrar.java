package aterrizar;

import java.util.ArrayList;


public class Filtrar {
	
	ArrayList<TipoDeFiltro> filtros;
	
	public Filtrar(ArrayList<TipoDeFiltro> listaDeFiltros)
	{
		this.filtros = listaDeFiltros;
	}
	
	public ArrayList<Viaje> aplicarFiltros(ArrayList<Viaje> asientosSinFiltrar)
	{
		ArrayList<Viaje> listaFiltrada = asientosSinFiltrar;
		
		for(TipoDeFiltro unFiltro: this.filtros)
		{
			listaFiltrada = unFiltro.filtrar(listaFiltrada);

		}
		
		return listaFiltrada;
	}
	
	
}
