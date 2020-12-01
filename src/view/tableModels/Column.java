package view.tableModels;

import java.util.function.Function;

public class Column<T, E> {
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