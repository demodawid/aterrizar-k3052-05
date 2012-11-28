package aterrizar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oceanic.AerolineaOceanic;
import com.oceanic.AsientoDTO;
import aterrizar.AerolineaAdapter;
import aterrizar.Asiento;
import aterrizar.Usuario;
import aterrizar.NoPuedeComprarException;
import aterrizar.NoPuedeReservarException;
import aterrizar.Reservas;
import aterrizar.Viaje;

import org.uqbar.commons.model.Entity;
import uqbar.arena.persistence.annotations.PersistentClass;

@PersistentClass
public class AerolineaOceanicAdapter extends Entity implements AerolineaAdapter {
	
	private AerolineaOceanic _aerolineaOceanic = AerolineaOceanic.getInstance();
	private static Integer porcentajeImpuesto = 0;
	private ArrayList<Reservas> listaDeReservas = new ArrayList<Reservas>();
	private ArrayList<Asiento> asientosComprados = new ArrayList<Asiento>();
	private ArrayList<Asiento> asientosReservados = new ArrayList<Asiento>();
	private String nombre = "Oceanic";
	
	public int getPorcentajeImpuesto()
	{
		return porcentajeImpuesto;
	}

	public void comprar(Asiento asiento, Usuario usuario) {
		if(!this._aerolineaOceanic.comprarSiHayDisponibilidad(usuario.getDni(), asiento.getCodigoDeVuelo(), asiento.getNumero()))
			throw new NoPuedeComprarException();
		Reservas reserva = this.buscarReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
		if(reserva != null)
			this.listaDeReservas.remove(reserva);
		this.asientosComprados.add(asiento);
	}

	public ArrayList<Viaje> buscarAsientos(String origen, String destino, String salida, String horaSalida, String llegada, String horaLlegada, Integer cantEscalas, Usuario usuario) throws Exception {
		
		ArrayList<Asiento> misAsientos = new ArrayList<Asiento>();
		List<AsientoDTO> asientosDto = new ArrayList<AsientoDTO>();
		Asiento asientoActual;
		
		if(origen != null)
		{
			if(origen.length() == 2)
			{
				if(origen.equals("LA"))
				{
					origen = "S"+origen;
				}else{
					origen = "_"+origen;
				}
			}
		}
		else
			return null;
		
		if(destino != null)
		{
			if(destino.length()==2)
			{
				if(destino.equals("LA"))
				{
					destino = "S"+destino;
				}
				else if(!destino.equals("LA") && destino != null)
				{
					destino = "_"+destino;
				}
			}
		}
		
		if(destino == null)
		{
			asientosDto = this._aerolineaOceanic.asientosDisponiblesParaOrigen(origen, salida);			
		}else{
			asientosDto = masEscalas(origen,destino,salida,horaSalida,llegada,horaLlegada,cantEscalas);
		}

		if(asientosDto == null)
			return null;
		
		for (AsientoDTO unAsiento: asientosDto){
			Float precio = ((unAsiento.getPrecio()).floatValue()) * ((porcentajeImpuesto/100) + 1) + usuario.impuestoAdicional();
			
			String disponible;
			
			if(unAsiento.getReservado())
			{
				 disponible = "R";				
			}else{
				 disponible = "D";
			}
		
		
			asientoActual = new Asiento(unAsiento.getOrigen(),unAsiento.getDestino(),unAsiento.getFechaDeSalida(),unAsiento.getFechaDeLlegada(),unAsiento.getHoraDeSalida(),unAsiento.getHoraDeLlegada(),unAsiento.getNumeroDeAsiento(), unAsiento.getCodigoDeVuelo(),precio, unAsiento.getClase(),unAsiento.getUbicacion(),disponible , this);
			if( usuario.puedeListar(asientoActual) )
				misAsientos.add(asientoActual);
		}
		
			ArrayList<Viaje> viajes;
			viajes = armarViajeConAsientos(misAsientos,origen,destino,null,null,null);
		
			return viajes;
	}

	public void reservar(Asiento asiento, Usuario usuario)
	{
		if(!this._aerolineaOceanic.estaReservado(asiento.getCodigoDeVuelo(),asiento.getNumero())){
			
			this._aerolineaOceanic.reservar(usuario.getDni(),asiento.getCodigoDeVuelo(),asiento.getNumero());
			
			this.asientosReservados.add(asiento);
		}
		else
		{
			throw new NoPuedeReservarException();
		}
		
	}
	
	private Reservas buscarReserva(String codigoDeVuelo, Integer numAsiento)
	{
		Iterator<Reservas> listaTemp = this.listaDeReservas.iterator();
		
		while(listaTemp.hasNext())
		{
			Reservas reservaActual = listaTemp.next();
			if(reservaActual.codigoDeVuelo.equals(codigoDeVuelo) && reservaActual.numAsiento.equals(numAsiento))
				return reservaActual;

		}
		
		return null;
	}
	
	public void actualizarReserva(Asiento asiento)
	{
		Reservas reserva = this.buscarReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
		
		if(reserva != null)
		{
			Usuario usuario = reserva.listReserva.remove(0);
			this._aerolineaOceanic.reservar(usuario.getDni(),asiento.getCodigoDeVuelo(),asiento.getNumero());
		}
	}


	private List<AsientoDTO> masEscalas(String origen, String destino,String feSalida, String horaSalida, String feLlegada, String horaLlegada, Integer cantEscalas) throws Exception
	{
			
			List<AsientoDTO> asientosEscala = new ArrayList<AsientoDTO>();
			List<AsientoDTO> asientos = new ArrayList<AsientoDTO>();
			List<AsientoDTO> escalasTemp = null;
		
			if(cantEscalas > 0) 
				asientos = this._aerolineaOceanic.asientosDisponiblesParaOrigen(origen, feSalida);
			else if(cantEscalas == 0)
				return this._aerolineaOceanic.asientosDisponiblesParaOrigenYDestino(origen, destino, feSalida);
			else
				return null;
			
			Iterator<AsientoDTO> listaTemp = asientos.iterator();
			if(!listaTemp.hasNext())
				return null;
			while(listaTemp.hasNext())
			{
				AsientoDTO asientoActual = listaTemp.next();

					if(asientoActual.getDestino().equals(destino))
					{
						asientosEscala.add(asientoActual);
					}else{
						int escalas = cantEscalas-1;
						escalasTemp = masEscalas(asientoActual.getDestino(), destino, asientoActual.getFechaDeLlegada(), asientoActual.getHoraDeLlegada(),feLlegada, horaLlegada, escalas);
						if(escalasTemp != null){
							asientosEscala.add(asientoActual);
							asientosEscala.addAll(escalasTemp);
						}
							
						
					}
				}
					

				
			//}

		return asientosEscala;
		
	}
	
	public ArrayList<Viaje> armarViajeConAsientos(ArrayList<Asiento> asientos, String origen, String destino, Asiento primero, Asiento segundo, Asiento tercero) throws Exception
	{
		ArrayList<Viaje> ListaViajes = new ArrayList<Viaje>();
		Iterator<Asiento> asiento = asientos.iterator();
		while (asiento.hasNext())
		{
			Asiento asientoActual = asiento.next();
			
			if(asientoActual.getOrigen().equals(origen) && asientoActual.getDestino().equals(destino) || (origen == null || destino == null))
			{
				if(primero == null)
					primero = asientoActual;

				else {
					if(primero!=null && segundo == null)
						segundo = asientoActual;
				
				else if(primero!=null && segundo != null && tercero == null)
					tercero = asientoActual;
					}
				
				Viaje viaje = new Viaje(primero,segundo,tercero);
				ListaViajes.add(viaje);
				primero=null;
				segundo=null;
				tercero=null;
			}
			else
			{
				if(asientoActual.getOrigen().equals(origen) && !asientoActual.getDestino().equals(destino))
				{
					if(primero == null)
						primero = asientoActual;
					
					else if(primero != null && segundo == null)
						segundo = asientoActual;
					
					else if(primero != null && segundo != null && tercero == null)
						tercero = asientoActual;
					
					
					ListaViajes.addAll(armarViajeConAsientos(asientos, asientoActual.getDestino(), destino, primero, segundo, tercero));
				
				}
			}
			
		}
		
		return ListaViajes;
	}

	public int getPopularidad(Viaje unViaje){
		int cantAsientos = 1;
		int popularidad = this.cantidadDeOcurrencias(this.asientosComprados,unViaje.getAsientoUno().getCodigoDeVuelo());
		if(unViaje.getAsientoDos() != null ){
			popularidad += this.cantidadDeOcurrencias(this.asientosComprados,unViaje.getAsientoDos().getCodigoDeVuelo());
			cantAsientos++;
		}
		if(unViaje.getAsientoTres() != null){
			popularidad += this.cantidadDeOcurrencias(this.asientosComprados,unViaje.getAsientoTres().getCodigoDeVuelo());
			cantAsientos++;
		}
		return popularidad / cantAsientos;
	}
	
	private int cantidadDeOcurrencias(ArrayList<Asiento> asientosComprados,String codVuelo){
		int popularidad = 0;
		Iterator<Asiento> iterador = asientosComprados.iterator();
		while(iterador.hasNext()){
			Asiento asientoActual = iterador.next();
			if(asientoActual.getCodigoDeVuelo().equals(codVuelo))
				popularidad++;
		}
		return popularidad;
	}

	public void setMock(Object aerolineaMock) {
		this._aerolineaOceanic = (AerolineaOceanic) aerolineaMock;	
	}
	
	public ArrayList<Reservas> getAsientosReservados() {
		return listaDeReservas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void sobreReservar(Asiento asiento, Usuario usuario) {
		
		Reservas reserva = this.buscarReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
		
		if(reserva == null)
		{
			this.listaDeReservas.add(new Reservas(asiento.getCodigoDeVuelo(),asiento.getNumero(),usuario));
		}
		else
		{
			if(reserva.listReserva.contains(usuario))
				throw new NoPuedeReservarException();
			reserva.agregarReserva(usuario);
		}
	}
	
}