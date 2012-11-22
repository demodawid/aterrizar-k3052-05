package aterrizar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ManejadorFechas {
	
	protected Calendar fecha = Calendar.getInstance();
	
	
	public void validarFecha(String fecha) throws Exception {
		
		if (fecha == null || fecha.length() != 10) 
			throw new FechaInvalidaException();
		
		for (int i = 0; i < fecha.length(); i++) 
			{
	            if(i!=2 & i!=5){
				if (!Character.isDigit(fecha.charAt(i)))
	            	  	throw new FechaInvalidaException();
	            }
	        } 
		
		return;
	}
	
	public void parseFechas(String fechaDada, String tipo) throws Exception{
		SimpleDateFormat formato;
		if(tipo == "iso"){
			formato = new SimpleDateFormat("yyyy-MM-dd");
			Date d =  formato.parse(fechaDada);
			fecha.setTime(d);
		}else{ 
			if(tipo == "americano"){
				formato = new SimpleDateFormat("MM-dd-yyyy");
				Date d =  formato.parse(fechaDada);
				fecha.setTime(d);
			}else {
					formato = new SimpleDateFormat("dd/MM/yyyy");
					Date d =  formato.parse(fechaDada);
					fecha.setTime(d);
			}
			  
		}
		return;
	}
	
	public boolean esMayorA(ManejadorFechas fecha){
		
		int resultado = this.fecha.compareTo(fecha.fecha);
		return resultado > 0;
	}
	
	
	public int tuDiferenciaCon(ManejadorFechas fecha){
				
		long milis1 = this.fecha.getTimeInMillis();
		long milis2 = fecha.fecha.getTimeInMillis();
		
		long difMilis = milis1 - milis2;
		int dif = Math.round(difMilis / (24 * 60 * 60 * 1000));
		
		return dif;
	}
	
	
	public void cambiarFecha(String fecha) throws Exception {
		return;
	}

	
	public void setFecha(int anio, int mes, int dia){

		this.fecha.set(anio, mes-1, dia);
	}
	
	public Calendar getFecha(){
		return fecha;
	}
}

