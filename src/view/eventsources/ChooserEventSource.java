package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JFileChooser;

import model.Action;
import model.enums.EventId;

public class ChooserEventSource extends SingleEventSource {
	
	private JFileChooser filechooser;
	
	public ChooserEventSource(EventId eventId, JFileChooser filechooser, Supplier<?>... params) {
		super(eventId, params);
		this.filechooser = filechooser;
	}

	@Override
	public void addEventHandler(Action action) {
		filechooser.addActionListener(e -> action.perform(params));
	}
}
