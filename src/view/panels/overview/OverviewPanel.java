package view.panels.overview;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.search.SearchStrategy;
import model.Model;
import view.ViewProperties;
import view.panels.prototypes.AbstractActionPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.LinkedTable;
import view.widgets.SearchField;

@SuppressWarnings("serial") // should not be serialized
public abstract class OverviewPanel<T> extends DefaultPanel {

	protected Model model;

	// UI-components
	protected AbstractDataTableModel<T> tableModel;
	protected AbstractActionPanel<T> actionPanel;
	protected SearchField<T> searchField;

	protected JTable table;
	protected JScrollPane tableScrollPane;

	public OverviewPanel(Model model, String title) {
		super(title);
		this.model = model;
		this.setLayout(new BorderLayout(10, 10));
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

	}

	protected abstract AbstractDataTableModel<T> createTableModel();

	protected void addUIElements() {
		this.add(this.searchField, BorderLayout.PAGE_START);
		this.add(this.tableScrollPane, BorderLayout.CENTER);
		this.add(this.actionPanel, BorderLayout.PAGE_END);
	}

	protected void createUIElements() {
		this.searchField = new SearchField<>(this.createSearchStrategy(), t -> updateTableModel());

		this.tableModel = createTableModel();
		this.table = new LinkedTable<T>(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);

		this.table.setFillsViewportHeight(true);
		this.table.setAutoCreateRowSorter(true);
	}

	protected abstract SearchStrategy<T> createSearchStrategy();

	protected int[] getSelectedRows() {
		return this.table.getSelectedRows();
	}

	protected int[] getSelectedRowIndex() {
		return IntStream.of(this.table.getSelectedRows()).map(this.table::convertRowIndexToModel).toArray();
	}

	protected List<T> getSelectedElements() {
		return IntStream.of(this.table.getSelectedRows()).map(this.table::convertRowIndexToModel)
				.mapToObj(this.tableModel::getByIndex).collect(Collectors.toList());
	}

	protected void updateTableModel() {
		this.tableModel.updateData();
		this.repaint();
	}

}
