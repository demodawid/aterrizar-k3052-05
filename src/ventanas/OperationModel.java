package ventanas;

import homes.HomeAsiento;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import aterrizar.Asiento;

@Observable
public class OperationModel {

	private String label;
	private List<Asiento> asientos;

	public OperationModel(List<Asiento> asientos, String label) {
		this.label = label;
		this.asientos = asientos;
	}

	public String getLeyenda() {
		return this.label;
	}

	public List<Asiento> getAsientos() {
		return asientos;
	}

	public void setAsientos(List<Asiento> asientos) {
		this.asientos = asientos;
	}

	{
		for (Asiento asiento : asientos) {

			HomeAsiento.getInstance().update(asiento);

		}

	}
}
