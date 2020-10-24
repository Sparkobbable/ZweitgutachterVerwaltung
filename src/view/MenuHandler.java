package view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.constants.ButtonId;

public class MenuHandler {

	private JMenuBar menuBar; //TODO remove (and instead use inheritance) or do it like that everywhere
	private Map<ButtonId, JMenu> menus; //TODO rename ButtonId to ComponentId or create new enum?
	
	public MenuHandler() {
		this.menuBar = new JMenuBar();
		this.menus = new HashMap<>();
		menus.put(ButtonId.BACK, new JMenu("Zurück"));
	}

	public void init() {
		//TODO replace with more flexible option
		menus.values().forEach(menu -> menuBar.add(menu));
	}

	public void useAsMenuBarFor(JFrame frame) { //TODO this is weird. Rename or restructure.
		frame.setJMenuBar(menuBar);
	}
	
	public void onMenuClick(ButtonId componentId, Runnable action) {
		if (!menus.containsKey(componentId)) {
			throw new IllegalArgumentException(String.format(
					"Menu with ButtonId %s does not exist in MenuHandler \"%s\". Could not add ActionListener.", componentId));
		}
		
		//TODO don't use JMenus like that because this is abuse and leads to weird behavior
		menus.get(componentId).addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				action.run();
				menus.get(componentId).setSelected(false); // uaagh
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				
			}
		});
	}
}
