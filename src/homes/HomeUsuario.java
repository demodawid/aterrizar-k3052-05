package homes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aterrizar.AerolineaAdapter;
import aterrizar.Asiento;
import aterrizar.Usuario;
import aterrizar.UsuarioEstandar;

import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentHome;

@Observable
public class HomeUsuario extends PersistentHome<Usuario> implements Serializable  {
	
	private static HomeUsuario instance;
	
	public static synchronized HomeUsuario getInstance() {
		if (instance == null) {
			instance = new HomeUsuario();
		}
		return instance;
	}

	private HomeUsuario() {
	}
	
	@Override
	public Class<Usuario> getEntityType() {
		return Usuario.class;
	}

	@Override
	public Usuario createExample() {
		return new UsuarioEstandar();
	}

}