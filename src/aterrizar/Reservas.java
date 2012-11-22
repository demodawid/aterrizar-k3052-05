package aterrizar;

import java.util.ArrayList;

public class Reservas {
	
	public ArrayList<Usuario> listReserva = new ArrayList<Usuario>();
	public String codigoDeVuelo;
	public Integer numAsiento;
	
	
	public Reservas(String codigoVuelo, Integer numAsiento, Usuario usuario)
	{

		this.codigoDeVuelo = codigoVuelo;
		this.numAsiento = numAsiento;
		this.listReserva.add(usuario);
		
	}
	
	public void agregarReserva(Usuario usuario)
	{
		this.listReserva.add(usuario);
	}
	
	
}
