package aterrizar;

import java.util.ArrayList;

public class AerolineaAdapterMock extends AerolineaLanchitaAdapter {

	public Boolean meLlegoBuscarAsientos;
	private ArrayList<Asiento> asientos;
	public Boolean meLlegoComprarAsiento;
	public AerolineaAdapterMock(){
		meLlegoBuscarAsientos = false;
		asientos = new ArrayList<Asiento>();
		asientos.add( new Asiento("", (float) 0 , "", "", "", this) ); //Para que no esté vacía la colección
	}
	
	@Override
	public ArrayList<Asiento> buscarAsientos(String origen, String destino,
			Fecha salida, Fecha llegada, Usuario usuario) {
		meLlegoBuscarAsientos = true;
		
		ArrayList<Asiento> asientosADevolver = new ArrayList<Asiento>();
		
		//Me quedo solo con los asientos que el usuario puede ver
		for (Asiento unAsiento: asientos){
			if(usuario.puedeVer(unAsiento))
				asientosADevolver.add(unAsiento);
		}
		return asientosADevolver;
	}

	@Override
	public void comprar(Asiento unAsiento) {
		meLlegoComprarAsiento = true;
	}
	
	public void agregarAsiento(Asiento unAsiento){
		asientos.add(unAsiento);
	}
	
}
