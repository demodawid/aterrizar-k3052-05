package ventanas;

import homes.HomeAsiento;
import homes.HomeOperationModel;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import aterrizar.Asiento;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;
import org.uqbar.commons.model.Entity;

@Observable
@PersistentClass
public class OperationModel extends Entity {

	private String label;
	private List<Asiento> asientos;
	
	public OperationModel(){
		
	}

	public OperationModel(List<Asiento> asientos, String label) {
		this.label = label;
		this.asientos = asientos;
		HomeOperationModel.getInstance().create(this);
		HomeOperationModel.getInstance().update(this);
	}
	@PersistentField
	public String getLeyenda() {
		return this.label;
	}

	public List<Asiento> getAsientos() {
		return asientos;
	}

	public void setAsientos(List<Asiento> asientos) {
		this.asientos = asientos;
	}

}
