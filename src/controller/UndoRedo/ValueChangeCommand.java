package controller.UndoRedo;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ValueChangeCommand <O,V> implements Command{

	
	private V oldValue;
	private V newValue;
	private O object;
	private BiConsumer<O, V> setter;
	
	public ValueChangeCommand(BiConsumer<O, V> setter,Function<O, V> getter, O object, V newValue) {
		this.setter =setter;
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
