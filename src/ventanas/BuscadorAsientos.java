package ventanas;

import homes.HomeAsiento;

import java.io.Serializable;
import java.util.List;
import org.uqbar.commons.utils.Observable;
import java.util.ArrayList;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import aterrizar.FechaInvalidaException;
import aterrizar.Asiento;
import aterrizar.SistemaDeComprasAterrizar;
import aterrizar.Usuario;
import aterrizar.NoPuedeComprarException;
import aterrizar.NoPuedeReservarException;
import aterrizar.Viaje;
import uqbar.arena.persistence.Configuration;

@Observable
public class BuscadorAsientos  implements Serializable{
	private Usuario usuario;
	private String origen;
	private String destino;
	private String fechaViaje;
	private List<Viaje> resultados;
	private Viaje viajeSeleccionado;


	// ********************************************************
	// ** Acciones
	// ********************************************************
	
	public BuscadorAsientos(Usuario usuario){
		this.usuario = usuario;
	}
	
	public void buscar(){
		try {
			if(this.fechaViaje != null && this.fechaViaje.equals(""))
				this.fechaViaje = null;
			if(this.origen != null && this.origen.equals(""))
				this.origen = null;
			if(this.destino != null && this.destino.equals(""))
				this.destino = null;
			this.setResultados(SistemaDeComprasAterrizar.getInstance().buscarAsientos(this.origen, this.destino, this.fechaViaje, null, this.fechaViaje, null, 0, this.usuario, null, null));
		} catch (FechaInvalidaException e) {
			throw new UserException("Fecha invalida, por favor introduce una fecha valida.");
		} catch (Exception e2){
			throw new UserException("Otro error");
		}
	}
	

	public void clear() {
		this.destino = null;
		this.origen = null;
		this.fechaViaje = null;
	}

	
	// ********************************************************
	// ** Accessors
	// ********************************************************
	
	
	
	public List<Viaje> getResultados() {
		return resultados;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getFechaViaje() {
		return fechaViaje;
	}

	public void setFechaViaje(String fechaViaje) {
		this.fechaViaje = fechaViaje;
	}

	public Viaje getViajeSeleccionado() {
		return viajeSeleccionado;
	}

	public void setViajeSeleccionado(Viaje viajeSeleccionado) {
		this.viajeSeleccionado = viajeSeleccionado;
	}

	private void setResultados(ArrayList<Viaje> asientos) {
		this.resultados = asientos;
	}
	
	

	public void comprar(BuscadorAsientosWindow window){
		Asiento elAsiento = this.viajeSeleccionado.getAsientoUno();
		try{
			this.usuario.comprarAsiento(elAsiento);
			this.buscar();
			HomeAsiento.getInstance().update(elAsiento);
			ExitoWindow ventanaExito = new ExitoWindow(window,"El asiento "+elAsiento.getCodigoDeVuelo()+"-"+elAsiento.getNumero()+" ha sido comprado exitosamente.");
			ventanaExito.open();
		}catch(NoPuedeComprarException e){
			ErrorWindow ventanaError = new ErrorWindow(window,"Ha ocurrido un error en su compra, el asiento no se encuentra disponible.");
			ventanaError.open();
		}catch(Exception e2){
			System.out.println("Error.");
		}
	}

	public void reservar(BuscadorAsientosWindow buscadorAsientosWindow) {
		Asiento elAsiento = this.viajeSeleccionado.getAsientoUno();
		try
		{
			this.usuario.reservarAsiento(elAsiento);
			this.buscar();
			HomeAsiento.getInstance().update(elAsiento);
			ExitoWindow ventanaOk = new ExitoWindow(buscadorAsientosWindow,"El asiento"+elAsiento.getCodigoDeVuelo()+"-"+elAsiento.getNumero()+" fue reservado exitosamente.");
			ventanaOk.open();
		} 
		catch(NoPuedeReservarException e)
		{
				SobreReservaWindow ventana = new SobreReservaWindow(buscadorAsientosWindow,new SobreReservaModel(elAsiento,this.usuario));
				ventana.open();
		}
	}
	
}
