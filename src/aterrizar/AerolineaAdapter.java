package aterrizar;

import java.util.ArrayList;

public interface AerolineaAdapter {
	//No se pueden definir variables en la interfaz :(
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada, Usuario usuario);
	public void comprar(Asiento unAsiento, Usuario unUsuario);
}
