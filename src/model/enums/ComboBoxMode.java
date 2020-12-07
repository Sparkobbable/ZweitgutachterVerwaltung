package model.enums;

import java.util.Optional;
import java.util.stream.Stream;

//TODO @jpfrehe javadoc
public enum ComboBoxMode {
	TABLE("Tabelle"), PIECHART("Tortendiagramm"), BARCHART("Balkendiagramm"),
	FIRSTREVIEWER("Nur Erstgutachter"), SECONDREVIEWER("Nur Zweitgutachter"),
	ALLREVIEWER("Zweit- & Erstgutachter");
	
	
	private String label;
	
	private ComboBoxMode(String label) {
		this.label = label;
	}
	
	public static Optional<ComboBoxMode> of(String label) {
		return Stream.of(ComboBoxMode.values()).filter(p -> p.getLabel().equals(label)).findAny();
	}

	public String getLabel() {
		return this.label;
	}
}
