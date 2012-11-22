package aterrizar;

import aterrizar.FechaInvalidaException;



public class Hora {
	
	String hora;
	Integer horas;
	Integer minutos;
		
	
	public Hora(String hora) throws Exception
	{
		this.validarHora(hora);
	}
	
	
	public void validarHora(String hora) throws Exception 
	{
		
		if(hora == null)  
		{
			this.hora = hora;
			return;
		}
		
		if(hora.length() != 5) /* valido que todo este bien */
			throw new FechaInvalidaException();
		
		for (int i = 0; i < hora.length(); i++) 
		{
            if(i!=2)
            {
            	if (!Character.isDigit(hora.charAt(i)))
            		throw new FechaInvalidaException();
            }
        }
		
		int horas = Integer.parseInt(hora.substring(0,2));
		int minutos = Integer.parseInt(hora.substring(3,5));
		
		if(!(horas >= 0 && horas <= 24))
			throw new FechaInvalidaException();
		
		if(!(minutos >= 0 && minutos <= 60))
			throw new FechaInvalidaException();
		
		this.hora = String.format("%02d",(horas))+":"+String.format("%02d",(minutos)); /* Guardo la hora en este formato */
		if(minutos == 0)
			this.hora.concat(Integer.toString(0));
		this.minutos = minutos;
		this.horas = horas;
		
		return;
	}
	
	public Boolean esMenorOIgual(Hora horaDada)
	{
		return(horaDada.hora == null || this.hora == null || this.horas < horaDada.horas || (this.horas == horaDada.horas && this.minutos <= horaDada.minutos));
	}
	
	public Boolean esMenor(Hora horaDada)
	{
		return(horaDada.hora == null || this.hora == null || this.horas < horaDada.horas || (this.horas == horaDada.horas && this.minutos < horaDada.minutos));
	}
	
	
	
}
