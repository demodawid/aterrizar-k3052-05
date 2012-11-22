package aterrizar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aterrizar.Flexible;
import aterrizar.AerolineaLanchitaAdapter;
import aterrizar.AerolineaAdapter;
import aterrizar.Asiento;
import aterrizar.Hora;
import aterrizar.Usuario;
import aterrizar.AerolineaOceanicAdapter;
import aterrizar.Filtrar;
import aterrizar.Ordenar;
import aterrizar.Viaje;
import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentHome;

@Observable
public class SistemaDeComprasAterrizar extends PersistentHome<Asiento> implements Serializable  {
	
	private static SistemaDeComprasAterrizar uniqueInstance;
	
	ArrayList<AerolineaAdapter> aerolineas;
	
	public AerolineaLanchitaAdapter getAerolineas(){
		AerolineaLanchitaAdapter aerolinea = new AerolineaLanchitaAdapter();
		return aerolinea;
		
	}
	
	
	private SistemaDeComprasAterrizar()
	{
		this.aerolineas = new ArrayList<AerolineaAdapter>();
		this.aerolineas.add(new AerolineaLanchitaAdapter());
		this.aerolineas.add(new AerolineaOceanicAdapter());
	}
	
	public void setAerolineas(ArrayList<AerolineaAdapter> aerolineas)
	{
		this.aerolineas = aerolineas;
	}
	
    public static synchronized SistemaDeComprasAterrizar getInstance() {
        if (uniqueInstance == null) 
        {
            uniqueInstance = new SistemaDeComprasAterrizar();
        }
        return uniqueInstance;
    }
	///////VEERRR
    public void create(Asiento asiento) {
		super.create(asiento);
	}
    
    public List<Asiento> search(String fechaSal,int numero, String codigoDeVuelo, Float precio, String clase, String ubicacion,AerolineaAdapter aerolinea) {
		Asiento example = new Asiento(fechaSal, numero, codigoDeVuelo, precio, clase, ubicacion, aerolinea);
		return this.searchByExample(example);
	}

	@Override
	public Class<Asiento> getEntityType() {
		return Asiento.class;
	}

	@Override
	public Asiento createExample() {
		return new Asiento();
	}
    
	public ArrayList<Viaje> buscarAsientos(String origen, String destino, String salida, String horaSalida, String llegada, String horaLlegada,Integer cantEscalas, Usuario usuario,Ordenar ordenar, Filtrar filtros) throws Exception
	{
			Flexible fechaFlexS = new Flexible(salida);
			Flexible fechaFlexLL = new Flexible(llegada);
			String fechaSalida;
			String fechaLlegada;
			

			if(fechaFlexLL.getFecha() == null)
			{
				fechaLlegada = null;
			}
			else
			{
				fechaLlegada = (String.format("%02d", (fechaFlexLL.getFecha().get(Calendar.DAY_OF_MONTH))) + "/" + (String.format("%02d",(fechaFlexLL.getFecha().get(Calendar.MONTH)+1))) + "/" + Integer.toString((fechaFlexLL.getFecha().get(Calendar.YEAR))));
			}
			
			
			if(fechaFlexS.getFecha() == null)
			{
				fechaSalida = null;
			}
			else
			{
				fechaSalida = (String.format("%02d",(fechaFlexS.getFecha().get(Calendar.DAY_OF_MONTH))) +"/"+ (String.format("%02d",(fechaFlexS.getFecha().get(Calendar.MONTH)+1))) + "/" + Integer.toString((fechaFlexS.getFecha().get(Calendar.YEAR))));
			}
			
				
	     	Hora tipoHoraS = new Hora(horaSalida);
			Hora tipoHoraLl = new Hora(horaLlegada);
			String horaS = tipoHoraS.hora;
			String horaLl = tipoHoraLl.hora;
			
			ArrayList<Viaje> viajes = new ArrayList<Viaje>();
			for(AerolineaAdapter unaAerolinea: aerolineas)
			{
				if(unaAerolinea.buscarAsientos(origen, destino, fechaSalida , horaS, fechaLlegada, horaLl, cantEscalas, usuario)!=null)
					viajes.addAll(unaAerolinea.buscarAsientos(origen, destino, fechaSalida , horaS, fechaLlegada, horaLl, cantEscalas, usuario));
			}
			
			if(filtros != null)
				viajes = filtros.aplicarFiltros(viajes);
			
			if(ordenar != null)
				viajes = ordenar.ordenarLista(viajes);

			return viajes;
	}
			
	public AerolineaAdapter getLanchita() {
		return aerolineas.get(0);
	}
	
	public AerolineaAdapter getOceanic() {
		return aerolineas.get(1);
	}
	
	public float comprar(Asiento asiento, Usuario usuario) throws Exception
	{
		asiento.getAerolinea().comprar(asiento, usuario);
		float precioTotal = (asiento.getPrecio()*((asiento.getAerolinea().getPorcentajeImpuesto()/100) + 1)) + usuario.impuestoAdicional();
	
		return precioTotal;
	}
	
	public void reservar(Asiento asiento, Usuario usuario) {
		asiento.getAerolinea().reservar(asiento,usuario);
	}

	public void sobreReservar(Asiento asiento, Usuario usuario) {
		asiento.getAerolinea().sobreReservar(asiento,usuario);		
	}

}