package aterrizar;

import aterrizar.FechaInvalidaException;
import aterrizar.ManejadorFechas;

public class Flexible extends ManejadorFechas {

	private int aux; 
	
	public Flexible (String fecha) throws Exception
	{
		if(fecha == null)
		{

			super.fecha = null;
			return;
		}
		
		cambiarFecha(fecha);
	}

	
	public void cambiarFecha(String fecha) throws Exception {
		
		aux = 0;
		
		try{ this.validarFecha(fecha); }
		catch(Exception e){
				aux = 1;
			}
		
		if(aux==0){
			parseFechas(fecha,"iso");
		return;
		}
		
		super.validarFecha(fecha);
		
				
		if((fecha.charAt(2)=='/') & (fecha.charAt(5)=='/'))
		{
			parseFechas(fecha,"latino");
			return;
		}else{
			parseFechas(fecha,"americano");
			return;
		}

	}


	public void validarFecha(String fecha) throws Exception {
		
		if (fecha.length() != 10) 
			throw new FechaInvalidaException();
		
		for (int i = 0; i < fecha.length(); i++) 
			{
	            if(i!=4 & i!=7){
				if (!Character.isDigit(fecha.charAt(i)))
	            	  	throw new FechaInvalidaException();
	            }
	        }  
		
		return;
	}




}
