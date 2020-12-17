package view.tableModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.table.TableModel;

/**
 * Base class for Table Models that hold a physical copy of their data
 *
 * @param <T> Type of elements shown in this table
 */
public abstract class AbstractDataTableModel<T> extends AbstractTableModel<T> {

	private static final long serialVersionUID = 1L;
	private List<T> elements;
	private List<Predicate<T>> filters;

	/**
	 * Creates a new {@link TableModel} which contains data of the specified type <T>
	 * @param columns	Needs the {@link Column} for accessing the data of type <T>
	 * @param filters	Filters the table with the given {@link Predicate}s
	 */
	public AbstractDataTableModel(List<Column<T, ?>> columns, List<Predicate<T>> filters) {
		super(columns);
		this.filters = filters;
		this.elements = new ArrayList<>();
	}

	@Override
	protected List<T> getElements() {
		return elements;
	}

	/**
	 * Updates the data in the table
	 */
	public void updateData() {
		Stream<T> stream = this.getUnfilteredData().stream();
		for (Predicate<T> filter : filters) {
			stream = stream.filter(filter);
		}
		this.elements = stream.collect(Collectors.toList());
		this.fireTableDataChanged();
	}

	/**
	 * Supplies the unfiltered data for the {@link TableModel}
	 * <p>
	 * Needs to be overridden.
	 * @return	The unfiltered list of type <T>
	 */
	protected abstract Collection<T> getUnfilteredData();

	@Override
	public T getByIndex(int index) {
		return this.elements.get(index);
	}
}
