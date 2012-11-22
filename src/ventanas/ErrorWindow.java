package ventanas;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.ui.jface.builder.ErrorsPanel;

@SuppressWarnings("serial")
public class ErrorWindow extends Dialog<Object> {
	
	protected String mensaje;
	
	public ErrorWindow(Window owner,String mensaje)
	{
		super(owner, null);
		this.mensaje = mensaje;
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Aterrizar.com | Operacion NO Exitosa");	
		Panel bienvenidaPanel = new Panel(mainPanel);
		new Label(bienvenidaPanel).setText(this.mensaje);
		new Label(bienvenidaPanel).setText("Por favor, intentelo nuevamente.");
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		Button seguirBuscando = new Button(actionsPanel)
		.setCaption("Aceptar")
		.onClick(new MessageSend(this,"close"));
		
	}
	
	
	@Override
	protected ErrorsPanel createErrorsPanel(Panel mainPanel) {
		return null;
	}
}
