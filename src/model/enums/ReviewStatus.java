package model.enums;

import model.domain.SecondReview;

/**
 * Status of a {@link SecondReview}
 */
public enum ReviewStatus {
	REQUESTED("Angefragt"), RESERVED("Vorgemerkt"), APPROVED("Bestätigt"), REJECTED("Abgelehnt");

	private String label;

	private ReviewStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
