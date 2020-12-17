package view.panels.overview;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import controller.events.EventSource;
import controller.search.SearchStrategy;
import model.Model;
import view.ViewProperties;
import view.panels.prototypes.AbstractActionPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.LinkedTable;
import view.widgets.SearchField;

/**
 * Abstract {@link DefaultPanel} presenting an overview over the given type <T>. 
 * <p>
 * It serves convenience methods and is supposed to be specified with the exact data, {@link EventSource}, UI-Elements and strategies.
 * <p>
 * This {@link DefaultPanel} contains a {@link JTable} combined with a {@link SearchField} as well as an {@link AbstractActionPanel} for adding actions.
 *
 * @param <T>	The type of the objects to be presented
 */
@SuppressWarnings("serial") // should not be serialized
public abstract class OverviewPanel<T> extends DefaultPanel {

	protected Model model;

	// UI-components
	protected AbstractDataTableModel<T> tableModel;
	protected AbstractActionPanel<T> actionPanel;
	protected SearchField<T> searchField;

	protected JTable table;
	protected JScrollPane tableScrollPane;

	/**
	 * Creates a new {@link OverviewPanel}
	 * @param model	Needs the {@link Model} as data access
	 * @param title	Title of the Panel
	 */
	public OverviewPanel(Model model, String title) {
		super(title);
		this.model = model;
		this.setLayout(new BorderLayout(10, 10));
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

	}

	/**
	 * The {@link TableModel} containing the data to be presented is created here
	 * <p>
	 * Needs to be overridden.
	 * @return	Returns the {@link AbstractDataTableModel} of the given type <T>
	 */
	protected abstract AbstractDataTableModel<T> createTableModel();

	/**
	 * Adds the created UI-Elements
	 * {@link #searchField}, {@link #tableScrollPane} and {@link #actionPanel} are added by default
	 */
	protected void addUIElements() {
		this.add(this.searchField, BorderLayout.PAGE_START);
		this.add(this.tableScrollPane, BorderLayout.CENTER);
		this.add(this.actionPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Creates the needed UI-Elements.
	 * {@link #searchField}, {@link #tableModel}, {@link #table} and {@link #tableScrollPane} are created by default
	 */
	protected void createUIElements() {
		this.searchField = new SearchField<>(this.createSearchStrategy(), t -> updateTableModel());

		this.tableModel = createTableModel();
		this.table = new LinkedTable<T>(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);

		this.table.setFillsViewportHeight(true);
		this.table.setAutoCreateRowSorter(true);
	}

	/**
	 * The implementation of the {@link SearchStrategy} which is featured in the {@link #searchField}
	 * @return	Returns the {@link SearchStrategy} for the given type <T>
	 */
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

	/**
	 * Updates the {@link AbstractDataTableModel} with the latest data
	 */
	protected void updateTableModel() {
		this.tableModel.updateData();
		this.repaint();
	}

}
