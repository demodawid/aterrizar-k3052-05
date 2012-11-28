package homes;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import aterrizar.Asiento;



import java.io.Serializable;
import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.PersistentHome;
import ventanas.OperationModel;

@Observable
@PersistentClass
public class HomeOperationModel extends PersistentHome<OperationModel> implements Serializable{

	private String label;
	private List<Asiento> asientos;
	private static HomeOperationModel instance;
	
	public static synchronized HomeOperationModel getInstance() {
		if (instance == null) {
			instance = new HomeOperationModel();
		}
		return instance;
	}
	
	private HomeOperationModel() {
	}
	
	public void create(OperationModel model) {
		super.create(model);
	}
	
	public HomeOperationModel(List<Asiento> asientos, String label) {
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
	
	@Override
	public Class<OperationModel> getEntityType() {
		return OperationModel.class;
	}

	@Override
	public OperationModel createExample() {
		return new OperationModel();
	}

}
