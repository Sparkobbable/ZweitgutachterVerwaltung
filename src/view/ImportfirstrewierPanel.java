package view;

import java.awt.Color;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class ImportfirstrewierPanel extends AbstractView{

	private JButton loadReview;
	public ImportfirstrewierPanel(ViewId viewId) {
		super(viewId,"FirstReviewer Import");
		this.setBackground(Color.orange);
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	protected void createUIElements() {
		this.loadReview = new JButton("Import Review");
		}
	
	public void addUIElements() {
		this.add(loadReview);
		
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(
				new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, loadReview));
				
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
