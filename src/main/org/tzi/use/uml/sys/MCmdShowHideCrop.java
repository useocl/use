package org.tzi.use.uml.sys;

import java.util.List;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

public class MCmdShowHideCrop extends MCmd {
	public enum Mode {
		SHOW,
		HIDE,
		CROP
	};
	
	private MAssociation association = null;
	private List<MObject> objects = null;
	private Mode mode;
	
	/**
	 * Creates a show/hide/crop command for a link
	 * @param mode Show, hide or crop
	 * @param association The referenced association
	 * @param objects The objects participating the link
	 */
	public MCmdShowHideCrop(Mode mode, MAssociation association, List<MObject> objects) {
		this(null, mode, association, objects);
	}
	
	/**
	 * Creates a show/hide/crop command for a link
	 * @param pos Source position
	 * @param mode Show, hide or crop
	 * @param association The referenced association
	 * @param objects The objects participating the link
	 */
	public MCmdShowHideCrop(SrcPos pos, Mode mode, MAssociation association, List<MObject> objects) {
		super(pos, false);
		this.mode = mode;
		this.association = association;
		this.objects = objects;
	}
	
	/**
	 * Creates a show/hide/crop command for the specified objects
	 * @param mode
	 * @param objects
	 */
	public MCmdShowHideCrop(SrcPos pos, Mode mode, List<MObject> objects) {
		super(pos, false);
		this.mode = mode;
		this.objects = objects;
	}

	/**
	 * Creates a command to show or hide all objects.
	 * @param mode Show or hide
	 */
	public MCmdShowHideCrop(SrcPos pos, Mode mode) {
		super(pos, false);
		this.mode = mode;
	}

	/**
	 * The association referenced by this command.
	 * null if command does not involve an association. 
	 * @return
	 */
	public MAssociation getAssociation() {
		return association;
	}

	/**
	 * Objects referenced by this command
	 * null if command does not use object list
	 * @return
	 */
	public List<MObject> getObjects() {
		return objects;
	}

	/**
	 * Determines what to do
	 * @return
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * True if command is targeting all objects.
	 * @return
	 */
	public boolean handleAll() {
		return this.objects == null;
	}
	
	/**
	 * True if command is targeting a link 
	 * @return
	 */
	public boolean handleLink() {
		return this.association != null;
	}
	
	@Override
	public void getChanges(StateChangeEvent sce, boolean undoChanges) {
		// No changes to system state
	}

	@Override
	public String getUSEcmd() {
		return "!" + name();
	}

	@Override
	protected void doExecute() throws CommandFailedException {
		// Handled in MainWindow
	}

	@Override
	public String name() {
		StringBuilder cmd = new StringBuilder();
		switch (mode) {
			case SHOW:
				cmd.append("show");
				break;
			case HIDE:
				cmd.append("hide");
				break;
			case CROP:
				cmd.append("crop");
				break;
		}
		cmd.append(" ");
		
		if (this.handleAll()) {
			cmd.append("all");
		} else {
			if (this.handleLink()) {
				cmd.append("link (");
				cmd.append(StringUtil.fmtSeq(this.objects, ","));
				cmd.append(") from ");
				cmd.append(this.association.name());
			} else {
				cmd.append(StringUtil.fmtSeq(this.objects, ","));
			}
		}
		
		return cmd.toString();
	}

	@Override
	public String toString() {
		return name();
	}

	@Override
	public void undo() throws CannotUndoException {
		throw new CannotUndoException();
	}

}
