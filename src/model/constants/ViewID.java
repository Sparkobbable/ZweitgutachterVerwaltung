package model.constants;

public enum ViewID {
	HOME("HomePanel#1"), OVERVIEW_TABLE("OverviewTablePanel#1"), ACTIONS("ActionPanel#1");
	private String id;
	
	ViewID(String id) {
		this.id = id;
	}
	
	public String getViewID() {
		return this.id;
	}
}
