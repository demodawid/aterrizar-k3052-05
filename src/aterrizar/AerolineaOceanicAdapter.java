package aterrizar;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.lanchita.AerolineaLanchita;
import com.oceanic.AerolineaOceanic;
import com.oceanic.AsientoDTO;

public class AerolineaOceanicAdapter implements AerolineaAdapter {
	
	protected AerolineaOceanic oceanic;
	
	public AerolineaOceanicAdapter(){
		this.oceanic = AerolineaOceanic.getInstance();
	}
	
	public String convertirCodigo(String unCodigo){
		String codigo;
		String guion = "_";
		if (unCodigo.length() == 3 ){			
			codigo = unCodigo;			
			}
		else {
			codigo = (unCodigo + guion);		
		}
		return codigo;		
	}
	
	@Override
	public ArrayList<Asiento> buscarAsientos(String origen, String destino, Fecha salida, Fecha llegada, Usuario usuario){
		String origenOceanic = convertirCodigo(origen);
		String destinoOceanic = convertirCodigo(destino);
		List<AsientoDTO> asientosDTO = new ArrayList<AsientoDTO>();
		if (destino == null){
			asientosDTO.addAll(this.oceanic.asientosDisponiblesParaOrigen(origenOceanic, salida.comoString()));
		} else {
			asientosDTO.addAll(this.oceanic.asientosDisponiblesParaOrigenYDestino(origenOceanic, destinoOceanic, salida.comoString()));
		}
		
		ArrayList<Asiento> asientos = new ArrayList<Asiento> ();
		for (AsientoDTO unAsiento: asientosDTO){
			String codigo = unAsiento.getCodigoDeVuelo() +"-"+ unAsiento.getNumeroDeAsiento();
			String reservacion;
			if(unAsiento.getReservado())
				reservacion = "R";
			else
				reservacion = "D";
			String claseAsiento = "";
			switch (unAsiento.getClase()) {
			case "Ejecutiva":
				claseAsiento = "E";
				break;
			case "Primera":
				claseAsiento = "P";
				break;
			case "Turista":
				claseAsiento = "T";
				break;
			default:
				break;
			}
			String ubicacionAsiento = "";
			switch (unAsiento.getClase()) {
			case "Pasillo":
				ubicacionAsiento = "P";
				break;
			case "Ventana":
				ubicacionAsiento = "V";
				break;
			case "Centro":
				ubicacionAsiento = "C";
				break;
			default:
				break;
			}
			Integer num1, num2;
			String[] partido = unAsiento.getHoraDeLlegada().split(":");
			num1 = Integer.parseInt(partido[0]);
			num2 = Integer.parseInt(partido[1]);
			Time horaLleg = new Time(num1,num2,0);
			partido = unAsiento.getHoraDeSalida().split(":");
			num1 = Integer.parseInt(partido[0]);
			num2 = Integer.parseInt(partido[1]);
			Time horaSal = new Time(num1,num2,0);
			
			asientos.add(new Asiento(codigo, unAsiento.getPrecio().floatValue() , claseAsiento, ubicacionAsiento, reservacion,
						this, horaLleg, horaSal, (new LatinoAmericano()).convertir(unAsiento.getFechaDeSalida()) ));
		}
		return asientos;
		
	}
	
	public void comprar(Asiento unAsiento, Usuario unUsuario){
		
		String codVue = (unAsiento.getCodigo().split("-"))[0];
		Integer codAsi = Integer.parseInt((unAsiento.getCodigo().split("-"))[1]);
		
		Boolean resp = oceanic.comprarSiHayDisponibilidad(unUsuario.dni, codVue, codAsi);
		
		if(!resp)
			throw new NoPuedeComprarException();
		
	}
	
	public void reservar(Asiento unAsiento, Usuario unUsuario){
		String codVue = (unAsiento.getCodigo().split("-"))[0];
		Integer codAsi = Integer.parseInt((unAsiento.getCodigo().split("-"))[1]);
		if(!oceanic.reservar(unUsuario.dni, codVue, codAsi))
			throw new NoPuedeReservarException();
	}
	
	public void miAerolinea(AerolineaOceanic unaAero){
		this.oceanic = unaAero;
	}
}
	
	