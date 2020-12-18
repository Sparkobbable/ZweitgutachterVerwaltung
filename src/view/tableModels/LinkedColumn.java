package view.tableModels;

import java.util.function.Function;

/**
 * Column that links to a referenced object
 *
 * @param <T> Type of the element represented in a row
 * @param <E> Type of the Objects in this row
 * @param <R> Type of the referenced Object
 */
public class LinkedColumn<T, E, R> extends Column<T, E> {

	private Function<T, R> referencedObject;

	public LinkedColumn(String title, Function<T, E> values, Function<T, R> referencedObject, Class<E> columnClass) {
		super(title, values, columnClass);
		this.referencedObject = referencedObject;
	}

	public R getReferencedObject(T t) {
		return referencedObject.apply(t);
	}

	/**
	 * lightweight optional factory method that provides some extra convenience
	 * 
	 * @param title
	 * @param values
	 * @return
	 */
	public static <T, E, R> LinkedColumn<T, E, R> of(String title, Function<T, E> values,
			Function<T, R> referencedObject, Class<E> columnClass) {
		return new LinkedColumn<>(title, values, referencedObject, columnClass);
	}
}
