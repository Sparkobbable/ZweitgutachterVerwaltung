package view.overview;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import view.View;
import view.eventsources.TableClickEventSource;
import view.panelstructure.DefaultPanel;
import view.tableModels.AbstractDataTableModel;
import view.widgets.SearchField;

public abstract class OverviewPanel<T> extends DefaultPanel {

	private static final long serialVersionUID = 1L;

	protected Model model;

	// UI-components
	protected AbstractDataTableModel<T> tableModel;
	protected JTable table;
	protected JScrollPane tableScrollPane;
	protected OverviewActionPanel actionPanel;
	protected SearchField searchField;

	public OverviewPanel(Model model, String title) {
		super(title);
		this.model = model;
		this.setLayout(new BorderLayout(10,10));
		this.setBackground(View.background);

	}

	protected abstract AbstractDataTableModel<T> createTableModel();

	protected void addUIElements() {
		this.add(this.searchField, BorderLayout.PAGE_START);
		this.add(this.tableScrollPane, BorderLayout.CENTER);
		this.add(this.actionPanel, BorderLayout.PAGE_END);
	}

	protected void createUIElements() {
		this.searchField = new SearchField();
		
		this.tableModel = createTableModel();
		this.table = new JTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);

		this.table.setFillsViewportHeight(true);
		// TODO observe sorting behavior when bachelor thesis count >= 10
		this.table.setAutoCreateRowSorter(true);
	}

	protected int[] getSelectedRows() {
		return this.table.getSelectedRows();
	}

	protected int[] getSelectedRowIndex() {
		return IntStream.of(this.table.getSelectedRows()).map(this.table::convertRowIndexToModel).toArray();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return new LinkedList<EventSource>(List.of(this.actionPanel, new TableClickEventSource(EventId.EDIT, this.table, () -> getSelectedRowIndex())));
	}

	protected void updateTableModel() {
		this.tableModel.updateData();
		this.repaint();
	}

}
