package aterrizar;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Testotra {

	public static void main(String[] args){
		Fecha diaDeLaIndependencia = new Fecha(9,7,1816);
		Fecha revolucionDeMayo = new Fecha(25,5,1810);
		System.out.println( "Días entre la revolución de mayo y el día de la independencia: " + diaDeLaIndependencia.diasEntre(revolucionDeMayo) );
		System.out.println( "Independencia antes de revolución? " + revolucionDeMayo.esAnterior(diaDeLaIndependencia));
		LatinoAmericano unFormato = new LatinoAmericano();
		Fecha unaFecha = unFormato.convertir("20/05/1989");
		System.out.println("La fecha es: " + unaFecha.getDias() + " del " + unaFecha.getMeses() + " de " + unaFecha.getAnios());
		
		Float unNum = (float) 45;
		
		System.out.println(unNum);
		
		unNum = Float.valueOf("123.13");
		System.out.println(unNum);
		
		Usuario demian = new UsuarioEstandar("Demian", "Dawid", "34519332", SistemaDeComprasAterrizar.getInstance());
		ArrayList<Asiento> resultado = demian.buscarAsientos("Bue", null, null, null);
		for(Asiento unAsiento: resultado){
			System.out.println(unAsiento.getUbicacion() +" "+ 
						unAsiento.getClase()+" $" +
						unAsiento.getPrecio());
		}
		
		ArrayList<Asiento> resultado2 = new ArrayList<Asiento>();
		
		for(Asiento unAsiento: resultado2){
			
		}
	}
	
	
		
}
