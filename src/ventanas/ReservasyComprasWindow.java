package ventanas;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.ui.jface.builder.ErrorsPanel;

import aterrizar.Asiento;
import aterrizar.SistemaDeComprasAterrizar;
import aterrizar.Usuario;

public class ReservasyComprasWindow extends TransactionalDialog<OperationModel>{

	protected Usuario usuario;
	protected OperationModel modelo;

	public ReservasyComprasWindow(WindowOwner owner, Usuario usuario,OperationModel modelo) {
		super(owner, modelo);
		this.usuario = usuario;
		this.modelo = modelo;
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Aterrizar.com");	
		new Label(mainPanel).setText(this.modelo.getLeyenda()+" de "+this.usuario.getNombre());
		this.createResultsGrid(mainPanel);
	}
	
	protected void createResultsGrid(Panel mainPanel) {
		Table<Asiento> table = new Table<Asiento>(mainPanel, Asiento.class);
		table.setHeigth(200);
		table.setWidth(450);

		table.bindItemsToProperty("asientos");

		this.describeResultsGrid(table);
	}
	
	protected void describeResultsGrid(Table<Asiento> table) {
		Column<Asiento> salidaColumn = new Column<Asiento>(table);
		salidaColumn.setTitle("Salida");
		salidaColumn.setFixedSize(95);
		salidaColumn.bindContentsToProperty("fechaSal");

		Column<Asiento> aerolineaCol = new Column<Asiento>(table);
		aerolineaCol.setTitle("Aerolinea");
		aerolineaCol.setFixedSize(100);
		aerolineaCol.bindContentsToTransformer(new AerolineaAsNombreTransformer());

		Column<Asiento> vueloCol = new Column<Asiento>(table);
		vueloCol.setTitle("Vuelo");
		vueloCol.setFixedSize(95);
		vueloCol.bindContentsToProperty("codigoDeVuelo");
		
		Column<Asiento> asientoColumn = new Column<Asiento>(table);
		asientoColumn.setTitle("Asiento");
		asientoColumn.setFixedSize(100);
		asientoColumn.bindContentsToProperty("numero");
		
		Column<Asiento> precioColumn = new Column<Asiento>(table);
		precioColumn.setTitle("Precio");
		precioColumn.setFixedSize(70);
		precioColumn.bindContentsToProperty("precio");

	}

	@Override
	protected void addActions(Panel actionsPanel) {
		actionsPanel.setLayout(new HorizontalLayout());
		Button cerrar = new Button(actionsPanel)
			.setCaption("Cerrar")
			.onClick(new MessageSend(this,"close"));
		
		/*this.onAccept(new Action() {
		@Override
		public <T> void execute(T... objects) {
			
		}
		
		@Override
		public void execute() {
			SistemaDeComprasAterrizar.getInstance().update(ReservasyComprasWindow.this.getModelObject());
		}
	  });*/
	}

	
}
