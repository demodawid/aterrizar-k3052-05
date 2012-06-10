package aterrizar;

public class UsuarioNoPaga extends Usuario {
	
	
	public UsuarioNoPaga(String nombre, String apellido, String dni, SistemaDeComprasAterrizar aterrizar){
		super(nombre, apellido, dni, aterrizar);
	}
	
	@Override
	public Boolean puedeVer(Asiento unAsiento) {
		return !unAsiento.esSuperOferta();
	}

	@Override
	public Float adicionalPrecio() {
		return (float) 20;
	}

}
