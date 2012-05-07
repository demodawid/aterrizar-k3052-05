package aterrizar;

//import static org.junit.Assert.*;

import org.junit.Test;

public class TestFechaFormato {

	@Test
	public void testEsAnterior() { //La revolución de Mayo antes que el día de la independencia?
		Fecha revolucionDeMayo = new Fecha(25,5,1810);
		Fecha diaDeLaIndependencia = new Fecha(9,7,1816);
		assert(revolucionDeMayo.esAnterior(diaDeLaIndependencia));
	}
	
	@Test
	public void testDiasEntre(){ //Cuantos días pasaron desde la revolución de Mayo y la Independecia?
		Fecha revolucionDeMayo = new Fecha(25,5,1810);
		Fecha diaDeLaIndependencia = new Fecha (9,7,1816);
		Integer diferencia = revolucionDeMayo.diasEntre(diaDeLaIndependencia);
		assert(diferencia == 2234);
	}
	
	@Test
	public void testConvertirNorteAmericano(){
		Fecha miCumpleAnios = (new NorteAmericano()).convertir("05-20-1989");
		assert(miCumpleAnios.getAnios() == 1989);
		assert(miCumpleAnios.getMeses() == 5);
		assert(miCumpleAnios.getDias() == 20);
	}
	
	@Test
	public void testConvertirLatinoAmericano(){
		Fecha miCumpleAnios = (new LatinoAmericano()).convertir("20/05/1989");
		assert(miCumpleAnios.getAnios() == 1989);
		assert(miCumpleAnios.getMeses() == 5);
		assert(miCumpleAnios.getDias() == 20);
	}
	
	@Test
	public void testConvertirISO8601(){
		Fecha miCumpleAnios = (new ISO8601()).convertir("1989-05-20");
		assert(miCumpleAnios.getAnios() == 1989);
		assert(miCumpleAnios.getMeses() == 5);
		assert(miCumpleAnios.getDias() == 20);
	}
	
	@Test
	public void tesConvertirFlexible1(){
		Flexible unaFlex = new Flexible();
		Fecha starWarsDay = unaFlex.convertir("2012-05-04");
		assert(starWarsDay.getAnios() == 2012);
		assert(starWarsDay.getMeses() == 5);
		assert(starWarsDay.getDias() == 4);
	}
	
	@Test(expected = NoSePuedeConvertirException.class)
	public void testConvertirFlexible2(){
		(new Flexible()).convertir("Hola que tal"); //falla por ser tener formato incorrecto (en método Integer.parseInt)
	}
	
	@Test(expected = NoSePuedeConvertirException.class)
	public void testConvertirFlexible3(){
		(new Flexible()).convertir("4534"); //falla por ser muy corto (en método String.substring)
	}
	
	@Test(expected = NoSePuedeConvertirException.class)
	public void testConvertirFlexible4(){
		(new Flexible()).convertir("25/05-1810"); //falla por tener formato incorrecto (Chequeo en LatinoAmericano.convertir y NorteAmericano.convertir )
	}
}
