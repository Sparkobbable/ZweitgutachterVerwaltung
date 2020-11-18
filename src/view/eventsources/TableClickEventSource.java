package view.eventsources;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JTable;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;

/**
 * Handles the role of JButtons as EventSource
 */
public class TableClickEventSource extends SingleEventSource {
	private JTable table;

	public TableClickEventSource(EventId eventId, JTable table, Supplier<?>... params) {
		super(eventId, params);
		this.table = table;
	}

	@Override
	public void addEventHandler(Action action) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					action.perform(params);
				}
			}
		});
	}

}
