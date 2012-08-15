package aterrizar;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lanchita.AerolineaLanchita;

public class TestReservas{
	
	SistemaDeComprasAterrizar aterrizar;
	UsuarioEstandar demian;
	UsuarioEstandar florencia;
	UsuarioEstandar victoria;
	UsuarioNoPaga cristian;
	UsuarioNoPaga miguel;
	AerolineaLanchitaAdapter aerolineaAdapter;
	AerolineaLanchita aerolineaMock;
	
	@Before
	public void setUp() {
		//Sistema de compras
		aterrizar = SistemaDeComprasAterrizar.getInstance();
		//Mi aerolinea mock
		 aerolineaMock = Mockito.mock(AerolineaLanchita.class);
		//Mi aerolinea adapter
		 aerolineaAdapter = new AerolineaLanchitaAdapter();
		//Inyecto la aerolinea mock
		 aerolineaAdapter.miAerolinea(aerolineaMock);
		//Colección con mi aerolineaAdapter
		ArrayList<AerolineaAdapter> aerolineaAdapters = new ArrayList<AerolineaAdapter>();
		aerolineaAdapters.add(aerolineaAdapter);
		//Inyecto esta colección en el sistema de compras
		aterrizar.setAerolineas(aerolineaAdapters);
		//Usuarios
		demian = new UsuarioEstandar("Demian", "Dawid", "12345678", aterrizar);
		demian.setComprasTotales( (float) 200000 ); //Es vip
		florencia = new UsuarioEstandar("Florencia", "Gomez", "87654321", aterrizar);
		florencia.setComprasTotales((float) 3000); //No es vip
		victoria = new UsuarioEstandar("Victoria", "Gonzalez", "56321478", aterrizar);
		cristian = new UsuarioNoPaga("Cristian", "Rojas", "85616975", aterrizar);
		miguel = new UsuarioNoPaga("Miguel", "Conforti", "47331649", aterrizar);
	}

	@Test(expected=UsuarioNoAutorizadoException.class)
	public void siUsuarioNoPagaQuiereReservarNoPuede()throws UsuarioNoAutorizadoException{
		Asiento asientoAReservar = new Asiento("Probando", (float)100, "T","V","D",aerolineaAdapter);
		miguel.reservar(asientoAReservar);
	}
	
	@Test
	public void leLlegaElMensajeALaAerolineaCuandoReservo(){
		Asiento asientoAReservar = new Asiento("Probando", (float)100, "T","V","D",aerolineaAdapter);
		florencia.reservar(asientoAReservar);
		Mockito.verify(aerolineaMock).reservar(asientoAReservar.getCodigo(),florencia.dni);
	}
}
