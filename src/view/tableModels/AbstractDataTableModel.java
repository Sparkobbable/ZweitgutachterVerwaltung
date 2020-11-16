package view.tableModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for Table Models that hold a physical copy of their data
 *
 * @param <T> Type of elements shown in this table
 */
public abstract class AbstractDataTableModel<T> extends AbstractTableModel<T> {

	private static final long serialVersionUID = 1L;
	private List<T> elements;
	private List<Predicate<T>> filters;

	public AbstractDataTableModel(List<Column<T, ?>> columns, List<Predicate<T>> filters) {
		super(columns);
		this.filters = filters;
		this.elements = new ArrayList<>();
	}

	@Override
	protected List<T> getElements() {
		return elements;
	}

	public void updateData() {
		Stream<T> stream = this.getUnfilteredData().stream();
		for (Predicate<T> filter : filters) {
			stream = stream.filter(filter);
		}
		this.elements = stream.collect(Collectors.toList());
		this.fireTableDataChanged();
	}

	protected abstract Collection<T> getUnfilteredData();

	@Override
	public T getByIndex(int index) {
		return this.elements.get(index);
	}
}
