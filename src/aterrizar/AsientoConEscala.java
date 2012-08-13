package aterrizar;

import java.sql.Time;

import javax.management.loading.PrivateMLet;

public class AsientoConEscala extends Asiento {
	
	private Asiento escala;
	
	public AsientoConEscala(String codigo, Float precio, String clase, String ubicacion,
							String estado, AerolineaAdapter aerolinea, Time horaLlegada,
							Time horaSalida, Fecha fecha, Asiento escala){
		super(codigo,precio,clase,ubicacion,estado,aerolinea,horaLlegada,horaSalida,fecha);
		this.escala = escala;
	}
	
	@Override
	public Boolean tieneEscala(){
		return true;
	}
	
}
