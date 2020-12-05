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

	public static <L, R> Pair<L, R> of(L left, R right) {
		return new Pair<L, R>(left, right);
	}

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

}