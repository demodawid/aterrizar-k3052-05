package aterrizar;

public class Testotra {

	public static void main(String[] args){
		Fecha diaDeLaIndependencia = new Fecha(9,7,1816);
		Fecha revolucionDeMayo = new Fecha(25,5,1810);
		System.out.println( "Días entre la revolución de mayo y el día de la independencia: " + diaDeLaIndependencia.diasEntre(revolucionDeMayo) );
		System.out.println( "Independencia antes de revolución? " + revolucionDeMayo.esAnterior(diaDeLaIndependencia));
		LatinoAmericano unFormato = new LatinoAmericano();
		Fecha unaFecha = unFormato.convertir("20/05/1989");
		System.out.println("La fecha es: " + unaFecha.getDias() + " del " + unaFecha.getMeses() + " de " + unaFecha.getAnios());
	}
		
}
