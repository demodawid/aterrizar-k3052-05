package ventanas;

import java.awt.Color;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import aterrizar.Usuario;
import aterrizar.Viaje;

@SuppressWarnings({ "serial" })
public class BuscadorAsientosWindow extends SimpleWindow<BuscadorAsientos>{

	public BuscadorAsientosWindow(WindowOwner parent,Usuario usuario){
		super(parent, new BuscadorAsientos(usuario));
		this.getModelObject().buscar();
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
		.setCaption("Buscar")
		.onClick(new MessageSend(this.getModelObject(), "buscar"))
		.setAsDefault()
		.disableOnError();

	new Button(actionsPanel) //
		.setCaption("Limpiar")
		.onClick(new MessageSend(this.getModelObject(), "clear"));		
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Aterrizar.com | Seccion de busqueda");
		this.setTaskDescription("Ingrese los parámetros de búsqueda");

		super.createMainTemplate(mainPanel);

		this.createResultsGrid(mainPanel);
		this.createGridActions(mainPanel);

	}
	
	// *************************************************************************
	// * FORMULARIO DE BUSQUEDA 
	// *************************************************************************
		
		@Override
		protected void createFormPanel(Panel mainPanel) {
			Panel searchFormPanel = new Panel(mainPanel);
			searchFormPanel.setLayout(new ColumnLayout(2));

			new Label(searchFormPanel).setText("Origen").setForeground(Color.BLUE);
			new TextBox(searchFormPanel).bindValueToProperty("origen");
			
			new Label(searchFormPanel).setText("Destino").setForeground(Color.BLUE);
			new TextBox(searchFormPanel).bindValueToProperty("destino");
			
			new Label(searchFormPanel).setText("Fecha").setForeground(Color.BLUE);
			new TextBox(searchFormPanel).bindValueToProperty("fechaViaje");
		}
		
	// *************************************************************************
	// **  RESULTADOS DE LA BUSQUEDA 
	// *************************************************************************
		
			protected void createResultsGrid(Panel mainPanel) {
				Table<Viaje> table = new Table<Viaje>(mainPanel, Viaje.class);
				table.setHeigth(200);
				table.setWidth(450);

				table.bindItemsToProperty("resultados");
				table.bindValueToProperty("viajeSeleccionado");

				this.describeResultsGrid(table);
			}

			protected void describeResultsGrid(Table<Viaje> table) {
				new Column<Viaje>(table) //
					.setTitle("Aerolinea")
					.setFixedSize(80)
					.bindContentsToTransformer(new AerolineaNombreTransformer());

				Column<Viaje> vueloColumn = new Column<Viaje>(table);
				vueloColumn.setTitle("Vuelo");
				vueloColumn.setFixedSize(120);
				vueloColumn.bindContentsToTransformer(new CodigoVueloTransformer());

				Column<Viaje> asientoColumn = new Column<Viaje>(table);
				asientoColumn.setTitle("Asiento");
				asientoColumn.setFixedSize(60);
				asientoColumn.bindContentsToTransformer(new NumeroAsientoTransformer());
				
				Column<Viaje> precioColumn = new Column<Viaje>(table);
				precioColumn.setTitle("Precio");
				precioColumn.setFixedSize(60);
				precioColumn.bindContentsToProperty("precio");
				
				Column<Viaje> ubicacionCol = new Column<Viaje>(table);
				ubicacionCol.setTitle("Ubicacion");
				ubicacionCol.setFixedSize(80);
				ubicacionCol.bindContentsToTransformer(new UbicacionTransformer());
				
				Column<Viaje> claseCol = new Column<Viaje>(table);
				claseCol.setTitle("Clase");
				claseCol.setFixedSize(40);
				claseCol.bindContentsToTransformer(new ClaseTransformer());

			}
			
			protected void createGridActions(Panel mainPanel) {
				Panel actionsPanel = new Panel(mainPanel);
				actionsPanel.setLayout(new HorizontalLayout());

				Button comprar = new Button(actionsPanel);
				comprar.setCaption("Comprar");
				comprar.onClick(new MessageSend(this, "comprarViaje"));

				Button reservar = new Button(actionsPanel);
				reservar.setCaption("Reservar");
				reservar.onClick(new MessageSend(this, "reservarViaje"));
				
				
				Button cerrar = new Button(actionsPanel);
				cerrar.setCaption("Cerrar");
				cerrar.onClick(new MessageSend(this,"close"));

				// Deshabilitar los botones si no hay ningún elemento seleccionado en la grilla.
				NotNullObservable elementSelected = new NotNullObservable("viajeSeleccionado");
				reservar.bindEnabled(elementSelected);
				comprar.bindEnabled(elementSelected);
			}
		
			
		public void comprarViaje(){
			this.getModelObject().comprar(this);
		}
		
		public void reservarViaje(){
			this.getModelObject().reservar(this);
		}
}
