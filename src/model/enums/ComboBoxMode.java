package model.enums;

import java.util.Optional;
import java.util.stream.Stream;

import view.widgets.ComboBoxPanel;

/**
* Results from {@link ComboBoxPanel} transferring the entered Strings
*/
public enum ComboBoxMode {
	TABLE("Tabelle"), PIECHART("Tortendiagramm"), BARCHART("Säulendiagramm"),
	BARCHARTHORIZONTAL("Balkendiagramm"), FIRSTREVIEWER("Nur Erstgutachten"),
	SECONDREVIEWER("Nur Zweitgutachten"), ALLREVIEWER("Erst- & Zweitgutachten");
	
	/**
	 * String from the comboBox
	 */
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
