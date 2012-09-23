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

public class TestAerolineaLanchita {
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
		//Colecci�n con mi aerolineaAdapter
		ArrayList<AerolineaAdapter> aerolineaAdapters = new ArrayList<AerolineaAdapter>();
		aerolineaAdapters.add(aerolineaAdapter);
		//Inyecto esta colecci�n en el sistema de compras
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
	
	@Test
	public void leLlegaElMensajeALaAerolineaCuandoBusco(){
		florencia.buscarAsientos("BUE", "LA", null, null);
		Mockito.verify(aerolineaMock).asientosDisponibles("BUE", "LA", null, null, null, null);
	}

	@Test
	public void leLlegaElMensajeALaAerolineaCuandoCompro(){
		demian.comprar(new Asiento("Probando", (float)100, "T","V","D",aerolineaAdapter));
		Mockito.verify(aerolineaMock).comprar("Probando");
	}
	
	@Test
	public void siElClienteNoPagaTieneRecargo(){
		//Cristian es un UsuarioNoPaga
		assert (cristian.adicionalPrecio() > 0);
	}

	@Test 
	public void siElClienteEsVipPuedeVerSuperOfertas(){
		//Creo un asiento superoferta
		String[][] asientosConSuperoferta = new String[][] {{"UnCodigoLoco","123.45","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", null, null, null, null, null)).thenReturn(asientosConSuperoferta);
		//Demian es un usuario VIP
		ArrayList<Asiento> asientos = demian.buscarAsientos("BUE", null, null, null);
		
//		ArrayList<Busqueda> busquedas = demian.getBusquedasHistoricas();
//		assertFalse(busquedas.isEmpty());
//		ArrayList<Asiento> asientos = busquedas.get(0).buscar();
		assertTrue(asientos.get(0).esSuperOferta());
	}
	
	@Test
	public void siElClienteEsNoVipNoPuedeVerSuperOfertas(){
		ArrayList<Asiento> asientos;
		//Creo un asiento superoferta
		String[][] asientosConSuperoferta = new String[][] {{"UnCodigoLoco","123.45","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", null, null, null, null, null)).thenReturn(asientosConSuperoferta);
		//Florencia no es vip y Miguel no paga
		asientos = florencia.buscarAsientos("BUE", null, null, null);
		
//		asientos = florencia.getBusquedasHistoricas();
		assertTrue(asientos.isEmpty());
	}
	
	@Test
	public void cuandoElClienteCompraAumentanSusComprasHistoricas(){
		Asiento unAsiento = new Asiento("UnCodigoLoco", (float)100, "", "", "", aerolineaAdapter);
		Float comprasAnteriores = victoria.getComprasTotales();
		victoria.comprar(unAsiento);
		assertTrue(comprasAnteriores < victoria.getComprasTotales() );
	}
	
	@Test
	public void cuandoLasComprasTotalesSuperan100000ElClientePasaASerVip(){
		//Florencia es usuario estandar NO vip
		assertEquals( florencia.getNivelImportancia().getClass() , NivelNormal.class);
		//Creo un asiento con valor 100001
		Asiento unAsiento = new Asiento("UnCodigoLoco", (float)100001, "", "", "", aerolineaAdapter);
		
		florencia.comprar(unAsiento);
		
		//Su nivel de importancia cambi� a VIP
		assertEquals(florencia.getNivelImportancia().getClass(), NivelVip.class);
	}
}


	