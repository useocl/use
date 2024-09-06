package org.tzi.use.runtime.util;

/**
 * This interface provides the all Parser Symbols.
 * 
 * @author Roman Asendorf
 */
public interface IPluginParserSymbols {

	String TRUE = "true";
	String FALSE = "false";

	String PLUGIN = "plugin";
	String PLUGIN_ID = "id";
	String PLUGIN_NAME = "name";
	String PLUGIN_VERSION = "version";
	String PLUGIN_PUBLISHER = "publisher";
	String PLUGIN_CLASS = "class";

	String PLUGIN_COMMANDS = "commands";
	String PLUGIN_COMMAND = "command";
	String PLUGIN_COMMAND_ID = "id";
	String PLUGIN_COMMAND_LABEL = "label";
	String PLUGIN_COMMAND_SHELLCMD = "shellcmd";
	String PLUGIN_COMMAND_ALIAS = "alias";
	String PLUGIN_COMMAND_CLASS = "class";
	String PLUGIN_COMMAND_HELP = "help";

	String PLUGIN_SERVICES = "services";
	String PLUGIN_SERVICE = "service";
	String PLUGIN_SERVICE_ID = "id";
	String PLUGIN_SERVICE_LABEL = "label";
	String PLUGIN_SERVICE_CLASS = "class";

	String PLUGIN_ACTIONS = "actions";

	String PLUGIN_ACTION_MENU = "menu";
	String PLUGIN_ACTION_MENU_ID = "id";
	String PLUGIN_ACTION_MENU_LABEL = "label";

	String PLUGIN_ACTION = "action";
	String PLUGIN_ACTION_ID = "id";
	String PLUGIN_ACTION_LABEL = "label";
	String PLUGIN_ACTION_CLASS = "class";
	String PLUGIN_ACTION_ICON = "icon";
	String PLUGIN_ACTION_TOOLTIP = "tooltip";
	String PLUGIN_ACTION_MENU_ITEM = "menuitem";
	String PLUGIN_ACTION_TOOLBAR_ITEM = "toolbaritem";
}
