package ventanas;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.ui.jface.builder.ErrorsPanel;
import uqbar.arena.persistence.Configuration;

import aterrizar.Usuario;


@SuppressWarnings("serial")
public class WindowPrincipal extends SimpleWindow<Object> {
	Usuario usuario;
	
	{ Configuration.configure(); }
	
	@SuppressWarnings("unchecked")
	public WindowPrincipal(AterrizarApplication sistema,Usuario usuario) {
		super(sistema,usuario);
		this.usuario = usuario;
	}

	public void createOptionsPanel(Panel mainPanel){
		//Panel mainPanel = new Panel(panelPrincipal);
		mainPanel.setLayout(new HorizontalLayout());
		Button verReservas = new Button(mainPanel)
			.setCaption("Ver Reservas")
			.onClick(new MessageSend(this,"verReservas"));

		Button verCompras = new Button(mainPanel) //
			.setCaption("Ver Compras")
			.onClick(new MessageSend(this,"verCompras"));

		Button buscarViajes = new Button(mainPanel)//
			.setCaption("Buscar Viajes")
			.onClick(new MessageSend(this,"buscarViajes"));	
		}
	
	
	public void buscarViajes(){
		BuscadorAsientosWindow ventana = new BuscadorAsientosWindow(this,this.usuario);
		ventana.open();
	}
	 
	public void verReservas(){
		this.nuevaVentana(new ReservasyComprasWindow(this,this.usuario,new OperationModel(usuario.getAsientosReservados(),"Reservas")));
	}
	
	public void verCompras(){
		this.nuevaVentana(new ReservasyComprasWindow(this,this.usuario,new OperationModel(usuario.getAsientosComprados(),"Compras")));
	}


	@Override
	protected void addActions(Panel actionsPanel) {
		this.createOptionsPanel(actionsPanel);
		
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Aterrizar.com");	
		Panel bienvenidaPanel = new Panel(mainPanel);
		new Label(bienvenidaPanel).setText("Hola "+this.usuario.getNombre());
		new Label(bienvenidaPanel).setText("Que desea hacer?");
	}
	
	protected void nuevaVentana(Dialog<?> dialog) {;
		dialog.open();
	}
	
	@Override
	protected ErrorsPanel createErrorsPanel(Panel mainPanel) {
		return null;
	}

}
