package view.collaboration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import controller.events.CustomEventSource;
import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import model.enums.PresentationMode;
import view.ViewState;
import view.panelstructure.AbstractViewPanel;
import view.panelstructure.DefaultPanel;

public class CollaborationPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;

	private Model model;

	private AbstractViewPanel options;
	private AbstractViewPanel chart;
	
	private PieChart pieChart;
	private CollaborationTableModel table;
	
	private CustomEventSource initializeEventSource;

	public CollaborationPanel(Model model) {
		super("Zusammenarbeit anzeigen");
		this.model = model;
		this.initializeEventSource = new CustomEventSource(EventId.INITIALIZE);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.observe(this.model);
	}

	private void createUIElements() {
		this.pieChart = new PieChart(this.model);
		this.table = new CollaborationTableModel(this.model);
		
		this.options = new CollaborationOptionsPanel(this.model);
		this.chart = this.table;
	}

	private void addUIElements() {
		this.add(this.options, BorderLayout.PAGE_START);
		this.add(this.chart, BorderLayout.CENTER);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource);
	}

	@Override
	public void initializeState(ViewState viewState) {
		switch (viewState) {
		case TABLE:
			this.setBackground(Color.GREEN);
			this.remove(chart);
			this.chart = this.table;
			this.add(chart);
			break;
		case PIECHART:
			this.setBackground(Color.BLUE);
			this.remove(chart);
			this.chart = this.pieChart;
			this.add(chart);
			break;
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		
		initializeEventSource.trigger();
	}
}
