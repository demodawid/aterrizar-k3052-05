package homes;

import java.io.Serializable;
import java.util.List;

import aterrizar.AerolineaAdapter;
import aterrizar.Asiento;
import aterrizar.Usuario;

import uqbar.arena.persistence.annotations.PersistentClass;

import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentHome;

@Observable
@PersistentClass
public class HomeAsiento extends PersistentHome<Asiento> implements Serializable  {
	
	private static HomeAsiento instance;
	
	public static synchronized HomeAsiento getInstance() {
		if (instance == null) {
			instance = new HomeAsiento();
		}
		return instance;
	}

	private HomeAsiento() {
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