package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Base class for all tableModels that provides some extra functionality in
 * comparison to {@link javax.swing.table.AbstractTableModel}
 *
 * @param <T>
 */
public abstract class AbstractTableModel<T> extends javax.swing.table.AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final List<Column<T, ?>> columns;

	public AbstractTableModel(List<Column<T, ?>> columns) {
		this.columns = columns;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns.get(columnIndex).getTitle();
	}
	
	@Override
	public int getRowCount() {
		return this.getElements().size();
	}

	@Override
	public int getColumnCount() {
		return this.columns.size();
	}
	
	@Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columns.get(columnIndex).getColumnClass();
    }

	protected abstract Collection<T> getElements();

	public abstract T getByIndex(int index);

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.columns.get(columnIndex).getValue(this.getByIndex(rowIndex));
	}

	public static class Column<T, E> {
		private final String title;
		private final Function<T, E> valueFunction;
		private Class<E> columnClass;

		public Column(String title, Function<T, E> values, Class<E> columnClass) {
			this.title = title;
			this.valueFunction = values;
			this.columnClass = columnClass;
		}

		public String getTitle() {
			return title;
		}

		public Class<E> getColumnClass() {
			return columnClass;
		}

		public Function<T, E> getValues() {
			return valueFunction;
		}

		public E getValue(T t) {
			return valueFunction.apply(t);
		}

		/**
		 * lightweight optional factory method that provides some extra convenience
		 * 
		 * @param title
		 * @param values
		 * @return
		 */
		public static <T, E> Column<T, E> of(String title, Function<T, E> values, Class<E> columnClass) {
			return new Column<>(title, values, columnClass);
		}

	}

}
