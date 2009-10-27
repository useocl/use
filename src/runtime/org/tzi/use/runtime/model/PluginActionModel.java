package org.tzi.use.runtime.model;

/**
 * The Plugin Action Model providing all Plugin Action meta information.
 * 
 * @author Roman Asendorf
 */
public class PluginActionModel {

	private String id = null;
	private String actionClass = null;
	private String label = null;
	private String icon = null;
	private String tooltip = null;
	private String menu = null;
	private String menuitem = null;
	private String toolbaritem = null;

	/**
	 * Method to get the Plugin Action class name.
	 * 
	 * @return The Plugin Action class name.
	 */
	public String getActionClass() {
		return this.actionClass;
	}

	/**
	 * Method to get the Plugin Action Icon name.
	 * 
	 * @return The Plugin Action Icon name
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * Method to get the Plugin Action id.
	 * 
	 * @return The Plugin Action id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Method to get the Plugin Action label.
	 * 
	 * @return The Plugin Action label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Method to get the Plugin Action menu name.
	 * 
	 * @return The Plugin Action menu name
	 */
	public String getMenu() {
		return this.menu;
	}

	/**
	 * Method to get the Plugin Action menu item name.
	 * 
	 * @return The Plugin Action menu item name
	 */
	public String getMenuitem() {
		return this.menuitem;
	}

	/**
	 * Method to get the Plugin Action toolbar item name.
	 * 
	 * @return The Plugin Action toolbar item name
	 */
	public String getToolbaritem() {
		return this.toolbaritem;
	}

	/**
	 * Method to get the Plugin Action tooltip.
	 * 
	 * @return The Plugin Action tooltip
	 */
	public String getTooltip() {
		return this.tooltip;
	}

	/**
	 * Method to set the Plugin Action class name
	 * 
	 * @param actionClass
	 *            The Plugin Action class name
	 */
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	/**
	 * Method to set the Plugin Action icon name
	 * 
	 * @param icon
	 *            The Plugin Action icon name
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Method to set the Plugin Action id.
	 * 
	 * @param id
	 *            The Plugin Action id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to set the Plugin Action label.
	 * 
	 * @param label
	 *            The Plugin Action label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Method to set the Plugin Action menu name.
	 * 
	 * @param menu
	 *            The Plugin Action menu name
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}

	/**
	 * Method to set the Plugin Action menu item name.
	 * 
	 * @param menuitem
	 *            The Plugin Action menu item name
	 */
	public void setMenuitem(String menuitem) {
		this.menuitem = menuitem;
	}

	/**
	 * Method to set the Plugin Action toolbar item name.
	 * 
	 * @param toolbaritem
	 *            The Plugin Action toolbar item name
	 */
	public void setToolbaritem(String toolbaritem) {
		this.toolbaritem = toolbaritem;
	}

	/**
	 * Method to set the Plugin Action tooltip.
	 * 
	 * @param tooltip
	 *            The Plugin Action Tooltip
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

}
