package aterrizar;

import java.util.ArrayList;

public class UsuarioEstandar extends Usuario {
	
	private Float comprasTotales;
	private NivelImportancia nivelImportancia;
	
	public UsuarioEstandar(String nombre, String apellido, String dni, SistemaDeComprasAterrizar aterrizar){
		super(nombre, apellido, dni, aterrizar);
		this.comprasTotales = (float) 0;
		this.nivelImportancia = new NivelNormal();
	}
	
	@Override
	public void comprar(Asiento unAsiento) {
		super.comprar(unAsiento);
		this.registrarCompra(unAsiento);
	}
	
	/**
	 * Registra la compra (aumenta comprasTotales) y cambia de Nivel de
	 * importancia si es necesario.
	 */
	private void registrarCompra(Asiento unAsiento) {
		this.comprasTotales += unAsiento.getPrecio();
		if(comprasTotales > 100000)
			this.nivelImportancia = new NivelVip();
	}

	@Override
	public Boolean puedeVer(Asiento unAsiento){
		return this.nivelImportancia.puedeVer(unAsiento);
	}

	@Override
	public Float adicionalPrecio() {
		return (float) 0;
	}
	
	/**
	 * Util para pruebas
	 */
	public void setComprasTotales(Float comprasTotales){
		this.comprasTotales = comprasTotales;
		if(this.comprasTotales > 100000)
			this.nivelImportancia = new NivelVip();
	}
	
	public ArrayList<Busqueda> getBusquedasHistoricas(){
		return this.busquedasHistoricas;
	}
	
	public Float getComprasTotales(){
		return this.comprasTotales;
	}
	
	public NivelImportancia getNivelImportancia(){
		return this.nivelImportancia;
	}
	public void reservar(Asiento unAsiento){
		aterrizar.reservar(unAsiento, this);
	}
	
	@Override
	public void comprarReserva(Asiento unAsiento){
		aterrizar.comprarReserva(unAsiento, this);
	}
}
