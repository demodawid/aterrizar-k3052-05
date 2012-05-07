package aterrizar;

import java.util.List;
import java.util.ArrayList;

public class Flexible extends Formato{
	private List<Formato> formatos;
	public Flexible(){
		super();
		this.formatos = new ArrayList<Formato>();
		this.formatos.add(new LatinoAmericano());
		this.formatos.add(new NorteAmericano());
		this.formatos.add(new ISO8601());
	}
	
	/**
	 * Convierte un String en fomato LatinoAmericano, NorteAmericano, o ISO 8601
	 * en un objeto Fecha.
	 */
	public Fecha convertir(String fechaStr){
		Fecha unaFecha = new Fecha(0,0,0);
		Boolean convertido = false;
		for(Formato unFormato: formatos){
			try{
				unaFecha = unFormato.convertir(fechaStr);
				convertido = true;
				break;
			}
			catch(NumberFormatException e){
				continue;
			}
			catch(StringIndexOutOfBoundsException e){
				continue;
			}
			catch(NoSePuedeConvertirException e){
				continue;
			}
		}
		if (!convertido){
			throw new NoSePuedeConvertirException();
		}
		return unaFecha;
	}
}
