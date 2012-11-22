package ventanas;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import aterrizar.NivelVip;
import aterrizar.UsuarioEstandar;

/**
 * Correr esta clase con el siguiente argument
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader
 */

public class AterrizarApplication extends Application{
	protected static UsuarioEstandar usuarioEjemplo;
	
	public static void main(String[] args) {
		usuarioEjemplo = new UsuarioEstandar("Adriel","Paredes","10.000.000");
		usuarioEjemplo.setNivel(new NivelVip());
		new AterrizarApplication().start();
	}
	@Override
	protected Window<?> createMainWindow() {
		return new WindowPrincipal(this,usuarioEjemplo);
	}

}
