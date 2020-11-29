package model.enums;

public enum EventId {

	SHOW_REVIEWERS, IMPORT_FIRST_REVIEWERS, ANALYSE, BACK, EDIT, DELETE, NEW, SEARCH, SAVE_REVIEWER, ADD_THESIS,
	CHOOSE_FILEPATH, LOAD_STATE, SAVE_STATE, REJECT, ADD_THESIS_TO_REVIEWER, SHOW_THESES, SHOW_COLLABORATION,
	CHOOSE_DATA_FOR_COLLABORATION, CHOOSE_PRESENTATION_FOR_COLLABORATION, APPROVE_SEC_REVIEW, UNDO, REDO, SELECT,
	INITIALIZE;

	// TODO Refactor names, like "NEW" could be used for a button anywhere in the
	// application
	// TODO we should rather make them less telling, so that an EventId only makes sense when combined with an ApplicationState
}
