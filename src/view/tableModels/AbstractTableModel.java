package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

	@SuppressWarnings("unchecked")
	public Optional<?> getReferencedAt(int rowIndex, int columnIndex) {
		Column<T, ?> column = this.columns.get(columnIndex);
		if (column instanceof LinkedColumn) {
			LinkedColumn<T, ?, ?> linkedColumn = (LinkedColumn<T, ?, ?>) column;
			return Optional.ofNullable(linkedColumn.getReferencedObject(this.getByIndex(rowIndex)));
		}
		return Optional.empty();
	}

	public boolean isReference(int rowIndex, int columnIndex) {
		return this.getReferencedAt(rowIndex, columnIndex).isPresent();
	}

}
