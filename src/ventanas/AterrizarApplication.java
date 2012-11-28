package ventanas;

import java.util.List;

import homes.HomeUsuario;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import uqbar.arena.persistence.Configuration;

import aterrizar.NivelVip;
import aterrizar.Usuario;
import aterrizar.UsuarioEstandar;

/**
 * Correr esta clase con el siguiente argument
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader
 */

public class AterrizarApplication extends Application {
	
	
	
	protected static Usuario usuarioEjemplo;

	public static void main(String[] args) {
		
		Configuration.configure(); 
		// usuarioEjemplo = new
		// UsuarioEstandar("Adriel","Paredes","10.000.000");
		// usuarioEjemplo.setNivel(new NivelVip());
		Usuario example = HomeUsuario.getInstance().createExample();
		example.setNombre("Adriel");

		List<Usuario> usuarios = HomeUsuario.getInstance().allInstances();
		if(usuarios.size() >0 ) usuarioEjemplo = usuarios.get(0);
		else {
			usuarioEjemplo = new UsuarioEstandar("Adriel","Paredes","10.000.000");
			HomeUsuario.getInstance().create(usuarioEjemplo);
		}
		((UsuarioEstandar)usuarioEjemplo).setNivel(new NivelVip());
		usuarioEjemplo.setDni("10.000.000");
		// HomeUsuario.getInstance().create(usuarioEjemplo);
		new AterrizarApplication().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new WindowPrincipal(this, usuarioEjemplo);
	}

}
