package aterrizar;

public class Testotra {

	public static void main(String[] args){
		Fecha diaDeLaIndependencia = new Fecha(9,7,1816);
		Fecha revolucionDeMayo = new Fecha(25,5,1810);
		System.out.println( "D�as entre la revoluci�n de mayo y el d�a de la independencia: " + diaDeLaIndependencia.diasEntre(revolucionDeMayo) );
		System.out.println( "Independencia antes de revoluci�n? " + revolucionDeMayo.esAnterior(diaDeLaIndependencia));
		LatinoAmericano unFormato = new LatinoAmericano();
		Fecha unaFecha = unFormato.convertir("20/05/1989");
		System.out.println("La fecha es: " + unaFecha.getDias() + " del " + unaFecha.getMeses() + " de " + unaFecha.getAnios());
	}
		
}
