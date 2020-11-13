package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JComboBox;

import model.Action;
import model.enums.EventId;

public class ComboBoxEventSource extends SingleEventSource {
	
	private JComboBox<String> comboBox;
	
	public ComboBoxEventSource(EventId eventId, JComboBox<String> comboBox, Supplier<?>... params) {
		super(eventId, params);
		this.comboBox = comboBox;
	}

	@Override
	public void addEventHandler(Action action) {
		comboBox.addActionListener(e -> action.perform(params));
	}
}
