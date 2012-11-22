package aterrizar;

import com.lanchita.*;
import com.lanchita.excepciones.EstadoErroneoException;
import aterrizar.AerolineaAdapter;
import aterrizar.Asiento;
import aterrizar.Usuario;
import aterrizar.NoPuedeComprarException;
import aterrizar.NoPuedeReservarException;
import aterrizar.Reservas;
import aterrizar.Viaje;

import java.util.ArrayList;
import java.util.Iterator;

public class AerolineaLanchitaAdapter implements AerolineaAdapter {

	private AerolineaLanchita _aerolineaLanchita = AerolineaLanchita.getInstance();
	private static Integer porcentajeImpuesto = 15;
	private ArrayList<Reservas> listaDeSobreReservas = new ArrayList<Reservas>();
	private ArrayList<Asiento> asientosComprados = new ArrayList<Asiento>();
	private ArrayList<Asiento> asientosReservados = new ArrayList<Asiento>();
	private String nombre = "Lanchita";

	public void comprar(Asiento asiento, Usuario comprador) throws Exception {
		String codAsiento = asiento.getCodigoDeVuelo()+"-"+asiento.getNumero();
		
		try{
			this._aerolineaLanchita.comprar(codAsiento);
			
			Reservas reserva = this.buscarSobreReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
			
			if(reserva != null)
			{
				this.listaDeSobreReservas.remove(reserva);
			}
					
			this.asientosComprados.add(asiento);
		} catch(EstadoErroneoException e){
			throw new NoPuedeComprarException();
		}
		
	}

	public ArrayList<Viaje> buscarAsientos(String origen, String destino,	String salida, String horaSalida, String llegada, String horaLlegada, Integer cantEscalas, Usuario usuario) throws Exception
	{
		Asiento asientoActual;
		ArrayList<Asiento> misAsientos = new ArrayList<Asiento>();
		String[][] asientos = new String[][] {};
		String[][] asientosTemp = new String[][] {};
		String[][] asientosTot;
		String[][] asientosRes;

		if(this.asientosReservados.size() != 0)
		{
			asientosRes = new String[this.asientosReservados.size()][12];
		}
		else
		{
			asientosRes = null;
		}
		
		asientosTemp = this._aerolineaLanchita.asientosDisponibles(origen, destino, salida, horaSalida, null, horaLlegada);
		
		int i = 0;
		
        for(Asiento unAsiento: this.asientosReservados){
        	asientosRes[i][0] = unAsiento.getCodigoDeVuelo()+"-"+String.valueOf(unAsiento.getNumero());
        	asientosRes[i][1] = String.valueOf(unAsiento.getPrecio());
        	asientosRes[i][2] = unAsiento.getClase();
        	asientosRes[i][3] = unAsiento.getUbicacion();
        	asientosRes[i][4] = unAsiento.getEstado();
        	asientosRes[i][5] = null;
        	asientosRes[i][6] = unAsiento.getHoraSal();
        	asientosRes[i][7] = unAsiento.getHoraLle();
        	asientosRes[i][8] = unAsiento.getOrigen();
        	asientosRes[i][9] = unAsiento.getDestino();
        	asientosRes[i][10] = unAsiento.getFechaSal();
            asientosRes[i][11] = unAsiento.getFechaLle();
            i++;
        }
        
		if(asientosTemp != null && asientosRes != null)
		{
			asientosTot = new String[(asientosTemp.length + asientosRes.length)][12];
	        System.arraycopy(asientosTemp, 0, asientosTot, 0, asientosTemp.length);  
	        System.arraycopy(asientosRes, 0, asientosTot, asientosTemp.length, asientosRes.length);
		}else{
			if(asientosTemp != null && asientosRes == null)
			{
				asientosTot = new String[(asientosTemp.length)][12];
		        System.arraycopy(asientosTemp, 0, asientosTot, 0, asientosTemp.length);  

			}else{
				if(asientosTemp == null && asientosRes != null)
				{
					asientosTot = new String[(asientosRes.length)][12];			        
			        System.arraycopy(asientosRes, 0, asientosTot, 0, asientosRes.length);
				}else{
					return null;
				}
			}
		}

		if(cantEscalas > 0)
			asientos = buscarEscalas(asientosTot, origen, destino,salida,llegada,horaSalida,horaLlegada,cantEscalas);
		else
			asientos = asientosTot;
		
		int numAsiento;
		String codigoDeVuelo;
		if(asientos.length > 0)
		{
			for (String[] unAsiento: asientos){
				Float precio = (Float.valueOf(unAsiento[1])) * ((porcentajeImpuesto/100) + 1) + usuario.impuestoAdicional();
				
				numAsiento = Integer.parseInt(unAsiento[0].substring(15,unAsiento[0].length()));
				codigoDeVuelo = unAsiento[0].substring(0,14);
				asientoActual = new Asiento(unAsiento[8],unAsiento[9],unAsiento[10],unAsiento[11],unAsiento[6],unAsiento[7],numAsiento,codigoDeVuelo, precio, unAsiento[2],unAsiento[3], unAsiento[4], this);
				
				if( usuario.puedeListar(asientoActual) )
					misAsientos.add(asientoActual);
			}
		}
		
		ArrayList<Viaje> viajes;
		viajes = armarViajeConAsientos(misAsientos,origen,destino,null,null,null);
	
		return viajes;
	}

	private String[][] buscarEscalas(String[][] listaTemp, String origen, String destino,String fechaSal, String fechaLle,String horaSalida, String horaLlegada, Integer cantEscalas) throws Exception
	{
		int maxLength = 0;
		int i = 0, p = 0;
		if(listaTemp != null)
			maxLength = (int) Math.pow(listaTemp.length,cantEscalas+1);
		String[][] asientosConEscalas = new String[maxLength][12];
		String[][] asientosTemp;
		String[][] listaDeAsientosDispo;
		String[][] asientosTot;
		String[][] asientosRes;

		if(cantEscalas >= 0) 
		{
			
			while(i < listaTemp.length)
			{
					if(listaTemp[i][9].equals(destino) || (destino == null || origen == null))
					{
						asientosConEscalas[p] = listaTemp[i];
						p++;
					}
					else 
					{
						listaDeAsientosDispo = this._aerolineaLanchita.asientosDisponibles(listaTemp[i][9], null, listaTemp[i][11], listaTemp[i][7], fechaLle, horaLlegada);
						
						if(this.asientosReservados.size() != 0)
						{
						asientosRes = new String[this.asientosReservados.size()][12];
						}else{
						asientosRes = null;
						}
					 
						    int j = 0;
        					for(Asiento unAsiento: this.asientosReservados){
					        	asientosRes[j][0] = unAsiento.getCodigoDeVuelo()+"-"+String.valueOf(unAsiento.getNumero());
					        	asientosRes[j][1] = String.valueOf(unAsiento.getPrecio());
					        	asientosRes[j][2] = unAsiento.getClase();
					        	asientosRes[j][3] = unAsiento.getUbicacion();
					        	asientosRes[j][4] = unAsiento.getEstado();
					        	asientosRes[j][5] = null;
					        	asientosRes[j][6] = unAsiento.getHoraSal();
					        	asientosRes[j][7] = unAsiento.getHoraLle();
					        	asientosRes[j][8] = unAsiento.getOrigen();
					        	asientosRes[j][9] = unAsiento.getDestino();
					        	asientosRes[j][10] = unAsiento.getFechaSal();
					            asientosRes[j][11] = unAsiento.getFechaLle();
					            j++;
        					}
		
        					if(listaDeAsientosDispo != null && asientosRes != null)
        					{
        						asientosTot = new String[(listaDeAsientosDispo.length + asientosRes.length)][12];
    					        System.arraycopy(listaDeAsientosDispo, 0, asientosTot, 0, listaDeAsientosDispo.length);  
    					        System.arraycopy(asientosRes, 0, asientosTot, listaDeAsientosDispo.length, asientosRes.length);
        					}else{
        						if(listaDeAsientosDispo != null && asientosRes == null)
        						{
        							asientosTot = new String[(listaDeAsientosDispo.length)][12];
        					        System.arraycopy(listaDeAsientosDispo, 0, asientosTot, 0, listaDeAsientosDispo.length);  
        					        
        						}else{
        							if(listaDeAsientosDispo == null && asientosRes != null)
        							{
        								asientosTot = new String[(asientosRes.length)][12];
        						        System.arraycopy(asientosRes, 0, asientosTot, 0, asientosRes.length);
        							}else{
        								return null;
        							}
        						}
        					}
					       					        
					
					    int escalas = cantEscalas-1;
						asientosTemp = buscarEscalas(asientosTot,listaTemp[i][9],destino,listaTemp[i][11],fechaLle,listaTemp[i][7],horaLlegada,escalas);
						
						if(asientosTemp != null)
						{
							System.arraycopy(asientosTemp, 0, asientosConEscalas ,p, asientosTemp.length);
							p += asientosTemp.length;
						}
						
					}
				
				i++;
			}
			
		}
		else
			return null;

		return asientosConEscalas;
	}
	
	public void reservar(Asiento asiento, Usuario usuario)
	{
		try{			
			String codAsiento = asiento.getCodigoDeVuelo()+"-"+asiento.getNumero();
						
			this._aerolineaLanchita.reservar(codAsiento, usuario.dni);
			
			this.asientosReservados.add(asiento);
		}
		catch(EstadoErroneoException e)
		{
			throw new NoPuedeReservarException();
		}
		
	}
	
	private Reservas buscarSobreReserva(String codigoDeVuelo, Integer numAsiento)
	{
		Iterator<Reservas> listaTemp = this.listaDeSobreReservas.iterator();
		
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
		Reservas sobreReserva = this.buscarSobreReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
		
		if(sobreReserva != null)
		{
			Usuario usuario = sobreReserva.listReserva.remove(0);
			
			String codAsiento = asiento.getCodigoDeVuelo()+"-"+asiento.getNumero();
			
			this._aerolineaLanchita.reservar(codAsiento, usuario.dni);
			
			usuario.asientosReservados.add(asiento); //usuario estandar (bien cabeza escribir estandar)
		}
	}
	
	public int getPorcentajeImpuesto()
	{
		return porcentajeImpuesto;
	}
	
	public ArrayList<Viaje> armarViajeConAsientos(ArrayList<Asiento> asientos, String origen, String destino, Asiento primero, Asiento segundo, Asiento tercero) throws Exception
	{
		ArrayList<Viaje> ListaViajes = new ArrayList<Viaje>();
		Iterator<Asiento> asiento = asientos.iterator();
		while (asiento.hasNext())
		{
			Asiento asientoActual = asiento.next();
			
			if((asientoActual.getOrigen().equals(origen) && asientoActual.getDestino().equals(destino)) || ( destino == null || origen == null))
			{
				if(primero == null)
					primero = asientoActual;

				else if(primero!=null && segundo == null)
					segundo = asientoActual;
				
				else if(primero!=null && segundo != null && tercero == null)
					tercero = asientoActual;
				
				Viaje viaje = new Viaje(primero,segundo,tercero);
				ListaViajes.add(viaje);
				primero = null;
				segundo = null;
				tercero = null;
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
					
					
					armarViajeConAsientos(asientos, asientoActual.getDestino(), destino, primero, segundo, tercero);
				
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
	
	public void setMock(Object aerolinea){
		this._aerolineaLanchita = (AerolineaLanchita)aerolinea;
	}
	
	public ArrayList<Asiento> getAsientosReservados() {
		return asientosReservados;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void sobreReservar(Asiento asiento, Usuario usuario) {
		Reservas sobreReserva = this.buscarSobreReserva(asiento.getCodigoDeVuelo(),asiento.getNumero());
		
		if(sobreReserva == null)
		{
			this.listaDeSobreReservas.add(new Reservas(asiento.getCodigoDeVuelo(),asiento.getNumero(),usuario));
		}
		else
		{
			if(sobreReserva.listReserva.contains(usuario))
				throw new NoPuedeReservarException();
			sobreReserva.agregarReserva(usuario);
		}		
	}
}
