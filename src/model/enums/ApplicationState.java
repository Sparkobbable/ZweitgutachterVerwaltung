package model.enums;

/**
 * A logical representation of a view panel
 *
 */
public enum ApplicationState {

	HOME, REVIEWER_OVERVIEW, THESES_OVERVIEW, REVIEWER_EDITOR, STATE_CHOOSER,
	THESIS_ASSIGNMENT, FIRSTREVIEWER_IMPORT, COLLABORATION_TABLE, COLLABORATION_PIECHART;

	public boolean isEqual(ApplicationState state) {
		if(this.equals(state)) {
			return true;
		} else {
			//Special case: collaboration has two appliationstates, which should be treated as one in navigation
			if(this.equals(COLLABORATION_PIECHART) || this.equals(COLLABORATION_TABLE)) {
				if(state.equals(COLLABORATION_PIECHART) || state.equals(COLLABORATION_TABLE)) {
					return true;
				}
			} 
			return false;
		}
	}
}
