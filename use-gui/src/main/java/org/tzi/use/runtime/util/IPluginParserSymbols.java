package org.tzi.use.runtime.util;

/**
 * This interface provides the all Parser Symbols.
 * 
 * @author Roman Asendorf
 */
public interface IPluginParserSymbols {

	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final String PLUGIN = "plugin";
	public static final String PLUGIN_ID = "id";
	public static final String PLUGIN_NAME = "name";
	public static final String PLUGIN_VERSION = "version";
	public static final String PLUGIN_PUBLISHER = "publisher";
	public static final String PLUGIN_CLASS = "class";

	public static final String PLUGIN_COMMANDS = "commands";
	public static final String PLUGIN_COMMAND = "command";
	public static final String PLUGIN_COMMAND_ID = "id";
	public static final String PLUGIN_COMMAND_LABEL = "label";
	public static final String PLUGIN_COMMAND_SHELLCMD = "shellcmd";
	public static final String PLUGIN_COMMAND_ALIAS = "alias";
	public static final String PLUGIN_COMMAND_CLASS = "class";
	public static final String PLUGIN_COMMAND_HELP = "help";

	public static final String PLUGIN_SERVICES = "services";
	public static final String PLUGIN_SERVICE = "service";
	public static final String PLUGIN_SERVICE_ID = "id";
	public static final String PLUGIN_SERVICE_LABEL = "label";
	public static final String PLUGIN_SERVICE_CLASS = "class";

	public static final String PLUGIN_ACTIONS = "actions";

	public static final String PLUGIN_ACTION_MENU = "menu";
	public static final String PLUGIN_ACTION_MENU_ID = "id";
	public static final String PLUGIN_ACTION_MENU_LABEL = "label";

	public static final String PLUGIN_ACTION = "action";
	public static final String PLUGIN_ACTION_ID = "id";
	public static final String PLUGIN_ACTION_LABEL = "label";
	public static final String PLUGIN_ACTION_CLASS = "class";
	public static final String PLUGIN_ACTION_ICON = "icon";
	public static final String PLUGIN_ACTION_TOOLTIP = "tooltip";
	public static final String PLUGIN_ACTION_MENU_ITEM = "menuitem";
	public static final String PLUGIN_ACTION_TOOLBAR_ITEM = "toolbaritem";
}
