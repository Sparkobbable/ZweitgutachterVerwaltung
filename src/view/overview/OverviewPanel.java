package view.overview;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EventSource;
import model.Model;
import model.enums.EventId;
import view.eventsources.TableClickEventSource;
import view.panelstructure.DefaultViewPanel;
import view.tableModels.AbstractDataTableModel;

public abstract class OverviewPanel<T> extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;

	protected Model model;

	// UI-components
	protected AbstractDataTableModel<T> tableModel;
	protected JTable table;
	protected JScrollPane tableScrollPane;
	protected OverviewActionPanel actionPanel;

	public OverviewPanel(Model model, String title) {
		super(title);
		this.model = model;
		this.setLayout(new BorderLayout());

	}

	protected abstract AbstractDataTableModel<T> createTableModel();

	protected void addUIElements() {
		this.add(this.tableScrollPane, BorderLayout.CENTER);
		this.add(this.actionPanel, BorderLayout.PAGE_END);
	}

	protected void createUIElements() {
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
		return List.of(this.actionPanel, new TableClickEventSource(EventId.EDIT, this.table, () -> getSelectedRowIndex()));
	}

	protected void updateTableModel() {
		this.tableModel.updateData();
		this.repaint();
	}

}
