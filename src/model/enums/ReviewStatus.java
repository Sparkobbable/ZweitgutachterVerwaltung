package model.enums;

public enum ReviewStatus {
	REQUESTED("Angefragt"), RESERVED("Vorgemerkt"), APPROVED("Best�tigt"), REJECTED("Abgelehnt");

	private String label;

	private ReviewStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
