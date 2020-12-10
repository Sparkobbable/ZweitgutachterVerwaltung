package model.enums;

/**
 * Id of an Event. Must be uniquely assignable to an event in combination with an {@link ApplicationState}.
 *
 */
public enum EventId {

	SHOW_REVIEWERS, 
	IMPORT_FIRST_REVIEWERS, ANALYSE, BACK, EDIT, DELETE, CREATE, SEARCH, ADD_THESIS,
	CHOOSE_FILEPATH, LOAD, SAVE, REJECT, ADD_THESIS_TO_REVIEWER, SHOW_THESES, SHOW_COLLABORATION,
	CHOOSE_REVIEW_FILTER, CHOOSE_PRESENTATION, APPROVE_SEC_REVIEW, UNDO, REDO, SELECT,
	INITIALIZE, NAME_CHANGED, EMAIL_CHANGED, MAX_SUPERVISED_THESES_CHANGED, COMMENT_CHANGED, NAVIGATE, RESERVE_SEC_REVIEW;

	// TODO Refactor names, like "NEW" could be used for a button anywhere in the
	// application
	// TODO we should rather make them less telling, so that an EventId only makes sense when combined with an ApplicationState
	
	
}
