package aterrizar;

import aterrizar.Asiento;
import aterrizar.NivelImportancia;
import aterrizar.NivelNormal;
import aterrizar.NivelVip;
import aterrizar.Usuario;

public class UsuarioEstandar extends Usuario{

	private float gastosAcumulados;
	private NivelImportancia nivel;
	
	
	public UsuarioEstandar(String nombre, String apellido, String dni)
	{
		super(nombre,apellido,dni);
		this.gastosAcumulados = (float)0;
		this.nivel = new NivelNormal();
	}
	

	@Override
	public void comprarAsiento(Asiento unAsiento) throws Exception{
		float precioTotal = super.sistema.comprar(unAsiento, this);
		this.registrarCompra(precioTotal);
		this.asientosComprados.add(unAsiento);
	}
	
	@Override
	public void reservarAsiento(Asiento unAsiento){
		super.sistema.reservar(unAsiento,this);
		this.asientosReservados.add(unAsiento);		
	}
	
	public void registrarCompra(float precioTotal)
	{
		this.gastosAcumulados += precioTotal;
		
		if(gastosAcumulados > 100000)
			{
				this.nivel = new NivelVip();
			}
		
	}
	
	@Override
	public float impuestoAdicional()
	{
		return (float)0;
	}
	
	@Override
	public Boolean puedeListar(Asiento unAsiento)
	{
		return this.nivel.puedeVer(unAsiento);
	}
		
	public NivelImportancia getNivel()
	{
		return nivel;
	}
	
	public float getGastosAcumulados()
	{
		return gastosAcumulados;
	}


	public void setNivel(NivelImportancia nivel) {
		this.nivel = nivel;
	}
	
}