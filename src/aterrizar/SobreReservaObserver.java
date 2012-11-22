package aterrizar;

import java.util.ArrayList;

public class SobreReservaObserver {
	
	private ArrayList<SobreReserva> sobreReservas;

	public SobreReservaObserver(){
		this.sobreReservas = new ArrayList<SobreReserva>();
	}

	public void chequearSobreReservas(Asiento unAsiento) {
		if(this.estaSobreReservado(unAsiento)){
			try{
				unAsiento.getAerolinea().reservar(unAsiento, this.quienSobreReservo(unAsiento));
				//Si llega hasta aca la aerolinea ya dio de baja la reserva anterior por vencimiento
				for(SobreReserva unaSobreReserva : sobreReservas){
					if(unaSobreReserva.getAsiento().getCodigoDeVuelo().equals(unAsiento.getCodigoDeVuelo())){
						sobreReservas.remove(unaSobreReserva);
					}
				}
			} catch(NoPuedeReservarException e){
				//No se hace nada porque esta reservado y sobreReservado
			}
		}
		
	}

	private Usuario quienSobreReservo(Asiento unAsiento) {
		for(SobreReserva unaSobreReserva : sobreReservas){
			if(unaSobreReserva.getAsiento().getCodigoDeVuelo().equals(unAsiento.getCodigoDeVuelo())){
				return unaSobreReserva.getUsuario();
			}
		}
		return null;
	}

	public boolean estaSobreReservado(Asiento unAsiento) {
		for(SobreReserva unaSobreReserva : sobreReservas){
			if(unaSobreReserva.getAsiento().getCodigoDeVuelo().equals(unAsiento.getCodigoDeVuelo())){
				return true;
			}
		}
		return false;
	}

	public void sobreReservar(Usuario unUsuario, Asiento unAsiento) {
		for(SobreReserva unaSobreReserva : sobreReservas){
			if(unaSobreReserva.getAsiento().getCodigoDeVuelo().equals(unAsiento.getCodigoDeVuelo())){
				if(!(unaSobreReserva.getUsuario()==unUsuario)){
					throw new NoPuedeReservarException();
				} else  {
					return;
				}
			}
		}
		SobreReserva sobreReserva = new SobreReserva(unAsiento, unUsuario);
		sobreReservas.add(sobreReserva);
	}

	public void usuarioCompraSobreReserva(Asiento unAsiento, UsuarioEstandar usuarioEstandar) {
		SobreReserva reservaAEliminar = null;
		for(SobreReserva unaSobreReserva : sobreReservas){
			if(unaSobreReserva.getAsiento().getCodigoDeVuelo().equals(unAsiento.getCodigoDeVuelo())){
				reservaAEliminar = unaSobreReserva;
			}
		}
		if(reservaAEliminar != null){
			sobreReservas.remove(reservaAEliminar);
		}
		SobreReserva nuevaSobreReserva = new SobreReserva(unAsiento, usuarioEstandar);
		sobreReservas.add(nuevaSobreReserva);
	}
}
