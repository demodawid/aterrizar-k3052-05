package ventanas;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import aterrizar.Asiento;
import aterrizar.SistemaDeComprasAterrizar;
import aterrizar.Usuario;
import aterrizar.NoPuedeReservarException;

@Observable
public class SobreReservaModel {

	private Asiento asiento;
	private Usuario usuario;
	
	public SobreReservaModel(Asiento unAsiento, Usuario usuario){
		this.asiento = unAsiento;
		this.usuario = usuario;
	}
	
	public void sobreReservar(SobreReservaWindow ventana){
		try{
			SistemaDeComprasAterrizar.getInstance().sobreReservar(this.asiento,this.usuario);
			ventana.close();
		}
		catch(NoPuedeReservarException e){
			throw new UserException("El asiento ya se encuentra sobre reservado por usted.");
		}
	}

	public String getCodAsiento() {
		return this.asiento.getCodigoDeVuelo()+"-"+this.asiento.getNumero();
	}
}
