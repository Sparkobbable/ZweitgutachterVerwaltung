package model.enums;

public enum ReviewType {
	FIRST_REVIEW("Erstgutachten"), SECOND_REVIEW("Zweitgutachten");
	private String label;

	private ReviewType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
