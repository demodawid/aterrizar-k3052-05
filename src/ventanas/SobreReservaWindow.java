package ventanas;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.ui.jface.builder.ErrorsPanel;

public class SobreReservaWindow extends Dialog<SobreReservaModel>{
	protected SobreReservaModel modelo;

	public SobreReservaWindow(WindowOwner owner, SobreReservaModel model) {
		super(owner, model);
		this.modelo = model;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Aterrizar.com | Sobre Reservas");	
		Panel bienvenidaPanel = new Panel(mainPanel);
		new Label(bienvenidaPanel).setText("El asiento "+this.modelo.getCodAsiento()+" esta reservado.");
		new Label(bienvenidaPanel).setText("¿Que desea hacer?");
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		Button sobreReservar = new Button(actionsPanel)
		.setCaption("Sobrereservar")
		.onClick(new MessageSend(this,"sobreReservar"));
		
		Button seguirBuscando = new Button(actionsPanel)
		.setCaption("Seguir Buscando")
		.onClick(new MessageSend(this,"close"));
		
	}

	public void sobreReservar(){
		this.getModelObject().sobreReservar(this);
	}
	
	@Override
	protected ErrorsPanel createErrorsPanel(Panel mainPanel) {
		return null;
	}
	
	
}
