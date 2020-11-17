package model.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum PresentationMode {
	TABLE("Tabelle"), PIECHART("Tortendiagramm");
	
	
	private String label;
	
	private PresentationMode(String label) {
		this.label = label;
	}
	
	public static Optional<PresentationMode> of(String label) {
		return Stream.of(PresentationMode.values()).filter(p -> p.getLabel().equals(label)).findAny();
	}

	public String getLabel() {
		return this.label;
	}
}
