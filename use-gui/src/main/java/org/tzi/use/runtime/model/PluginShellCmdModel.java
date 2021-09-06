package org.tzi.use.runtime.model;


/**
 * The Plugin Shell Command Model providing all Plugin Command Model meta
 * information.
 * 
 * @author Roman Asendorf
 */
public class PluginShellCmdModel {

	private String id = null;
	private String label = null;
	private String cmdClass = null;
	private String shellCmd = null;
	private String alias = null;
	private String cmdHelp = null;

	/**
	 * Method to get the Plugin Shell Command class name.
	 * 
	 * @return The Plugin Shell Command class name
	 */
	public String getCmdClass() {
		return cmdClass;
	}
	
	public String getAlias() {
		return alias;
	}
	
	/**
	 * Method to get the Plugin Shell Command help description.
	 * 
	 * @return The Plugin Shell Command help description
	 */
	public String getCmdHelp() {
		return cmdHelp;
	}

	/**
	 * Method to get the Plugin Shell Command id.
	 * 
	 * @return The Plugin Shell Command id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to get the Plugin Shell Command label.
	 * 
	 * @return The Plugin Shell Command label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Method to get the Plugin Shell Command name.
	 * 
	 * @return The Plugin Shell Command name
	 */
	public String getShellCmd() {
		return shellCmd;
	}

	/**
	 * Method to set the Plugin Shell Command class name
	 * 
	 * @param cmdClass
	 *            The Plugin Shell Command class name
	 */
	public void setCmdClass(String cmdClass) {
		this.cmdClass = cmdClass;
	}

	/**
	 * Method to set the Plugin Shell Command help description.
	 * 
	 * @param cmdHelp
	 *            The Plugin Shell Command help description
	 */
	public void setCmdHelp(String cmdHelp) {
		this.cmdHelp = cmdHelp;
	}

	/**
	 * Method to set the Plugin Shell Command id.
	 * 
	 * @param id
	 *            The Plugin Shell Command id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to set the Plugin Shell Command label.
	 * 
	 * @param label
	 *            The Plugin Shell Command label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Method to set the Plugin Shell Command name.
	 * 
	 * @param shellCmd
	 *            The Plugin Shell Command name
	 */
	public void setShellCmd(String shellCmd) {
		this.shellCmd = shellCmd;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
