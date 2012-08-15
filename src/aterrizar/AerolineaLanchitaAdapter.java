package aterrizar;

import com.lanchita.*;
import com.lanchita.excepciones.LanchitaException;
//import java.util.List;
import java.sql.Time;
import java.util.ArrayList;

public class AerolineaLanchitaAdapter implements AerolineaAdapter {
	protected AerolineaLanchita lanchita;
	private static Integer porcentajeImpuesto = 15;
	
	public AerolineaLanchitaAdapter(){
		this.lanchita = AerolineaLanchita.getInstance();
	}
	
	@Override
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada, Usuario usuario){
		
		//Si llega null o fecha invalida en salida o llegada, usar null para la b�squeda
		String strSalida = "";
		String strLlegada = "";
		try{strSalida = salida.comoString();}
			catch(NullPointerException e){strSalida = null;}
			catch(FechaInvalidaException e){strSalida = null;}
		try{strLlegada = llegada.comoString();}
			catch(NullPointerException e){strLlegada = null;}
			catch(FechaInvalidaException e){strLlegada = null;}
		
		String[][] asientos = this.lanchita.asientosDisponibles(origen, destino, strSalida, null,
																				strLlegada, null);
		
		ArrayList<Asiento> misAsientos = new ArrayList<Asiento>();
		Asiento asientoActual;
		
		if(asientos == null)
			return misAsientos;
		
		for (String[] unAsientoStr: asientos){
			Float precio = ( Float.valueOf(unAsientoStr[1]) ) * ( (porcentajeImpuesto/100) + 1 ) + usuario.adicionalPrecio();
			asientoActual = new Asiento(unAsientoStr[0], precio, unAsientoStr[2],unAsientoStr[3], unAsientoStr[4], this);
			
			
			asientoActual = new Asiento(unAsientoStr[0], precio, unAsientoStr[2], unAsientoStr[3] , unAsientoStr[4], this, 
										new Time(0,0,0), new Time(0,0,0), salida);
			
			if( usuario.puedeVer(asientoActual) )
				misAsientos.add(asientoActual);
		}
		
		return misAsientos;
	}

	@Override
	public void comprar(Asiento unAsiento, Usuario unUsuario) {
		lanchita.comprar( unAsiento.getCodigo());
	}
	
	public void miAerolinea(AerolineaLanchita unaAero){
		this.lanchita = unaAero;
	}

	@Override
	public void reservar(Asiento unAsiento, Usuario unUsuario) {
		try{
			lanchita.reservar(unAsiento.getCodigo(), unUsuario.dni);
		}
		catch(LanchitaException e){
			throw new NoPuedeReservarException();
		}	
	}
}
