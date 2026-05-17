package org.tzi.use.main.shell.runtime;

import org.tzi.use.runtime.spi.IPluginShellCmdDescriptor;

/**
 * SPI-facing view of a registered plugin shell command.
 *
 * <p>Lives in {@code main.shell.runtime} so that
 * {@link IPluginShellExtensionPoint#createPluginCmds} can declare its return
 * type against this interface without dragging the
 * {@code main.shell.plugin.PluginShellCmdFactory$PluginShellCmdContainer}
 * concrete impl into the SPI — that import would create a
 * {@code runtime → plugin} back-edge inside the shell slice.</p>
 *
 * <p>The concrete impl is
 * {@code org.tzi.use.main.shell.plugin.PluginShellCmdFactory.PluginShellCmdContainer}.</p>
 */
public interface IPluginShellCmdContainer {

    /** The shell command keyword (e.g. {@code "plugin1"}). */
    String getCmd();

    /** Optional alias for the command, or empty string. */
    String getAlias();

    /** Help text printed for the command. */
    String getHelp();

    /**
     * Executes the plugin command.
     *
     * @param cmd          the command keyword
     * @param arguments    the unparsed argument string
     * @param argumentList the pre-parsed argument list
     */
    void executeCmd(String cmd, String arguments, String[] argumentList);

    /** Descriptor of the underlying plugin shell command. */
    IPluginShellCmdDescriptor getDescriptor();
}
