package controller.commands.base;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.enums.ApplicationState;

/**
 * Change the value
 * 
 *
 * @param <O> typ of the changed object
 * @param <V> typ of the changed propertie
 */
public class ValueChangeCommand<O, V> extends RevertibleCommand {

	private final O object;
	private final V oldValue;
	private final V newValue;
	private final BiConsumer<O, V> setter;

	public ValueChangeCommand(BiConsumer<O, V> setter, Function<O, V> getter, O object, V newValue,
			ApplicationState applicationState) {
		super(applicationState);
		this.setter = setter;
		this.object = object;
		this.newValue = newValue;
		this.oldValue = getter.apply(object);
	}

	@Override
	public void execute() {
		setter.accept(object, newValue);

	}

	@Override
	public void revert() {
		setter.accept(object, oldValue);

	}

}
