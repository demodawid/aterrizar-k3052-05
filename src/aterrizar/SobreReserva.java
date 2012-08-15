package aterrizar;

public class SobreReserva {
	
	private Asiento asiento;
	private Usuario usuario;
	
	public SobreReserva(Asiento asiento, Usuario usuario){
		this.setAsiento(asiento);
		this.setUsuario(usuario);
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
