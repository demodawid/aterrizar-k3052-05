package aterrizar;

import java.util.ArrayList;

public interface AerolineaAdapter {
	public void comprar(Asiento asiento, Usuario usuario) throws Exception;
	public ArrayList<Viaje> buscarAsientos(String origen, String destino, String salida, String horaSalida, String llegada, String horaLlegada,Integer cantEscalas, Usuario usuario) throws Exception;
	public int getPorcentajeImpuesto();
	public void reservar(Asiento asiento, Usuario usuario);
	public void actualizarReserva(Asiento asiento);
	public ArrayList<Viaje> armarViajeConAsientos(ArrayList<Asiento> asientos, String origen, String destino, Asiento primero, Asiento segundo, Asiento tercero) throws Exception;
	public void setMock(Object aerolineaMock);
	public int getPopularidad(Viaje unViaje);
	public String getNombre();
	public void sobreReservar(Asiento asiento, Usuario usuario);
}
