package controller.commands.base;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.enums.ApplicationState;

public class ValueChangeCommand<O, V> extends RevertibleCommand {

	private O object;
	private V oldValue;
	private V newValue;
	private BiConsumer<O, V> setter;

	public ValueChangeCommand(BiConsumer<O, V> setter, Function<O, V> getter, O object, V newValue, ApplicationState applicationState) {
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
