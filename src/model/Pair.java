package model;

/**
 * Generically stores two elements of any type.
 *
 * @param <L> Type of the left element.
 * @param <R> Type of the right element.
 */
public class Pair<L, R> {

	private final L left;
	private final R right;

	/**
	 * Creates a new {@link Pair} of the given <L> and <R> values
	 * @param <L>	Type of the left Value
	 * @param <R>	Type of the right Value
	 * @param left	Value to be stored on the left side
	 * @param right	Value to be stored on the right side
	 * @return		Returns the Pair containing the given values
	 */
	public static <L, R> Pair<L, R> of(L left, R right) {
		return new Pair<L, R>(left, right);
	}

	/**
	 * Creates a new {@link Pair} of the given values
	 * Can also be created using {@link #of(Object, Object)}
	 * @param left	Value to be stored on the left side
	 * @param right	Value to be stored on the right side
	 */
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * @return The value stored on the left side
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * @return The value stored on the right side
	 */
	public R getRight() {
		return right;
	}

}