package aterrizar;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lanchita.AerolineaLanchita;

public class TestBusquedas {
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
	
	
	@Test
	public void siBuscoPorClasePrimeraFunciona() {
		//Hago que devuelva asientos de todas las clases
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"ABC-123","123.45","T","V","D"}, 
														{"ABC-123","123.45","T","V","D"},
														{"ABC-123","123.45","P","V","D"},
														{"ABC-123","123.45","E","V","D"},
														{"ABC-123","123.45","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "P", "", (float)0 , (float)1000, true, new SinOrden(), false);
		
		ArrayList<Asiento> asientos = demian.getBusquedasHistoricas();
		//Deberían verse solo los 3 asientos que son de primera
		for(Asiento unAsiento: asientos){
			assertTrue(unAsiento.getClase() == "P");
		}
		
		
	}
	
	@Test
	public void siBuscoPorClasePrimeraYEjecutivaFunciona(){
		//Hago que devuelva asientos de todas las clases
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"ABC-123","123.45","T","V","D"}, 
														{"ABC-123","123.45","T","V","D"},
														{"ABC-123","123.45","P","V","D"},
														{"ABC-123","123.45","E","V","D"},
														{"ABC-123","123.45","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PE", "", (float)0 , (float)1000, true, new SinOrden(), false);
		
		ArrayList<Asiento> asientos = demian.getBusquedasHistoricas();
		//Deberían verse asientos de primera y ejecutiva
		for(Asiento unAsiento: asientos){
			assertTrue( (unAsiento.getClase() == "P") || (unAsiento.getClase() == "E") );
		}
	}
	
	@Test
	public void siBuscoPorPrecioAndaBien(){
		//Hago que devuelva asientos de todos los rangos
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"ABC-123","500.12","T","V","D"}, 
														{"ABC-123","12345.67","T","V","D"},
														{"ABC-123","5555.55","P","V","D"},
														{"ABC-123","2.5","E","V","D"},
														{"ABC-123","100.00","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PTE", "", (float)0 , (float)600, true, new SinOrden(), false);
		
		ArrayList<Asiento> asientos = demian.getBusquedasHistoricas();
		//Deberían verse solo asientos en el rango de $0 a $600
		for(Asiento unAsiento: asientos){
			assertTrue( (unAsiento.getPrecio() >= 0) && (unAsiento.getPrecio() <= 600) );
		}
	}
	
	@Test
	public void siBuscoConOrdenDescendenteAndaBien(){
		//Hago que devuelva asientos de precios distintos
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"ABC-123","500.12","T","V","D"}, 
														{"ABC-123","12345.67","T","V","D"},
														{"ABC-123","5555.55","P","V","D"},
														{"ABC-123","2.5","E","V","D"},
														{"ABC-123","100.00","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PTE", "", (float)0 , (float)600, true, new PrecioDescendente(), false);
		
		ArrayList<Asiento> asientos = demian.getBusquedasHistoricas();
		//Los asientos deberían estar ordenados por orden descendente de precio
		int veces = 0;
		Float anterior = (float)0;
		for(Asiento unAsiento: asientos){
			if(veces == 0){
				anterior = unAsiento.getPrecio();
				veces++;
				continue;
			}
			assertTrue(unAsiento.getPrecio() < anterior);
			anterior = unAsiento.getPrecio();
			
		}
	}
	
	@Test
	public void siBuscoConOrdenAscendenteAndaBien(){
		//Hago que devuelva asientos de precios distintos
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"ABC-123","500.12","T","V","D"}, 
														{"ABC-123","12345.67","T","V","D"},
														{"ABC-123","5555.55","P","V","D"},
														{"ABC-123","2.5","E","V","D"},
														{"ABC-123","100.00","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PTE", "", (float)0 , (float)600, true, new PrecioAscendente(),false);
		
		ArrayList<Asiento> asientos = demian.getBusquedasHistoricas();
		//Los asientos deberían estar ordenados por orden descendente de precio
		int veces = 0;
		Float anterior = (float)0;
		for(Asiento unAsiento: asientos){
			if(veces == 0){
				anterior = unAsiento.getPrecio();
				veces++;
				continue;
			}
			assertTrue(unAsiento.getPrecio() > anterior);
			anterior = unAsiento.getPrecio();
			
		}
	}
	@Test
	public void siBuscoConOrdenPopularidadAndaBien(){
		//Hago que devuelva asientos de precios distintos
		String[][] asientosADevolver = new String[][] {{"ABC-123","123.45","P","V","D"},
														{"CARO-123","500.12","T","V","D"}, 
														{"ABC-123","12345.67","T","V","D"},
														{"CARO-123","5555.55","P","V","D"},
														{"ABC-123","2.5","E","V","D"},
														{"ABC-123","100.00","P","V","D"}};
		Mockito.when(aerolineaMock.asientosDisponibles("BUE", "LA", null, null, null, null)).thenReturn(asientosADevolver);
		//Busco Descendente
		demian.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PTE", "", (float)0 , (float)600, true, new PrecioDescendente(),false);
		//Compro el primero, que debería ser el mas caro, de vuelo "CARO":  
		demian.comprar(demian.getBusquedasHistoricas().get(0));
		//Ahora el vuelo "CARO" es el mas popular!
		//Busca florencia por orden de popularidad:
		florencia.buscarAsientos("BUE", "LA", new Fecha(0,0,0), "PTE", "", (float)0 , (float)600, true, new Popularidad(),false);
		//El primero debería ser el otro del vuelo "CARO"
		String vuelo = florencia.getBusquedasHistoricas().get(0).vuelo();
		assertTrue(vuelo.equals("CARO"));
	}

}
