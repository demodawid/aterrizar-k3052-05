package aterrizar;

import com.lanchita.*;
//import java.util.List;
import java.util.ArrayList;

public class AerolineaLanchitaAdapter implements AerolineaAdapter {
	private AerolineaLanchita lanchita;
	private static Integer porcentajeImpuesto = 15;
	public AerolineaLanchitaAdapter(){
		this.lanchita = AerolineaLanchita.getInstance();
	}
	
	@Override
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada, Usuario usuario){
		
		String[][] asientos = this.lanchita.asientosDisponibles(origen, destino, salida.comoString(), null,
																				llegada.comoString(), null);
		ArrayList<Asiento> misAsientos = new ArrayList<Asiento>();
		Asiento asientoActual;
		
		for (String[] unAsientoStr: asientos){
			Float precio = ( Float.valueOf(unAsientoStr[1]) ) * ( (porcentajeImpuesto/100) + 1 ) + usuario.adicionalPrecio();
			asientoActual = new Asiento(unAsientoStr[0], precio, unAsientoStr[2],unAsientoStr[3], unAsientoStr[4], this);
			
			if( usuario.puedeVer(asientoActual) )
				misAsientos.add(asientoActual);
		}
		
		return misAsientos;
	}

	@Override
	public void comprar(Asiento unAsiento) {
		lanchita.comprar( unAsiento.getCodigo() );
	}
	
	
	
}
